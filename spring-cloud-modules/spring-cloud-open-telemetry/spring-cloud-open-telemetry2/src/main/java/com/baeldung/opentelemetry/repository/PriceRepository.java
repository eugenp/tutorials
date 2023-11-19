package com.baeldung.opentelemetry.repository;

import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.exception.PriceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class PriceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceRepository.class);

    private final Map<Long, Price> priceMap = new HashMap<>();

    public Price getPrice(Long productId){
        LOGGER.info("Getting Price from Price Repo With Product Id {}", productId);

        if(!priceMap.containsKey(productId)){
            LOGGER.error("Price Not Found for Product Id {}", productId);
            throw new PriceNotFoundException("Product Not Found");
        }

        return priceMap.get(productId);
    }

    @PostConstruct
    private void setupRepo(){
        Price price1 = getPrice(100001L, 12.5, 2.5);
        priceMap.put(100001L, price1);

        Price price2 = getPrice(100002L, 10.5, 2.1);
        priceMap.put(100002L, price2);

        Price price3 = getPrice(100003L, 18.5, 2.0);
        priceMap.put(100003L, price3);

        Price price4 = getPrice(100004L, 18.5, 2.0);
        priceMap.put(100004L, price4);
    }

    private static Price getPrice(long productId, double priceAmount, double discount) {
        Price price = new Price();
        price.setProductId(productId);
        price.setPriceAmount(priceAmount);
        price.setDiscount(discount);
        return price;
    }
}