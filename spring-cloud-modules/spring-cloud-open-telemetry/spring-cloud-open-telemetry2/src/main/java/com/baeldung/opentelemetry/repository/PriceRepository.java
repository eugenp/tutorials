package com.baeldung.opentelemetry.repository;

import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.exception.PriceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PriceRepository {

    private final Logger logger = LoggerFactory.getLogger(PriceRepository.class);

    private final Map<Long, Price> priceMap = new HashMap<>();

    public PriceRepository() {
        setupRepo();
    }

    public Price getPrice(Long productId){
        logger.info("Getting Price from Price Repo With Product Id {}", productId);

        if(!priceMap.containsKey(productId)){
            logger.error("Price Not Found for Product Id {}", productId);
            throw new PriceNotFoundException("Product Not Found");
        }

        return priceMap.get(productId);
    }

    private void setupRepo(){

        Price price1 = new Price();
        price1.setProductId(100001L);
        price1.setPriceAmount(12.5);
        price1.setDiscount(2.5);
        priceMap.put(100001L, price1);

        Price price2 = new Price();
        price2.setProductId(100002L);
        price2.setPriceAmount(10.5);
        price2.setDiscount(2.1);
        priceMap.put(100002L, price2);


        Price price3 = new Price();
        price3.setProductId(100003L);
        price3.setPriceAmount(18.5);
        price3.setDiscount(2.0);
        priceMap.put(100003L, price3);

        Price price4 = new Price();
        price4.setProductId(100004L);
        price4.setPriceAmount(18.5);
        price4.setDiscount(2.0);
        priceMap.put(100004L, price4);
    }

}