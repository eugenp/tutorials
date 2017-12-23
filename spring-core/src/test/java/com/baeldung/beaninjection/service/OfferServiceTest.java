package com.baeldung.beaninjection.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.beaninjection.BeanConfig;
import com.baeldung.beaninjection.model.Offers;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeanConfig.class, loader = AnnotationConfigContextLoader.class)
public class OfferServiceTest {

    @Autowired
    private OffersService offersService;

    @Test
    public void testOfferService() {
        Offers offers = offersService.getOffers(123);
        assertTrue(offers.getOfferCode()
            .equals("ABC123"));
        assertTrue(offers.getProductRecommendation()
            .equals("Other Products"));
    }
}
