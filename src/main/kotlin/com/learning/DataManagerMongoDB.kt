package com.learning

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.or
import com.mongodb.client.model.Filters.regex
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

enum class DataManagerMongoDB {
  INSTANCE;
  val log = LoggerFactory.getLogger(DataManagerMongoDB::class.java)
  val database: MongoDatabase
  val bookCollection: MongoCollection<Book>

  init {
    val pojoCodecRegistry: CodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
    val codecRegistry: CodecRegistry = fromRegistries(
      MongoClientSettings.getDefaultCodecRegistry(),
      pojoCodecRegistry
    )

    val clientSettings = MongoClientSettings.builder()
      .codecRegistry(codecRegistry)
      .build()

    val mongoClient = MongoClients.create(clientSettings)
    database = mongoClient.getDatabase("development")
    bookCollection = database.getCollection(Book::class.java.simpleName, Book::class.java)
    initBooks()
  }

  fun initBooks() {
    bookCollection.insertOne(Book(null, "How to grow apples", "Mr. Appleton", 100.00f))
    bookCollection.insertOne(Book(null, "How to grow oranges", "Mr. Orangeton", 90.00f))
    bookCollection.insertOne(Book(null, "How to grow lemons", "Mr. Lemon", 110.00f))
    bookCollection.insertOne(Book(null, "How to grow pineapples", "Mr. Pineapple", 100.00f))
    bookCollection.insertOne(Book(null, "How to grow pears", "Mr. Pears", 110.00f))
    bookCollection.insertOne(Book(null, "How to grow coconuts", "Mr. Coconut", 130.00f))
    bookCollection.insertOne(Book(null, "How to grow bananas", "Mr. Appleton", 120.00f))
  }

  fun newBook(book: Book): Book {
    bookCollection.insertOne(book)
    return book
  }

  fun updateBook(book: Book): Book {
    val bookfound = bookCollection.find(Document("_id", book.id)).first()
    bookfound?.title = book.title
    bookfound?.author = book.author
    bookfound?.price = book.price
    return bookfound!!
  }

  fun deleteBook(bookid: String): Book {
    val bookfound = bookCollection.find(Document("_id", bookid)).first()
    bookCollection.deleteOne(eq("_id", ObjectId(bookid)))
    return bookfound!!
  }

  fun allBooks(): List<Book> {
    return bookCollection.find().toList()
  }

  fun sortedBooks(sortBy: String, asc: Boolean): List<Book> {
    val pageno = 1
    val pageSize = 1000
    val ascInt: Int = if (asc) 1 else -1
    return bookCollection
      .find()
      .sort(Document(mapOf(Pair(sortBy, ascInt), Pair("_id", -1))))
      .skip(pageno - 1)
      .limit(pageSize)
      .toList()
  }

  fun searchBooks(str: String): List<Book> {
    return bookCollection
      .find(
        or(
          regex("title", ".*$str.*"),
          regex("author", ".*$str.*")
        )
      )
      .sort(Document(mapOf(Pair("title", 1), Pair("_id", -1))))
      .toList()
  }
}