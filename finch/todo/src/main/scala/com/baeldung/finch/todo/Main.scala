package com.baeldung.finch.todo

import com.twitter.finagle.{Http, ListeningServer}
import com.twitter.util.Future

import cats.effect.{IO, IOApp, Blocker, ExitCode, Resource}
import cats.effect.syntax._

import io.finch._
import io.finch.catsEffect._
import io.finch.circe._

import io.circe.generic.auto._

import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import doobie._
import doobie.implicits._

import com.baeldung.todo.models.Todo

object Main extends IOApp {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val xa = Transactor.fromDriverManager[IO](
    "org.sqlite.JDBC", "jdbc:sqlite:data.db", "", "",
    Blocker.liftExecutionContext(ExecutionContexts.synchronous)
  )

  val createDb = sql"""
    CREATE TABLE IF NOT EXISTS todo (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT NOT NULL,
      description TEXT,
      done NUMERIC
    )
    """
    .update
    .run


  val todosPath: String = "todos"

  val createTodo: Endpoint[IO, Todo] = post(todosPath :: jsonBody[Todo]) { todo: Todo =>
    for {
      id <- sql"insert into todo (name, description, done) values (${todo.name}, ${todo.description}, ${todo.done})"
        .update
        .withUniqueGeneratedKeys[Int]("id")
        .transact(xa)
      created <- sql"select * from todo where id = $id"
        .query[Todo]
        .unique
        .transact(xa)
    } yield Created(created)
  }

  val getTodo: Endpoint[IO, Todo] = get(todosPath :: path[Int]) { id: Int =>
    for {
      todos <- sql"select * from todo where id = $id"
        .query[Todo]
        .to[Set]
        .transact(xa)
    } yield todos.headOption match {
      case None => NotFound(new Exception("Record not found"))
      case Some(todo) => Ok(todo)
    }
  }

  val updateTodo: Endpoint[IO, Todo] = put(todosPath :: path[Int] :: jsonBody[Todo]) { (id: Int, todo: Todo) => 
    for {
      _ <- sql"update todo set name = ${todo.name}, description = ${todo.description}, done = ${todo.done} where id = $id"
        .update
        .run
        .transact(xa)
       
      todo <- sql"select * from todo where id = $id"
        .query[Todo]
        .unique
        .transact(xa)
    } yield Ok(todo)
  }

  val getTodos: Endpoint[IO, Seq[Todo]] = get(todosPath) {
    for {
      todos <- sql"select * from todo"
        .query[Todo]
        .to[Seq]
        .transact(xa)
    } yield Ok(todos)
  }

  val deleteTodo: Endpoint[IO, Unit] = delete(todosPath :: path[Int]) { id: Int =>
    for {
      _ <- sql"delete from todo where id = $id"
        .update
        .run
        .transact(xa)
    } yield NoContent
  }

  def startServer: IO[ListeningServer] = 
  createDb.transact(xa).flatMap { _ =>
    IO(
      Http.server
        .serve(":8081", (
          createTodo 
          :+: getTodo 
          :+: updateTodo 
          :+: deleteTodo 
          :+: getTodos
        ).toServiceAs[Application.Json])
    )
  }

  def run(args: List[String]): IO[ExitCode] = {
    val server = Resource.make(startServer)(s =>
      IO.suspend(implicitly[ToAsync[Future, IO]].apply(s.close()))
    )

    server.use(_ => IO.never).as(ExitCode.Success)
  }
}