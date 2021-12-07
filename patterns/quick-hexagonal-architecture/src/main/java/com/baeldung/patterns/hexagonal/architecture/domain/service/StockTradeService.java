package com.baeldung.patterns.hexagonal.architecture.domain.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.baeldung.patterns.hexagonal.architecture.data.StockRepository;
import com.baeldung.patterns.hexagonal.architecture.data.StockTradeRepository;
import com.baeldung.patterns.hexagonal.architecture.data.TradeOrderRepository;
import com.baeldung.patterns.hexagonal.architecture.domain.Stock;
import com.baeldung.patterns.hexagonal.architecture.domain.StockTrade;
import com.baeldung.patterns.hexagonal.architecture.domain.TradeOrder;

@Component
@Transactional
public class StockTradeService implements TradeService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockTradeRepository tradeRepository;

    @Autowired
    private TradeOrderRepository orderRepository;

    @Override
    public Optional<TradeOrder> findTradeOrderById(Serializable id) {
        return orderRepository.findById(id);
    }

    @Override
    public Serializable placeOrder(TradeOrder order) {
        order.validate();

        stockRepository.findById(order.getStock()
            .getTicker())
            .orElseThrow(() -> new IllegalArgumentException("stock does not exist: " + order.getStock()
                .getTicker()));

        TradeOrder savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    @Override
    public boolean cancelOrder(Serializable id) {
        TradeOrder order = orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("order does not exist: " + id));

        order.cancel();
        orderRepository.save(order);
        return true;
    }

    @Override
    public Serializable trade(Serializable orderIdA, Serializable orderIdB) {
        TradeOrder orderA = orderRepository.findById(orderIdA)
            .orElseThrow(() -> new IllegalArgumentException("order does not exist: " + orderIdA));

        TradeOrder orderB = orderRepository.findById(orderIdB)
            .orElseThrow(() -> new IllegalArgumentException("order does not exist: " + orderIdB));

        StockTrade trade = new StockTrade(orderA, orderB);
        if (trade.fulfill()) {
            StockTrade savedTrade = tradeRepository.save(trade);
            return savedTrade.getId();
        } else {
            throw new IllegalArgumentException("could not fulfill trade");
        }
    }

    @Override
    public List<TradeOrder> findMatchingOrders(Serializable orderId) {
        TradeOrder order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("order does not exist: " + orderId));

        TradeOrder copy = copy(order).reverseType();
        int start = 0, maxResults = 10;
        PageRequest page = PageRequest.of(start, maxResults, Sort.by("date"));
        Page<TradeOrder> oldestMatches = orderRepository.findAll(Example.of(copy), page);
        return oldestMatches.getContent();
    }

    @Override
    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public Iterable<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<Stock> findStockById(String ticker) {
        return stockRepository.findById(ticker);
    }

    @Override
    public Iterable<TradeOrder> findAllTradeOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Iterable<StockTrade> findAllStockTrades() {
        return tradeRepository.findAll();
    }

    public <C> C copy(C o) {
        try {
            return deserialize(serialize(o));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] serialize(Object o) throws IOException {
        return serialize(o, new ByteArrayOutputStream()).toByteArray();
    }

    private static <O extends OutputStream> O serialize(Object o, O out) throws IOException {
        try (ObjectOutputStream obIn = new ObjectOutputStream(out)) {
            obIn.writeObject(o);
        }
        return out;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] o) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(o))) {
            return (T) in.readObject();
        }
    }
}
