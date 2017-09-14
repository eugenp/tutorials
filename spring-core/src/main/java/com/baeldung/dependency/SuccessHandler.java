package com.baeldung.dependency;

public class SuccessHandler {

    String successMsg="Successfully handled transaction";

    public void handle(){
        System.out.print(successMsg);
    }
}
