name := """student-api"""
organization := "com.baeldung"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.0"

libraryDependencies += guice

// https://mvnrepository.com/artifact/org.projectlombok/lombok
libraryDependencies += "org.projectlombok" % "lombok" % "1.18.8" % "provided"
