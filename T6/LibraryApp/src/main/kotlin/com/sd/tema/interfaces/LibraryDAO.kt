package com.sd.tema.interfaces

import com.sd.tema.model.Book

interface LibraryDAO {
    fun createBookTable()
    fun getBooks(): Set<Book>
    fun addBook(book: Book)
    fun findAllByAuthor(author: String): Set<Book>
    fun findAllByTitle(title: String): Set<Book>
    fun findAllByPublisher(publisher: String): Set<Book>
}