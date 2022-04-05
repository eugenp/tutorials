package com.baeldung.ndc.controller;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.ndc.Investment;
import com.baeldung.ndc.service.InvestmentService;

@RestController
public class Log4J2Controller {
    @Autowired
    @Qualifier("log4j2InvestmentService")
    private InvestmentService log4j2BusinessService;

    @RequestMapping(value = "/ndc/log4j2", method = RequestMethod.POST)
    public ResponseEntity<Investment> postPayment(@RequestBody Investment investment) {
        // Add transactionId and owner to NDC
        ThreadContext.push("tx.id=" + investment.getTransactionId());
        ThreadContext.push("tx.owner=" + investment.getOwner());

        try {
            log4j2BusinessService.transfer(investment.getAmount());
        } finally {
            // take out owner from the NDC stack
            ThreadContext.pop();

            // take out transactionId from the NDC stack
            ThreadContext.pop();

            ThreadContext.clearAll();
        }
        return new ResponseEntity<Investment>(investment, HttpStatus.OK);
    }
}