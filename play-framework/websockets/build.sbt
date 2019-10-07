name := """websockets"""
organization := "com.baeldung"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.0"

lazy val akkaVersion = "2.6.0-M8"

libraryDependencies ++= Seq(
  guice,
  ws,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-jackson" % "10.1.10",
  "com.typesafe.akka" %% "akka-http"   % "10.1.10",
  "org.projectlombok" % "lombok" % "1.18.8" % "provided",
  "junit" % "junit" % "4.12")
