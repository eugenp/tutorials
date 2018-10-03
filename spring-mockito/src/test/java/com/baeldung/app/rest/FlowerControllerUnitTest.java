package com.baeldung.app.rest;

import com.baeldung.app.api.Flower;
import com.baeldung.domain.service.FlowerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlowerControllerUnitTest {

    @Mock
    private FlowerService flowerService;

    @InjectMocks
    private FlowerController flowerController;

    @Test
    public void isAFlower_withMockito_OK() {
        when(flowerService.analize(eq("violetta"))).thenReturn("Flower");

        String response = flowerController.isAFlower("violetta");

        Assert.assertEquals("Flower", response);
    }

    @Test
    public void isABigFlower_withMockito_OK() {
        when(flowerService.isABigFlower(eq("violetta"), anyInt())).thenReturn(true);

        Flower flower = new Flower("violetta", 15);

        Boolean response = flowerController.isABigFlower(flower);

        Assert.assertTrue(response);
    }
}
