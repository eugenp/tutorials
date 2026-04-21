package com.baeldung.sonarqubeandjacoco.transientorserializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class UserSerializationUnitTest {

    @Test
    public void givenUser_whenSerialized_thenDeserializesCorrectly() throws IOException, ClassNotFoundException {
        Address address = new Address("123 Main St", "Springfield");
        User user = new User("john_doe", address);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(user);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        User deserializedUser = (User) ois.readObject();
        ois.close();

        assertEquals("john_doe", deserializedUser.getUsername());
        assertEquals("123 Main St", deserializedUser.getAddress().getStreet());
        assertEquals("Springfield", deserializedUser.getAddress().getCity());
        assertNotNull(deserializedUser.getTemporaryCache());
    }

    @Test
    public void givenUser_whenDeserialized_thenTransientFieldIsReinitialized() throws IOException, ClassNotFoundException {
        Address address = new Address("456 Oak Ave", "Shelbyville");
        User user = new User("jane_doe", address);
        user.getTemporaryCache().add("temp_data");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(user);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        User deserializedUser = (User) ois.readObject();
        ois.close();

        assertNotNull(deserializedUser.getTemporaryCache());
        assertTrue(deserializedUser.getTemporaryCache().isEmpty());
    }

    @Test
    public void givenUserPreferences_whenDeserialized_thenTransientServiceIsNull() throws IOException, ClassNotFoundException {
        UserPreferences preferences = new UserPreferences();
        preferences.setService(new PreferenceService());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(preferences);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        UserPreferences deserializedPreferences = (UserPreferences) ois.readObject();
        ois.close();

        assertNull(deserializedPreferences.getService());
    }
}
