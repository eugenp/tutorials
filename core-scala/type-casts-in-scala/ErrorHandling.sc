import java.io._
import java.net._

import scala.io.Source
import scala.util.{ Failure, Success, Try }

def retrieveBaeldungRSSArticles(): List[String] = {
  val url = new java.net.URL("https://feeds.feedburner.com/Baeldung/")

  val inputStream = url.openStream
  val rssDoc = Source.fromInputStream(inputStream).mkString
  inputStream.close()

  val rssXml = XML.loadString(rssDoc)
  (rssXml \\ "title").map(_.text).filterNot(_ == "Baeldung").toList
}

Try(retrieveBaeldungRSSArticles()) match {
  case Success(lines) if lines.isEmpty =>
    println(s"No articles were found")
  case Success(lines) =>
    println("Current articles:")
    lines.foreach(println)
  case Failure(e: MalformedURLException) =>
    println(s"Wrong URL specified: ${e.getMessage}")
  case Failure(e: UnknownHostException) =>
    println(s"Unknown host specified: ${e.getMessage}")
  case Failure(e: IOException) =>
    println("Network failure: ${e.getMessage}")
  case Failure(t) =>
    t.printStackTrace
}

