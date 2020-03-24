name := """async"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

// comment out the original line
libraryDependencies += guice
libraryDependencies += javaWs
