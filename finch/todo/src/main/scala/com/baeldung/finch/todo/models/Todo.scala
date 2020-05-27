package com.baeldung.finch.todo.models

case class Todo(
  id: Option[Int],
  name: String,
  description: String,
  done: Boolean
)