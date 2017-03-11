/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.examples.guice;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 *
 * @author Tayo
 */
public class Communication {

    final Date start = new Date();

    @Inject
    private Logger logger;

    @Inject
    private DefaultCommunicator communicator;

    public Communication(Boolean keepRecords) {
        if (keepRecords) {
            logger.info("keeping records");
        }
    }

    public boolean sendMessage(String message) {
      
        return communicator.sendMessage(message);
    }
 
    public DefaultCommunicator getCommunicator() {
        return this.communicator;
    }

}
