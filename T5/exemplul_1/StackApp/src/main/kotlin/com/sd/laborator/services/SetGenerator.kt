package com.sd.laborator.services


import com.sd.laborator.components.StackAppComponent
import com.sd.laborator.interfaces.ChainOperation
import com.sd.laborator.interfaces.PrimeNumberGenerator
import com.sd.laborator.model.Stack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SetGenerator: ChainOperation {

    private lateinit var nextService: ChainOperation

    @Autowired
    private lateinit var primeGenerator: PrimeNumberGenerator

    override fun execute(cmd: String): String {
        return when(cmd) {
            "regenerate_A" -> {
                StackAppComponent.A = generateStack(20)
                return "A~" +StackAppComponent.A?.data.toString()
            }
            "regenerate_B" -> {
                StackAppComponent.B = generateStack(20)
                return "B~" + StackAppComponent.B?.data.toString()
            }
            else -> nextService.execute(cmd)
        }
    }

    private fun generateStack(count: Int): Stack? {
        if (count < 1)
            return null
        var X: MutableSet<Int> = mutableSetOf()
        while (X.count() < count)
            X.add(primeGenerator.generatePrimeNumber())
        return Stack(X)
    }

    override fun setNext(service: ChainOperation) {
        nextService = service
    }
}