package com.baeldung.java9.process;

import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws Exception {
        ProcessApi procApi = new ProcessApi();
        procApi.createAndDestroyProcess();
        
        procApi.processInfoExample();

        Thread.sleep(40200);
        System.out.println("_______END!___________");
      
    }

}
