package com.baeldung.file

class ReadFile {

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
    
    public static void main(String[] args) {
        def file = new File("../../src")
        println file.directorySize
    }

}