package com.sd.tema.controllers

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Controller

@Controller
@Scope("prototype")
class RabbitMqController {
    @Value("\${spring.rabbitmq.host}")
    private lateinit var host: String
    @Value("\${spring.rabbitmq.port}")
    private val port: Int = 0
    @Value("\${spring.rabbitmq.username}")
    private lateinit var username: String
    @Value("\${spring.rabbitmq.password}")
    private lateinit var password: String
    @Value("\${libraryapp.rabbitmq.exchange}")
    private lateinit var exchange: String
    private lateinit var routingKey: String

    fun getExchange(): String = this.exchange

    fun setExchange(value: String) {
        this.exchange = value
    }

    fun getRoutingKey(): String = this.routingKey

    fun setRoutingKey(value: String) {
        this.routingKey = value
    }

    @Bean
    @Scope("prototype")
    private fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory()
        connectionFactory.host = this.host
        connectionFactory.username = this.username
        connectionFactory.setPassword(this.password)
        connectionFactory.port = this.port
        return connectionFactory
    }

    @Bean
    @Scope("prototype")
    fun rabbitTemplate(): RabbitTemplate = RabbitTemplate(connectionFactory())
}