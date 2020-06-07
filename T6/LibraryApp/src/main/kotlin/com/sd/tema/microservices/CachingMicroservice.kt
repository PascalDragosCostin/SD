package com.sd.tema.microservices

import com.sd.tema.controllers.RabbitMqController
import com.sd.tema.interfaces.CachingDAO
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller

@Controller
class CachingMicroservice {

    @Autowired
    private lateinit var cahingDAO: CachingDAO

    @Autowired
    private lateinit var rabbitMqCntroller: RabbitMqController

    private lateinit var amqpTemplate: AmqpTemplate

    @Value("\${libraryapp.rabbitmq.routingkey1}")
    private lateinit var routingKey: String

    @Autowired
    fun initTemplate() {
        rabbitMqCntroller.setRoutingKey(routingKey)
        this.amqpTemplate = rabbitMqCntroller.rabbitTemplate()
    }

    @RabbitListener(queues = ["\${libraryapp.rabbitmq.queue}"])
    //
    fun fetchMessage(message: String) {
        if(message.startsWith("update")){

            val msg = message.substringAfter(":")

            val query = msg.substringBefore(":")
            val result = msg.substringAfter(":")
            cahingDAO.addToCache(query, result)
        }else{
            val result = cahingDAO.exists(message)
            sendMessage(if(result.isNotEmpty()) result else "none")
        }
    }

    fun sendMessage(message: String) {
        this.amqpTemplate.convertAndSend(rabbitMqCntroller.getExchange(), rabbitMqCntroller.getRoutingKey(), message)
    }

}