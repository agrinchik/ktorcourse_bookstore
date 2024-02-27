package com.learning

import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

@Resource("/api/book/list")
class BookListResource(val sortby: String, val asc: Boolean)

fun Route.books() {
  val dataManager = DataManager()

  authenticate("bookStoreAuth") {
    get<BookListResource>() {
      call.respond(dataManager.sortedBooks(it.sortby, it.asc))
    }
  }

//  route("/book") {
//    get("/") {
//      call.respond(dataManager.allBooks())
//    }
//
//    put("/") {
//      val book = call.receive(Book::class)
//      val newBook = dataManager.newBook(book)
//      call.respond(newBook)
//    }
//
//    post("/{id}") {
//      val id = call.parameters["id"]
//      val book = call.receive(Book::class)
//      val updatedBook = dataManager.updateBook(book)
//      call.respond(updatedBook)
//    }
//
//    delete("/{id}") {
//      val id = call.parameters["id"].toString()
//      val deletedBook = dataManager.deleteBook(id)
//      call.respond(deletedBook)
//    }
//  }
}