package com.baeldung;

import com.baeldung.application.service.GamerAccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HexagonalArchitectureApplicationTests {

    @MockBean
    GamerAccountService gamerAccountService;

    @Test
    void should_return_saved_gamerAccount_service(){

        gamerAccountService.AddRankLevel(1L, 2);

    }

    @Test
    void should_return_true_to_reduce_rankLevel_service() {

        Mockito.when(gamerAccountService.reduceRankLevel(1L,1)).thenReturn(true);

        Boolean isReduceRankLevel = gamerAccountService.reduceRankLevel(1L,1);

        assertTrue(isReduceRankLevel);
    }

    @Test
    void contextLoads() {
    }

}
