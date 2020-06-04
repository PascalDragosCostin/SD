package com.sd.laborator

import io.micronaut.core.annotation.Introspected

@Introspected
class EratosteneRequest {
	private lateinit var listOfNumbers: List<Int>

	fun getlistOfNumbers(): List<Int> {
		return listOfNumbers
	}
}