package com.baeldung.hidden.classes;


import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup.ClassOption;
import java.lang.reflect.Method;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiddenClassUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(HiddenClassUnitTest.class);

    @Test
    void whenInvokeMethodOnHiddenClass_thenSuccess() {
        // Initiate lookup class
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // Create a byte code of a class

        Class<?> clazz = HiddenClass.class;
        String className = clazz.getName();
        String classAsPath = className.replace('.', '/') + ".class";
        InputStream stream = clazz.getClassLoader()
            .getResourceAsStream(classAsPath);

        try {
            // Define hidden class with byte code
            Class<?> hiddenClass = lookup.defineHiddenClass(IOUtils.toByteArray(stream), true, ClassOption.NESTMATE)
                .lookupClass();
            Object hiddenClassObject = hiddenClass.getConstructor()
                .newInstance();

            Method method = hiddenClassObject.getClass()
                .getDeclaredMethod("convertToUpperCase", String.class);

            Assertions.assertEquals(true, hiddenClass.isHidden());

            Assertions.assertEquals("HELLO", method.invoke(hiddenClassObject, "Hello"));

            Assertions.assertEquals(this.getClass()
                .getClassLoader(), hiddenClass.getClassLoader());

            Assertions.assertEquals(null, hiddenClass.getCanonicalName());      
            
            Assertions.assertThrows(ClassNotFoundException.class, () -> Class.forName(hiddenClass.getName()));
            
            Assertions.assertThrows(ClassNotFoundException.class, () -> lookup.findClass(hiddenClass.getName()));

        } catch (Exception e) {
            LOG.error("Couldn't instantiate hidden class" + e);
        }
    }

}
