package com.ui.home

import com.ui.GeneralViewTemplate
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.p


class HomeTemplate() : Template<HTML> {
  val basicTemplate: GeneralViewTemplate = GeneralViewTemplate()
  override fun HTML.apply() {
    insert(basicTemplate) {
      content {
        div(classes = "mt-2") {
          h2() {
            +"Welcome to the Bookstore"
          }
          p{
            +"- We have many good deals on a lot of different topics"
          }
          p{
            +"Let us know if you are looking for something that we do not currently have."
          }
        }
      }
    }
  }
}