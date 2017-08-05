package com.maven8919.dfgenerator.draftkings.baseball.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DfBaseballController {

    @GetMapping("df_baseball")
    fun dfBaseball() : String {
        return ""
    }

}