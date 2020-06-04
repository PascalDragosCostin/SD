package com.sd.laborator;

import io.micronaut.function.executor.FunctionInitializer
import io.micronaut.function.FunctionBean;
import javax.inject.*;
import java.util.function.Function;

@FunctionBean("hello-world-gradle-pascal")
class HelloWorldGradlePascalFunction : FunctionInitializer(), Function<HelloWorldGradlePascal, HelloWorldGradlePascal> {
    //echo {"name": "covid"} | java -jar hello-world-gradle-pascal-0.1-all.jar
    override fun apply(msg : HelloWorldGradlePascal) : HelloWorldGradlePascal {
         return msg
    }   
}

/**
 * This main method allows running the function as a CLI application using: echo '{}' | java -jar function.jar 
 * where the argument to echo is the JSON to be parsed.
 */
fun main(args : Array<String>) { 
    val function = HelloWorldGradlePascalFunction()
    function.run(args, { context -> function.apply(context.get(HelloWorldGradlePascal::class.java))})
}

