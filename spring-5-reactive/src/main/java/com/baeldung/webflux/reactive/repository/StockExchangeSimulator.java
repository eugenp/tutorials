package com.baeldung.webflux.reactive.repository;

import com.baeldung.webflux.reactive.model.StockQuote;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Random;

@Service
public class StockExchangeSimulator implements StockExchange {
    public static String[] stockNames = {"PPL", "OGDC", "KE", "FFCL"};
    public static double[] prices = {10.0, 20.0, 30.0, 40.0};

    public StockExchangeSimulator() {
    }

    public Flux<StockQuote> trading(){
        return Flux.<StockQuote>create(fluxSink -> {
            while(true){
                StockQuote sq = simulateTrading();
                System.out.println(sq.toString());
                fluxSink.next(sq);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){

                }

            }
        }).share();
    }

    private static StockQuote simulateTrading(){
        Random r = new Random();
        int name = r.nextInt(4);

        switch (name){
            case 0:
                return new StockQuote(stockNames[0], prices[0] + r.nextDouble());
            case 1:
                return new StockQuote(stockNames[1], prices[1] + r.nextDouble());
            case 2:
                return new StockQuote(stockNames[2], prices[2] + r.nextDouble());
            case 3:
                return new StockQuote(stockNames[3], prices[3] + r.nextDouble());
        }
        return null;
    }
}
