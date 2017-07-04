package com.baeldung.deserialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;

import org.junit.Ignore;
import org.junit.Test;

public class DeserializationUnitTest {

    private static final String FILE_PATH = "./specs.txt";
    private static long userDefinedSerialVersionUID = 1234567L;

    /**
     * Tests the deserialization of the original "AppleProduct" (no exceptions are thrown)
     * 
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Test
    public void testDeserializeObj_compatible() throws FileNotFoundException, ClassNotFoundException, IOException {

        assertEquals(userDefinedSerialVersionUID, AppleProduct.getSerialVersionUID());
        
        AppleProduct appleProduct = new AppleProduct();
        
        // serializes the "AppleProduct" object
        SerializationUtility.serializeObjectToFile(appleProduct);

        // deserializes the "AppleProduct" object
        DeserializationUtility.deSerializeObjectFromFile(FILE_PATH);
    }

    /**
     * Tests the deserialization of the modified (non-compatible) "AppleProduct".
     * The test should result in an InvalidClassException being thrown.
     * 
     * Note: to run this test: 
     *          1. Modify the value of the serialVersionUID identifier in AppleProduct.java
     *          2. Remove the @Ignore annotation
     *          3. Run the test individually (do not run the entire set of tests)
     *          4. Revert the changes made in 1 & 2 (so that you're able to re-run the tests successfully)
     *          
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Ignore
    @Test(expected = InvalidClassException.class)
    public void testDeserializeObj_incompatible() throws FileNotFoundException, ClassNotFoundException, IOException {

        assertNotEquals(userDefinedSerialVersionUID, AppleProduct.getSerialVersionUID());
        
        // attempts to deserialize the "AppleProduct" object
        DeserializationUtility.deSerializeObjectFromFile(FILE_PATH);
    }

}
