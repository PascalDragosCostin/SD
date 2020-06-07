package com.sd.tema.microservices

import com.sd.tema.controllers.RabbitMqController
import com.sd.tema.interfaces.LibraryDAO
import com.sd.tema.interfaces.LibraryPrinter
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.concurrent.Semaphore

@Controller
class LibraryPrinterMicroservice {
    @Autowired
    private lateinit var libraryDAO: LibraryDAO

    @Autowired
    private lateinit var libraryPrinter: LibraryPrinter

    private var unprocessedMessages = mutableListOf<String>()

    private var messageSemaphore = Semaphore(1)

    @Autowired
    private lateinit var rabbitMqCntroller: RabbitMqController

    private lateinit var amqpTemplate: AmqpTemplate

    @Value("\${libraryapp.rabbitmq.routingkey}")
    private lateinit var routingKey: String

    @Autowired
    fun initTemplate() {
        rabbitMqCntroller.setRoutingKey(routingKey)
        this.amqpTemplate = rabbitMqCntroller.rabbitTemplate()
    }

    @RabbitListener(queues = ["\${libraryapp.rabbitmq.queue1}"])
    fun fetchMessage(message: String) {
        synchronized(unprocessedMessages){
            unprocessedMessages.add(message)
        }
        if(messageSemaphore.availablePermits() < 1){
            messageSemaphore.release()
        }
    }

    private fun waitForMessage(){
        messageSemaphore.acquire()
        // It should be blocked here until a message arrives and releases a permit
        messageSemaphore.acquire()
        messageSemaphore.release()
    }

    fun sendMessage(message: String) {
        this.amqpTemplate.convertAndSend(rabbitMqCntroller.getExchange(), rabbitMqCntroller.getRoutingKey(), message)
    }

    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format", defaultValue = "") format: String): String {
        var query = "print;$format"
        sendMessage(query)
        waitForMessage()
        assert(unprocessedMessages.size > 0)
        var cacheResult = unprocessedMessages.last()
        unprocessedMessages.removeAt(unprocessedMessages.lastIndex)

        if(cacheResult != "none"){
            println("Cache hit!")
            return cacheResult
        }
        println("Cache miss!")

        var result =  when(format) {
            "html" -> libraryPrinter.printHTML(libraryDAO.getBooks())
            "json" -> libraryPrinter.printJSON(libraryDAO.getBooks())
            "raw" -> libraryPrinter.printRaw(libraryDAO.getBooks())
            else -> "Not implemented"
        }

        // Send an update message if the result was not found
        sendMessage("update:$query:$result")
        return result
    }

    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(@RequestParam(required = false, name = "author", defaultValue = "") author: String,
                   @RequestParam(required = false, name = "title", defaultValue = "") title: String,
                   @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String): String {
        var query = "find;$author;$title;$publisher"
        sendMessage(query)
        waitForMessage()
        assert(unprocessedMessages.size > 0)
        var cacheResult = unprocessedMessages.last()
        unprocessedMessages.removeAt(unprocessedMessages.lastIndex)

        if(cacheResult != "none"){
            println("Cache hit!")
            return cacheResult
        }
        println("Cache miss!")

        var result = "Not a valid field"
        if (author != "")
            result = this.libraryPrinter.printJSON(this.libraryDAO.findAllByAuthor(author))
        if (title != "")
            result = this.libraryPrinter.printJSON(this.libraryDAO.findAllByTitle(title))
        if (publisher != "")
            result = this.libraryPrinter.printJSON(this.libraryDAO.findAllByPublisher(publisher))

        // Send an update message if the result was not found
        sendMessage("update:$query:$result")
        return result
    }

}