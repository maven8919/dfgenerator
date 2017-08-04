package com.maven8919.dfgenerator.index

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    @GetMapping(value = *arrayOf("/", "", "/index"))
    fun index() : String {
        return "index"
    }

}