package com.ui

enum class Endpoints(val url: String) {
  LOGIN("/html/login"),
  LOGOUT("/html/logout"),
  DOLOGIN("/html/dologin"),
  HOME("/html/home"),
  BOOKS("/html/books"),
  CART("/html/cart");
}