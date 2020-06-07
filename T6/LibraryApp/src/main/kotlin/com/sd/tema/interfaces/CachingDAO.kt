package com.sd.tema.interfaces

interface CachingDAO {
    fun exists(query: String): String
    fun addToCache(query: String, result: String)
}