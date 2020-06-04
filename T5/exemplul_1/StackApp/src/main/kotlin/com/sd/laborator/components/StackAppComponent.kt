package com.sd.laborator.components

import com.sd.laborator.interfaces.CartesianProductOperation
import com.sd.laborator.interfaces.PrimeNumberGenerator
import com.sd.laborator.interfaces.UnionOperation
import com.sd.laborator.model.Stack
import com.sd.laborator.services.OperationService
import com.sd.laborator.services.SetGenerator
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.System.exit

@Component
class StackAppComponent {
    companion object{
        @JvmStatic
        var A: Stack? = null
        @JvmStatic
        var B: Stack? = null
    }

    @Autowired
    private lateinit var firstService: SetGenerator

    @Autowired
    private lateinit var operationService: OperationService
    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate() {
        firstService.setNext(operationService)
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }

    @RabbitListener(queues = ["\${stackapp.rabbitmq.queue}"])
    fun recieveMessage(msg: String) {
        // the result: 114,101,103,101,110,101,114,97,116,101,95,65 --> needs processing
        println(msg)

        val processed_msg = (msg.split(",").map { it.toInt().toChar() }).joinToString(separator="")
        var result = firstService.execute(processed_msg)
        println("result: ")
        println(result)
        if (result != null) sendMessage(result)
    }

    fun sendMessage(msg: String) {
        println("message: ")
        println(msg)
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange(),
            connectionFactory.getRoutingKey(),
            msg)
    }
}