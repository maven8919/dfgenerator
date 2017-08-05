package com.maven8919.dfgenerator.draftkings.baseball.controller

import com.maven8919.dfgenerator.draftkings.baseball.Player
import com.maven8919.dfgenerator.draftkings.baseball.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
class DfBaseballController {

    @Autowired lateinit var playerService : PlayerService

    @ModelAttribute("players")
    fun players(@RequestParam("playersCsv") file : MultipartFile) {
        var players = playerService.getPlayers(file)
        var starters = playerService.generateStarters(players)
        return starters

    }

    @PostMapping("df_baseball")
    fun dfBaseball(@RequestParam("playersCsv") file : MultipartFile, model : Model) : String {
        return ""
    }

}