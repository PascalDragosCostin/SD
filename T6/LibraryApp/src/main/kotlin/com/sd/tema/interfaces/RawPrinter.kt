package com.sd.tema.interfaces

import com.sd.tema.model.Book

interface RawPrinter {
    fun printRaw(books: Set<Book>): String
}