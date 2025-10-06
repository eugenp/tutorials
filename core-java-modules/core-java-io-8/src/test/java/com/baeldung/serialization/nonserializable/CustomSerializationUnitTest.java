package com.baeldung.serialization.nonserializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class CustomSerializationUnitTest {

    @Test
    void whenCustomSerializationExists_thenTheObjectCanBeSerialized() throws Exception {
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
        assertEquals(user.profile, readUser.profile);
    }

    @Test
    void whenReadResolveExists_thenTheObjectCanBeSerialized() throws Exception {
        User2 user = new User2("Graham", "/graham.png");
        user.getProfile();
        assertNotNull(user.profile);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(user);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object read = ois.readObject();

        assertTrue(read instanceof User2);
        User2 readUser = (User2) read;
        assertEquals(user.name, readUser.name);
        assertEquals(user.profilePath, readUser.profilePath);
        assertEquals(user.profile, readUser.profile);
    }

    static class User implements Serializable {
        private String name;
        private String profilePath;
        private Path profile;

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

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(name);
            out.writeObject(profilePath);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            String nameTemp = (String) in.readObject();
            String profilePathTemp = (String) in.readObject();

            this.name = nameTemp;
            this.profilePath = profilePathTemp;
            this.profile = FileSystems.getDefault().getPath(profilePath);
        }
    }

    static class User2 implements Serializable {
        private String name;
        private String profilePath;
        private transient Path profile;

        public User2(String name, String profilePath) {
            this.name = name;
            this.profilePath = profilePath;
        }

        public Path getProfile() {
            if (this.profile == null) {
                this.profile = FileSystems.getDefault().getPath(profilePath);
            }

            return this.profile;
        }

        public Object readResolve() {
            User2 result = new User2(this.name, this.profilePath);
            result.profile = FileSystems.getDefault().getPath(profilePath);
            return result;
        }
    }
}
