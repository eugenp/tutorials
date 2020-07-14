package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.core.StockPriceCore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MainController {

    private StockPriceCore stockPriceCore = new StockPriceCore();

    @GetMapping("/")
    public String index() {
        return "Read the README.md";
    }


    @RequestMapping(value = "/stocks/{stockName}/profit", method = GET)
    @ResponseBody
    public String getBestProfitForStock(
            @PathVariable("stockName") String stockName) {

        String message = String.format("Best possible profit for stock %s is %d", stockName, stockPriceCore.getBestPossibleProfit(stockName));
        return message;
    }

}
