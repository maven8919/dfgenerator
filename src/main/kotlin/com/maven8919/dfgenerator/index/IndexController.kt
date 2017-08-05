package com.maven8919.dfgenerator.index

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {

    @GetMapping(value = *arrayOf("/", "", "/index"))
    fun index() : String {
        return "index"
    }

}