package com.baeldung.serializable_singleton;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Unit test for the EnumSingleton class.
public class EnumSingletonUnitTest {

    private static final String ENUM_SINGLETON_TEST_TXT = "enum_singleton_test.txt";

    // Checks that when an EnumSingleton instance is serialized
    // and then deserialized, its state is preserved.
    @Test
    public void givenEnumSingleton_whenSerializedAndDeserialized_thenStatePreserved() {
        EnumSingleton es1 = EnumSingleton.getInstance();

        es1.setState("State One");

        try (FileOutputStream fos = new FileOutputStream(ENUM_SINGLETON_TEST_TXT);
             ObjectOutputStream oos = new ObjectOutputStream(fos);
             FileInputStream fis = new FileInputStream(ENUM_SINGLETON_TEST_TXT);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            // Serializing.
            oos.writeObject(es1);

            // Deserializing.
            EnumSingleton es2 = (EnumSingleton) ois.readObject();

            // Checking if the state is preserved.
            assertEquals(es1.getState(), es2.getState());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Checking that when an EnumSingleton instance is serialized
    // and then deserialized, then there is still one instance 
    // of the EnumSingleton class in memory.
    @Test
    public void givenEnumSingleton_whenSerializedAndDeserialized_thenOneInstance() {
        EnumSingleton es1 = EnumSingleton.getInstance();

        try (FileOutputStream fos = new FileOutputStream(ENUM_SINGLETON_TEST_TXT);
             ObjectOutputStream oos = new ObjectOutputStream(fos);
             FileInputStream fis = new FileInputStream(ENUM_SINGLETON_TEST_TXT);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Serializing.
            oos.writeObject(es1);

            // Deserializing.
            EnumSingleton es2 = (EnumSingleton) ois.readObject();
    
            // Checking if es1 and es2 are pointing to 
            // the same instance in memory.
            assertEquals(es1, es2);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @AfterAll
    public static void cleanUp() {
        final File removeFile = new File(ENUM_SINGLETON_TEST_TXT);
        if (removeFile.exists()) {
            removeFile.deleteOnExit();
        }
    }
}
