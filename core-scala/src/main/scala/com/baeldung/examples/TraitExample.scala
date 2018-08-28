package com.baeldung.examples

import java.io._

object TraitExample extends App {

    trait Loggable {
        val logger:PrintStream
        def getPrefix():String
        def log(message:String) = logger.println(s"[${getPrefix()}]:${message}")
    }
    object CustomerService extends Loggable {
        override val logger = System.out
        override def getPrefix(): String = "CustomerService"
        def retrieve(id:String) = {
            log(s"Retrieve with id ${id} called")
            // Code to retrieve Customer from DB
        }
    }

    CustomerService.retrieve("C100")

}
