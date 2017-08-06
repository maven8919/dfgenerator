package com.maven8919.dfgenerator.draftkings.baseball.service

import com.maven8919.dfgenerator.draftkings.baseball.DKBaseballPlayer
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
        fields?.forEach { print(it) }
        return DKBaseballPlayer(fields?.get(0) as String,
                fields?.get(1) as String,
                fields?.get(2).toInt(),
                fields?.get(3) as String,
                fields?.get(4).toDouble(),
                fields?.get(5) as String)
    }

    fun generateStarters(DKBaseballPlayers: List<DKBaseballPlayer>) : List<DKBaseballPlayer> {
        return listOf()
    }
}

