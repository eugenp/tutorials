package com.baeldung.mockito.argumentmatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.InvalidUseOfMatchersException;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.mockito.argumentmatchers.controller.FlowerController;
import com.baeldung.mockito.argumentmatchers.service.FlowerService;

@ExtendWith(MockitoExtension.class)
class FlowerControllerUnitTest {

    @InjectMocks
    private FlowerController flowerController;

    @Mock
    private FlowerService flowerService;

    @Test
    void givenPoppyFlower_whenUsingDoReturn_thenCorrect() {
        doReturn("Flower").when(flowerService).analyze("poppy");

        String response = flowerController.isAFlower("poppy");
        assertThat(response).isEqualTo("Flower");
    }

    @Test
    void givenAnyFlower_whenUsingArgumentMatcher_thenCorrect() {
        when(flowerService.analyze(anyString())).thenReturn("Flower");

        String response = flowerController.isAFlower("violetta");
        assertThat(response).isEqualTo("Flower");
    }

    @Test
    void givenIncorrectMatchers_whenUsingArgumentMatchers_thenThrowsError() {
        assertThrows(InvalidUseOfMatchersException.class, 
            () -> when(flowerService.isABigFlower("poppy", anyInt())).thenReturn(true));
    }

    @Test
    void givenCorrectMatchers_whenUsingArgumentMatchers_thenCorrect() {
        when(flowerService.isABigFlower(eq("poppy"), anyInt())).thenReturn(true);

        Flower flower = new Flower("poppy", 15);

        Boolean response = flowerController.isABigFlower(flower);
        assertThat(response).isTrue();
    }

    @Test
    void givenMatchersOutsideofStubbing_whenUsingMatchersAsReturnValue_thenThrowsError() {
        flowerController.isAFlower("poppy");

        String orMatcher = or(eq("poppy"), endsWith("y"));
        assertThrows(InvalidUseOfMatchersException.class, 
            () -> verify(flowerService).analyze(orMatcher));
    }

    @Test
    void givenMatchersAsOngoingStubbing_whenUsingMatchers_thenCorrect() {
        flowerController.isAFlower("poppy");

        verify(flowerService).analyze(or(eq("poppy"), endsWith("y")));
    }
}
