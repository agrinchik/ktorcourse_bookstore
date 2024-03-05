package com.ui.login

import com.learning.Constants
import com.learning.SecurityHandler
import com.ui.home.HomeTemplate
import com.ui.login.LoginTemplate
import io.ktor.http.content.PartData
import io.ktor.server.application.call
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.request.receiveMultipart
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.sessions
import org.slf4j.LoggerFactory
import com.ui.Endpoints
import java.util.UUID

data class Session(val username: String, val uniqueID: String = UUID.randomUUID().toString())

fun Route.loginView() {
  get(Endpoints.LOGIN.url) {
    call.respondHtmlTemplate(LoginTemplate()) {

    }
  }
  get(Endpoints.HOME.url) {
    call.respondHtmlTemplate(HomeTemplate()) {

    }
  }
  get(Endpoints.LOGOUT.url) {
    call.sessions.clear(Constants.COOKIE_NAME.value)
    call.respondHtmlTemplate(LogoutTemplate()) {

    }
  }

  post(Endpoints.DOLOGIN.url) {
    val log = LoggerFactory.getLogger("LoginView")
    val multipart = call.receiveMultipart()

    call.request.headers.forEach { s, list ->
      log.info("key $s values $list")
    }
    var username: String = ""
    var password: String = ""
    while (true) {
      val part = multipart.readPart() ?:break
      when (part) {
        is PartData.FormItem -> {
          log.info("FormItem: ${part.name} = ${part.value}")
          if (part.name == "username") {
            username = part.value
          }
          if (part.name == "password") {
            password = part.value
          }
        }
        is PartData.FileItem -> {
          log.info("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
        }
        else -> {

        }
      }
      part.dispose()
    }
    if(SecurityHandler().isValid(username, password)) {
      call.sessions.set(Constants.COOKIE_NAME.value, Session(username))
      call.respondHtmlTemplate(
        LoginSuccesfulTemplate()
      ) {
        greeting {
          +"You are logged in as $username and a cookie has been created"
        }
      }
    } else {
      call.respondHtmlTemplate(LoginTemplate()) {
        greeting {
          +"Username or password was invalid... Try again."
        }
      }
    }
  }
}
