package com.sd.laborator

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus

@Controller("/hello")
class HelloController {

    @Get("/")
    fun index(): String {
        return "Salut"
    }
}

//mn create-function com.sd.laborator.hello-world-gradle-pascal --lang kotlin -build gradle