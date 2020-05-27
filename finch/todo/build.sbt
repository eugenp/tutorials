scalaVersion := "2.12.11"

name := "todo"
organization := "com.baeldung"
version := "1.0"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  // NOTE: using finchx instead of finch allows us to use polymorphic Endpoint[F[_], T]
  "com.github.finagle" %% "finchx-core" % "0.31.0",
  "com.github.finagle" %% "finchx-circe" % "0.31.0",
  
  "io.circe" %% "circe-generic" % "0.9.0",
  "org.typelevel" %% "cats-effect" % "2.1.3",
  "org.typelevel" %% "cats-core" % "2.1.1",
  "org.xerial" % "sqlite-jdbc" % "3.31.1",
  "org.tpolecat" %% "doobie-core" % "0.8.8",
)
