package com.learning.plugins

import com.ui.books.books
import com.ui.cart.cart
import com.ui.checkout.receipt
import com.ui.login.loginView
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
  install(Resources)
  install(StatusPages) {
    exception<Throwable> { call, cause ->
      call.respond(HttpStatusCode.InternalServerError)
      throw cause
    }
  }
  routing {
    books()
    loginView()
    cart()
    receipt()

    get("/") {
      call.respondText("Hello World!")
    }

    // Static plugin. Try to access `/static/index.html`
    static("/static") {
      resources("static")
    }
  }
}
