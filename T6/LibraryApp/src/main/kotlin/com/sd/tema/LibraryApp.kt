package com.sd.tema

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class LibraryApp{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<LibraryApp>(*args)
        }
    }
}
