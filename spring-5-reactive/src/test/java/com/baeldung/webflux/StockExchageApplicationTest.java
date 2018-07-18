package com.baeldung.webflux;


import com.baeldung.webflux.reactive.client.StockExchangeClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.Disposable;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.main.web-application-type=reactive", webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StockExchageApplicationTest {
    @Test
    public void testStockExchangeTrading() {
        StockExchangeClient stockExchangeClient = new StockExchangeClient();
        Disposable disposable = stockExchangeClient.lastTrade();
        try{
            Thread.sleep(20000);
        }catch (InterruptedException e){

        }
    }
}
