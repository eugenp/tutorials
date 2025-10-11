package com.baeldung.mainclasstest;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.baeldung.Application;

public class ApplicationMockTest {

    @Test
    public void testInitializeApplicationWithMock() {
        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);

            springApplicationMock.when(() -> SpringApplication.run(Application.class, new String[] {}))
                    .thenReturn(mockContext);

            Application.initializeApplication(new String[] {});

            springApplicationMock.verify(() -> SpringApplication.run(Application.class, new String[] {}));
        }
    }
}