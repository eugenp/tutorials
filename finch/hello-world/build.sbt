scalaVersion := "2.12.11"

name := "hello-world"
organization := "com.baeldung"
version := "1.0"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % "0.22.0",
  "com.github.finagle" %% "finch-circe" % "0.22.0",
  "io.circe" %% "circe-generic" % "0.9.0"
)
