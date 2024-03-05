package com.ui.login

import com.ui.GeneralViewTemplate
import com.ui.login.Session
import io.ktor.server.html.Placeholder
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.css.div
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.p

class LoginSuccesfulTemplate(val basicTemplate: GeneralViewTemplate = GeneralViewTemplate()) : Template<HTML> {
  val greeting = Placeholder<FlowContent>()
  override fun HTML.apply() {
    insert(basicTemplate) {
      content {
        div(classes = "mt-2") {
          h2() {
            +"You have been logged in!"
          }
          p{
            insert(greeting)
          }
        }
      }
    }
  }
}