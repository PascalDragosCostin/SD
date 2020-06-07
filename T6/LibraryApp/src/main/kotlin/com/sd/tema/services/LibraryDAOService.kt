package com.sd.tema.services

import com.sd.tema.interfaces.LibraryDAO
import com.sd.tema.model.Book
import com.sd.tema.model.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.sql.SQLException


class BookRowMapper : RowMapper<Book> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Book {
        return Book(
            Content(
                rs.getString("author"),
                rs.getString("text"),
                rs.getString("name"),
                rs.getString("publisher")
            )
        )
    }
}

@Service
class LibraryDAOService: LibraryDAO {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate


    override fun createBookTable() {
        jdbcTemplate.execute("""CREATE TABLE IF NOT EXISTS books(
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        author VARCHAR(100),
                                        name VARCHAR(100),
                                        publisher VARCHAR(100),
                                        text TEXT)""")
    }

    override fun getBooks(): Set<Book> {
        val result: MutableList<Book> = jdbcTemplate.query("SELECT * FROM books",
            BookRowMapper()
        )
        return result.toSet()
    }

    override fun addBook(book: Book) {
        jdbcTemplate.update("INSERT INTO books(author, name, publisher, text) VALUES (?, ?, ?, ?)", book.author, book.name, book.publisher, book.content)
    }

    override fun findAllByAuthor(author: String): Set<Book> {
        return (this.getBooks().filter { it.hasAuthor(author) }).toSet()
    }

    override fun findAllByTitle(title: String): Set<Book> {
        return (this.getBooks().filter { it.hasTitle(title) }).toSet()
    }

    override fun findAllByPublisher(publisher: String): Set<Book> {
        return (this.getBooks().filter { it.publishedBy(publisher) }).toSet()
    }
}