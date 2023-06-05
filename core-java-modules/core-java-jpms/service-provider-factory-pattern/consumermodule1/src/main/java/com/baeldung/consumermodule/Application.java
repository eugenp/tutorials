package com.baeldung.consumermodule;

import com.baeldung.servicemodule.external.TextService;
import com.baeldung.servicemodule.external.TextServiceFactory;

public class Application {
    
    public static void main(String args[]) {
        TextService textService = TextServiceFactory.getTextService("lowercase");
        System.out.println(textService.processText("Hello from Baeldung!"));
    }
    
}
