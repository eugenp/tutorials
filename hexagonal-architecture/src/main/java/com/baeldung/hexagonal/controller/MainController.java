package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.adapters.UserRequestAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MainController {

    private UserRequestAdapter userRequestAdapter = new UserRequestAdapter();

    @GetMapping("/")
    public String index() {
        return "Read the README.md";
    }


    @RequestMapping(value = "/stocks/{stockName}/profit", method = GET)
    @ResponseBody
    public String getBestProfitForStock(
            @PathVariable("stockName") String stockName) {
        return userRequestAdapter.calculateBestProfitForStock(stockName);
    }

    @RequestMapping(value = "/stocks/{stockName}", method = GET)
    @ResponseBody
    public int[] getStockPrices(
            @PathVariable("stockName") String stockName) {
        return userRequestAdapter.requestStockPrices(stockName);
    }
}
