package org.baeldung.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// to test csrf
@Controller
public class BankController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    @ResponseBody
    public int transfer(@RequestParam("accountNo") final int accountNo, @RequestParam("amount") final int amount) {
        logger.info("Transfer to {}", accountNo);
        return amount;
    }

    // write - just for test
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestParam("accountNo") final int accountNo, @RequestParam("amount") final int amount) {
        logger.info("Transfer to {}", accountNo);

    }
}
