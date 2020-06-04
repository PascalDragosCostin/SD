package com.sd.laborator.interfaces

interface ChainOperation {
    fun execute(cmd: String): String
    fun setNext(service: ChainOperation)
}