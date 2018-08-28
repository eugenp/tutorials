package com.baeldung.examples

object ExceptionHandlingExamples extends App {
    import java.io._
    def printLines(filename:String) = {
        var reader:Option[BufferedReader] = None
        try {
            reader = Some(new BufferedReader(new FileReader(new File(filename))))
            reader.get.lines().forEach(println(_))
        } catch {
            case e:FileNotFoundException => println(s"There was no file named ${filename}")
            case e:Throwable => throw new Exception("Some error occurred", e)
        } finally {
            if (reader.isDefined) reader.get.close()
        }
    }
    printLines("test.txt")

}
