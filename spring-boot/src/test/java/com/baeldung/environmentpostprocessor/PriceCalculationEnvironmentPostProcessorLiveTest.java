package com.baeldung.environmentpostprocessor;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.environmentpostprocessor.service.PriceCalculationService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PriceCalculationApplication.class)
public class PriceCalculationEnvironmentPostProcessorLiveTest {
    
    @Autowired
    PriceCalculationService pcService;
    
    
    @Test
    public void WhenSetGrossEnvironmentVariable_ThenTaxApplied() {
        double total = pcService.productTotalPrice(100, 4);
        
        Assert.assertEquals(400.0,total);
    }

}
