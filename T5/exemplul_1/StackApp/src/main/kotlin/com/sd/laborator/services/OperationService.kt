package com.sd.laborator.services


import com.sd.laborator.components.StackAppComponent
import com.sd.laborator.interfaces.CartesianProductOperation
import com.sd.laborator.interfaces.ChainOperation
import com.sd.laborator.interfaces.UnionOperation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class OperationService: ChainOperation {

    private lateinit var nextService: ChainOperation

    @Autowired
    private lateinit var cartesianProductOperation: CartesianProductOperation
    @Autowired
    private lateinit var unionOperation: UnionOperation

    override fun execute(cmd: String): String {
        return when(cmd) {
            "compute" -> computeExpression()
            else -> nextService.execute(cmd)
        }
    }

    override fun setNext(service: ChainOperation) {
        nextService = service
    }

    private fun computeExpression(): String {
        val A = StackAppComponent.A
        val B = StackAppComponent.B
        if (A!!.data.count() == B!!.data.count()) {
            // (A x B) U (B x B)
            val partialResult1 = cartesianProductOperation.executeOperation(A.data, B.data)
            val partialResult2 = cartesianProductOperation.executeOperation(B.data, B.data)
            val result = unionOperation.executeOperation(partialResult1, partialResult2)
            return "compute~" + "{\"A\": \"" + A.data.toString() +"\", \"B\": \"" + B.data.toString() + "\", \"result\": \"" + result.toString() + "\"}"
        }
        return "compute~" + "Error: A.count() != B.count()"
    }

}