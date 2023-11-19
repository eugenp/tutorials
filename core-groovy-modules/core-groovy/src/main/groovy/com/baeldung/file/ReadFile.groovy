package com.baeldung.file

class ReadFile {

    /**
     * reads file content line by line using withReader and reader.readLine
     * @param filePath
     * @return
     */
    int readFileLineByLine(String filePath) {
        File file = new File(filePath)
        def line, noOfLines = 0;

        file.withReader { reader ->
            while ((line = reader.readLine()) != null) {
                println "${line}"
                noOfLines++
            }
        }

        return noOfLines
    }

    /**
     * reads file content in list of lines
     * @param filePath
     * @return
     */
    List<String> readFileInList(String filePath) {
        return new File(filePath).readLines()
    }

    /**
     * reads file content in string using File.text
     * @param filePath
     * @return
     */
    String readFileString(String filePath) {
        return new File(filePath).text
    }

    /**
     * reads file content in string with encoding using File.getText
     * @param filePath
     * @return
     */
    String readFileStringWithCharset(String filePath) {
        return new File(filePath).getText("UTF-8")
    }

    /**
     * reads content of binary file and returns byte array
     * @param filePath
     * @return
     */
    byte[] readBinaryFile(String filePath) {
        return new File(filePath).bytes
    }

    /**
     * More Examples of reading a file 
     * @return
     */
    def moreExamples() {

        //with reader with utf-8
        new File("src/main/resources/utf8Content.html").withReader('UTF-8') { reader ->
            def line
            while ((line = reader.readLine()) != null) {
                println "${line}"
            }
        }

        // collect api
        def list = new File("src/main/resources/fileContent.txt").collect { it }

        // as operator
        def array = new File("src/main/resources/fileContent.txt") as String[]

        // eachline  
        new File("src/main/resources/fileContent.txt").eachLine { println it }

        //newInputStream with eachLine

        // try-with-resources automatically closes BufferedInputStream resource
        try (def inputStream = new File("src/main/resources/fileContent.txt").newInputStream()) {
            inputStream.eachLine { println it }
        }

        // withInputStream
        new File("src/main/resources/fileContent.txt").withInputStream { stream ->
            stream.eachLine { line ->
                println line
            }
        }
    }
}
