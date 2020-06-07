package com.sd.tema.interfaces

import com.sd.tema.model.Book

interface HTMLPrinter {
    fun printHTML(books: Set<Book>): String
}