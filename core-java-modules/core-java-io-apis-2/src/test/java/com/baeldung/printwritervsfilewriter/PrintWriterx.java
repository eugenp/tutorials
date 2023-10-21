package com.baeldung.printwritervsfilewriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintWriterx {
    
    public static void main(String [] args) throws IOException {
        File fpw = new File("printwriter.txt");
        File fwp = new File("filewriter.txt");
        
        try (PrintWriter pw= new PrintWriter(fpw); FileWriter fw = new FileWriter(fwp);) {
            pw.write("printwriter text\r\n");
            fw.write("printwriter text\r\n");
            
        }
    }

}
