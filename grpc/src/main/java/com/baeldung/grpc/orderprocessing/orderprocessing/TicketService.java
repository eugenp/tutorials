package com.baeldung.grpc.orderprocessing.orderprocessing;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    public String createTicket(String error) {
        logger.info("Lets create ticket");
        return "TKT-" + new Random().nextInt();
    }

}
