organization in ThisBuild := "com.baeldung"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

lagomKafkaEnabled in ThisBuild := false

lazy val greetingApi = project("greeting-api")
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslApi
    )
  )

lazy val greetingImpl = project("greeting-impl")
  .enablePlugins(LagomJava)
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra
    )
  )
  .dependsOn(greetingApi, weatherApi)

lazy val weatherApi = project("weather-api")
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      lagomJavadslApi
    )
  )

lazy val weatherImpl = project("weather-impl")
  .enablePlugins(LagomJava)
  .settings(
    version := "1.0-SNAPSHOT"
  )
  .dependsOn(weatherApi)

def project(id: String) = Project(id, base = file(id))
