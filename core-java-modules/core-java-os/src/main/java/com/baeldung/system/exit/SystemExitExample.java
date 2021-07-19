package com.baeldung.system.exit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SystemExitExample {

    public void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("file.txt"));
            System.out.println(br.readLine());
            br.close();
        } catch (IOException e) {
            System.exit(2);
        } finally {
            System.out.println("Exiting the program");
        }
    }
}
