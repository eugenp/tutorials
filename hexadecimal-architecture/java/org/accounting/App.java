package org.accounting;

import org.nganga.bank.account.port.CustomerAccountDriverPort;

import io.vertx.core.Vertx;



public class App {

    public static void main(String[] args) {
        Vertx vertx=Vertx.vertx();
        vertx.deployVerticle(new CustomerAccountDriverPort());
        
    }
}
