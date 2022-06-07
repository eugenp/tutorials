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
            while ((line = reader.readLine())!=null)
            {
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
        File file = new File(filePath)
        def lines = file.readLines()
        return lines
    }

    /**
     * reads file content in string using File.text
     * @param filePath
     * @return
     */
    String readFileString(String filePath) {
        File file = new File(filePath)
        String fileContent = file.text
        return fileContent
    }

    /**
     * reads file content in string with encoding using File.getText
     * @param filePath
     * @return
     */
    String readFileStringWithCharset(String filePath) {
        File file = new File(filePath)
        String utf8Content = file.getText("UTF-8")
        return utf8Content
    }

    /**
     * reads content of binary file and returns byte array
     * @param filePath
     * @return
     */
    byte[] readBinaryFile(String filePath) {
        File file = new File(filePath)
        byte[] binaryContent = file.bytes
        return binaryContent
    }
    
    /**
     * More Examples of reading a file 
     * @return
     */
    def moreExamples() {
        
        //with reader with utf-8
        new File("src/main/resources/utf8Content.html").withReader('UTF-8') { reader ->
            def line
            while ((line = reader.readLine())!=null) { 
                println "${line}"
            }
        }
        
        //collect api
        def list = new File("src/main/resources/fileContent.txt").collect {it}
                
        //as operator
        def array = new File("src/main/resources/fileContent.txt") as String[]
              
        //eachline  
        new File("src/main/resources/fileContent.txt").eachLine { line ->
            println line
        }
        
        //newInputStream with eachLine
        def is = new File("src/main/resources/fileContent.txt").newInputStream()
        is.eachLine {
            println it
        }
        is.close()
        
        //withInputStream
        new File("src/main/resources/fileContent.txt").withInputStream { stream ->
            stream.eachLine { line ->
                println line
            }
        }
    }

}