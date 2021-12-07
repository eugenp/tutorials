package com.baeldung.patterns.hexagonal.architecture.web;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.patterns.hexagonal.architecture.domain.Stock;
import com.baeldung.patterns.hexagonal.architecture.domain.StockTrade;
import com.baeldung.patterns.hexagonal.architecture.domain.TradeOrder;
import com.baeldung.patterns.hexagonal.architecture.domain.service.StockTradeService;

@RestController
@RequestMapping("stocks")
@SpringBootApplication(scanBasePackages = { "com.baeldung.patterns.hexagonal.architecture" })
public class StockTradeController {
    private StockTradeService service;

    public StockTradeController(StockTradeService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(StockTradeController.class, args);
    }

    @GetMapping
    public Iterable<Stock> get() {
        return service.findAllStocks();
    }

    @GetMapping("{ticker}")
    public Optional<Stock> get(@PathVariable String ticker) {
        return service.findStockById(ticker);
    }

    @PostMapping()
    public void post(@RequestBody Stock stock) {
        service.saveStock(stock);
    }

    @GetMapping("trades")
    public Iterable<StockTrade> getTrades() {
        return service.findAllStockTrades();
    }

    @GetMapping("trades/orders")
    public Iterable<TradeOrder> getTradeOrder() {
        return service.findAllTradeOrders();
    }

    @GetMapping("trades/orders/{id}")
    public Optional<TradeOrder> getTradeOrder(@PathVariable Long id) {
        return service.findTradeOrderById(id);
    }

    @PostMapping("trades/orders")
    public Serializable postTradeOrder(@RequestBody TradeOrder body) {
        return service.placeOrder(body);
    }

    @DeleteMapping("trades/orders/{id}")
    public boolean deleteTradeOrder(@PathVariable Long id) {
        return service.cancelOrder(id);
    }

    @GetMapping("trades/orders/{id}/matches")
    public List<TradeOrder> getTradeOrderMatches(@PathVariable Long id) {
        return service.findMatchingOrders(id);
    }

    @PostMapping("trades/{aId}/{bId}/fulfill")
    public Serializable postStockTrade(@PathVariable Long aId, @PathVariable Long bId) {
        Serializable trade = null;
        try {
            trade = service.trade(aId, bId);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage(), e);
        }
        return trade;
    }
}
