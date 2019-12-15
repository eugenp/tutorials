name := """websockets"""
organization := "com.baeldung"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.0"

lazy val akkaVersion = "2.6.0-M8"
lazy val akkaHttpVersion = "10.1.10"

libraryDependencies += guice
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpVersion
libraryDependencies += "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.8" % "provided"
libraryDependencies += "junit" % "junit" % "4.12"

PlayKeys.devSettings += "play.server.http.idleTimeout" -> "infinite"
