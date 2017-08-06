package com.maven8919.dfgenerator.draftkings.baseball.service

import com.maven8919.dfgenerator.draftkings.baseball.DKBaseballPlayer
import org.ojalgo.optimisation.Expression
import org.ojalgo.optimisation.ExpressionsBasedModel
import org.ojalgo.optimisation.Variable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors


@Service
class DKBaseballPlayerService {

    fun getPlayers(file: MultipartFile) : List<DKBaseballPlayer> {
        var fileStream = BufferedReader(InputStreamReader(file.inputStream))
        return fileStream.lines()
                .skip(1)
                .map { transformLineToBaseballPlayer(it) }
                .collect(Collectors.toList())
    }

    private fun transformLineToBaseballPlayer(line: String?): DKBaseballPlayer {
        val fields: List<String>? = line?.split(",")
        val player = DKBaseballPlayer(parsePositions(fields?.get(0)),
                fields?.get(1) as String,
                fields?.get(2).toInt(),
                fields?.get(3) as String,
                fields?.get(4).toDouble(),
                fields?.get(5) as String)
        return player
    }

    private fun parsePositions(allPositions: String?): List<String> {
        val result = ArrayList<String>()
        val positions = allPositions?.replace("\"", "")?.split("/")
        positions?.forEach{result.add(it)}
        return result
    }

    fun generateStarters(dKBaseballPlayers: List<DKBaseballPlayer>) : List<DKBaseballPlayer> {
        val result = ArrayList<DKBaseballPlayer>()
        val players = dKBaseballPlayers.filter { it.avgPointsPerGame > 0.0 }
        val model = buildModel(players)
        val optResult = model.maximise()
        for (i in 0..players.size - 1) {
            if (0.9 < optResult.doubleValue(i.toLong())) {
                result.add(players.get(i))
            }
        }
        return result
    }

    private fun buildModel(dKBaseballPlayers: List<DKBaseballPlayer>): ExpressionsBasedModel {
        val model = ExpressionsBasedModel()
        val variables: List<Variable> = dKBaseballPlayers.map {
            Variable.make(it.name).lower(0).upper(1).weight(it.avgPointsPerGame).integer(true)
        }
        variables.forEach{
            model.addVariable(it)
        }

        val pitcherCountExpression: Expression = model.addExpression("Pitcher count").lower(2).upper(2)
        setExpression(variables, pitcherCountExpression, listOf<String>("SP", "RP"), dKBaseballPlayers);

        val catcherCountExpression: Expression = model.addExpression("Catcher count").lower(1).upper(1)
        setExpression(variables, catcherCountExpression, listOf<String>("C"), dKBaseballPlayers);

        val firstBaseCountExpression = model.addExpression("1B count").lower(1).upper(1)
        setExpression(variables, firstBaseCountExpression, listOf<String>("1B"), dKBaseballPlayers);

        val secondBaseCountExpression = model.addExpression("2B count").lower(1).upper(1)
        setExpression(variables, secondBaseCountExpression, listOf<String>("2B"), dKBaseballPlayers);

        val thirdBaseCountExpression = model.addExpression("3B count").lower(1).upper(1)
        setExpression(variables, thirdBaseCountExpression, listOf<String>("3B"), dKBaseballPlayers);

        val shortstopCountExpression = model.addExpression("SS count").lower(1).upper(1)
        setExpression(variables, shortstopCountExpression, listOf<String>("SS"), dKBaseballPlayers);

        val outfielderCountExpression = model.addExpression("OF count").lower(3).upper(3)
        setExpression(variables, outfielderCountExpression, listOf<String>("OF"), dKBaseballPlayers);

        val salaryMax = model.addExpression("Salary").lower(1).upper(50000)
        dKBaseballPlayers.forEach{
            salaryMax.set(variables.get(dKBaseballPlayers.indexOf(it)), it.salary)
        }

        return model
    }


    private fun setExpression(variables: List<Variable>, expression: Expression?,
                              neededPositions: List<String>, players: List<DKBaseballPlayer>) {
        assert(variables.size == players.size)
        for (player in players) {
            if (player.position.intersect(neededPositions).isNotEmpty()) {
                expression?.set(variables.get(players.indexOf(player)), 1)
            }
        }
    }
}

