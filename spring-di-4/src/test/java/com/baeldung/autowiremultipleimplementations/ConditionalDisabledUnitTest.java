package com.baeldung.autowiremultipleimplementations;

import com.baeldung.autowiremultipleimplementations.candidates.GoodService;
import com.baeldung.autowiremultipleimplementations.candidates.GoodServiceE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {GoodServiceE.class})
@TestPropertySource(properties = {"feature.toggle=disabled"})
public class ConditionalDisabledUnitTest {

    @Autowired(required = false)
    private GoodService goodService;

    @Test
    void testServiceWhenFeatureDisabled() {
        assertNull(goodService, "GoodService should not be autowired when feature.toggle is disabled");
    }
}
