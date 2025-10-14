package com.baeldung.serialization.nonserializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class TransientUnitTest {
    @Test
    void whenSerializingATransient_thenThatFieldIsNotDeserialized() throws Exception {
        User user = new User("Graham", "/graham.png");
        user.getProfile();
        assertNotNull(user.profile);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(user);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object read = ois.readObject();

        assertTrue(read instanceof User);
        User readUser = (User) read;
        assertEquals(user.name, readUser.name);
        assertEquals(user.profilePath, readUser.profilePath);
        assertNull(readUser.profile);
    }

    static class User implements Serializable {
        private String name;
        private String profilePath;
        private transient Path profile;

        public User(String name, String profilePath) {
            this.name = name;
            this.profilePath = profilePath;
        }

        public Path getProfile() {
            if (this.profile == null) {
                this.profile = FileSystems.getDefault().getPath(profilePath);
            }

            return this.profile;
        }
    }
}
