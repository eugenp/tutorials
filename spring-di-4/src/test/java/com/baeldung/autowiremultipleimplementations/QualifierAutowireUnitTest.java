package com.baeldung.autowiremultipleimplementations;

import com.baeldung.autowiremultipleimplementations.components.QualifierAutowire;
import com.baeldung.autowiremultipleimplementations.candidates.GoodService;
import com.baeldung.autowiremultipleimplementations.candidates.GoodServiceA;
import com.baeldung.autowiremultipleimplementations.candidates.GoodServiceB;
import com.baeldung.autowiremultipleimplementations.candidates.GoodServiceC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {QualifierAutowire.class, GoodServiceC.class, GoodServiceB.class, GoodServiceA.class})
@ExtendWith(SpringExtension.class)
public class QualifierAutowireUnitTest {

    @Autowired
    private QualifierAutowire qualifierAutowire;

    @Test
    public void testAutowiring() throws NoSuchFieldException, IllegalAccessException {
        assertNotNull(qualifierAutowire, "QualifierAutowire should be autowired");

        GoodService goodServiceA = getGoodServiceField("goodServiceA");
        GoodService goodServiceB = getGoodServiceField("goodServiceB");
        GoodService goodServiceC = getGoodServiceField("goodServiceC");

        // Verify the types of the autowired services
        assertInstanceOf(GoodServiceA.class, goodServiceA, "goodServiceA should be an instance of GoodServiceA");
        assertInstanceOf(GoodServiceB.class, goodServiceB, "goodServiceB should be an instance of GoodServiceB");
        assertInstanceOf(GoodServiceC.class, goodServiceC, "goodServiceC should be an instance of GoodServiceC");

        // Check that the hello message is as expected
        String expectedMessage = "Hello from A! Hello from B! Hello from C!";
        String actualMessage = qualifierAutowire.hello();
        assertEquals(expectedMessage, actualMessage, "Messages should be concatenated correctly from all services");
    }

    private GoodService getGoodServiceField(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = qualifierAutowire.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (GoodService) field.get(qualifierAutowire);
    }
}
