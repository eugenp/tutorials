package com.baeldung.msf4j.msf4jintro;

import org.wso2.msf4j.MicroservicesRunner;

public class Application {
    public static void main(String[] args) {
        new MicroservicesRunner()
            .deploy(new SimpleService())
            .start();
    }
}