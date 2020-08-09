package yamltopojo.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yamltopojo.demo.service.SizeConverterService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TshirtSizeControllerTest {

    @Mock
    private SizeConverterService service;

    @InjectMocks
    private TshirtSizeController tested;

    @Test
    void convertSize() {

        // Given
        String label = "S";
        String countryCode = "fr";
        int result = 36;

        //
        when(service.convertSize(label, countryCode)).thenReturn(result);
        int actual = tested.convertSize(label, countryCode);

        // Then
        assertEquals(actual, result);

    }
}