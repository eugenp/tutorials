package com.baeldung;

import io.vertx.core.Vertx;
import com.baeldung.adapter.CustomerAccountApi;



public class Main {

    public static void main(String[] args) {
        Vertx vertx=Vertx.vertx();
        vertx.deployVerticle(new CustomerAccountApi());   	
        
    }
}
