package com.maven8919.dfgenerator.draftkings.baseball.service

import com.maven8919.dfgenerator.draftkings.baseball.Player
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PlayerService {

    fun getPlayers(file: MultipartFile) : List<Player> {
        return listOf()
    }

    fun generateStarters(players: List<Player>) : List<Player> {
        return listOf()
    }
}

