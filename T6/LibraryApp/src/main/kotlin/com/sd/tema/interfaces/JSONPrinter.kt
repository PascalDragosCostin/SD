package com.sd.tema.interfaces

import com.sd.tema.model.Book

interface JSONPrinter {
    fun printJSON(books: Set<Book>): String
}