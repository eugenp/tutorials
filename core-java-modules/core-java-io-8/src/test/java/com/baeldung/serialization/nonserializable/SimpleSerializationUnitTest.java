package com.baeldung.serialization.nonserializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class SimpleSerializationUnitTest {
    @Test
    void whenSerializingASerializableClass_thenItCanDeserializeCorrectly() throws Exception {
        SerializableUser user = new SerializableUser("Graham", "/graham.png");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(user);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object read = ois.readObject();

        assertTrue(read instanceof SerializableUser);
        SerializableUser readUser = (SerializableUser) read;
        assertEquals(user.name, readUser.name);
        assertEquals(user.profilePath, readUser.profilePath);
    }

    @Test
    void whenSerializingANonSerializableClass_thenItCanDeserializeCorrectly() throws Exception {
        NonSerializableUser user = new NonSerializableUser("Graham", "/graham.png");
        user.getProfile();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        assertThrows(NotSerializableException.class, () -> oos.writeObject(user));
    }

    static class SerializableUser implements Serializable {
        private String name;
        private String profilePath;

        public SerializableUser(String name, String profilePath) {
            this.name = name;
            this.profilePath = profilePath;
        }
    }

    static class NonSerializableUser implements Serializable {
        private String name;
        private String profilePath;
        private Path profile;

        public NonSerializableUser(String name, String profilePath) {
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
