//package com.sd.tema
////
////import org.springframework.boot.autoconfigure.SpringBootApplication
////import org.springframework.boot.runApplication
////
////@SpringBootApplication
////class Hello {
////    companion object {
////        @JvmStatic
////        fun main(args: Array<String>){
////            val runApplication = runApplication<Hello>(args = *args)
////        }
////    }
////}

package com.sd.tema

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EntryPoint
fun main(args: Array<String>)
{
    runApplication<EntryPoint>(*args)
}