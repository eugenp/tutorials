package com.baeldung.autowiremultipleimplementations;

import com.baeldung.autowiremultipleimplementations.components.PrimaryAutowire;
import com.baeldung.autowiremultipleimplementations.candidates.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {PrimaryAutowire.class, GoodServiceD.class, GoodServiceC.class,
        GoodServiceB.class, GoodServiceA.class})
@ActiveProfiles("dev")
public class ProfilesAutowireUnitTest {

    @Autowired
    private GoodService goodService;

    @Test
    public void goodServiceDIsAutowiredCorrectly() {
        assertNotNull(goodService, "GoodService should be autowired");
        assertInstanceOf(GoodServiceD.class, goodService,
                "Autowired GoodService should be an instance of GoodServiceD under 'dev' profile");
    }
}
