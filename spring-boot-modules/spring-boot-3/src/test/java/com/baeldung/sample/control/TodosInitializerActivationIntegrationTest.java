package com.baeldung.sample.control;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
class TodosInitializerActivationIntegrationTest {

    @MockBean
    TodosService service;

    @BeforeEach
    void serviceHasEmptyData() {
        when(service.count()).thenReturn(0L);
    }

    @AfterEach
    void serviceHasNoFurtherInteractions() {
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("data initialization is invoked on default profile")
    void testIsInvoked() {
        verify(service).count();
        verify(service, atLeastOnce()).create(any());
    }

}
