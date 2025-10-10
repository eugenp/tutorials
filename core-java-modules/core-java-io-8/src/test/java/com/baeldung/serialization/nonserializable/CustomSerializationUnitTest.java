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
    void givenAClassWithCustomSerialization_whenSerializingTheClass_thenItSerializesCorrectly() throws Exception {
        CustomSerializationUser user = new CustomSerializationUser("Graham", "/graham.png");
        assertNotNull(user.profile);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(user);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object read = ois.readObject();

        assertTrue(read instanceof CustomSerializationUser);
        CustomSerializationUser readUser = (CustomSerializationUser) read;
        assertEquals(user.name, readUser.name);
        assertEquals(user.profile, readUser.profile);
    }

    @Test
    void givenAClassWithReadResolve_whenDeserializingTheClass_thenItDeserializesCorrectly() throws Exception {
        ReadResolveUser user = new ReadResolveUser("Graham", "/graham.png");
        assertNotNull(user.profile);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(user);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object read = ois.readObject();

        assertTrue(read instanceof ReadResolveUser);
        ReadResolveUser readUser = (ReadResolveUser) read;
        assertEquals(user.name, readUser.name);
        assertEquals(user.profilePath, readUser.profilePath);
        assertEquals(user.profile, readUser.profile);
    }

    static class CustomSerializationUser implements Serializable {
        private String name;
        private Path profile;

        public CustomSerializationUser(String name, String profilePath) {
            this.name = name;
            this.profile = FileSystems.getDefault().getPath(profilePath);
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(name);
            out.writeObject(profile.toString());
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            String nameTemp = (String) in.readObject();
            String profilePathTemp = (String) in.readObject();

            this.name = nameTemp;
            this.profile = FileSystems.getDefault().getPath(profilePathTemp);
        }
    }

    static class ReadResolveUser implements Serializable {
        private String name;
        private String profilePath;
        private transient Path profile;

        public ReadResolveUser(String name, String profilePath) {
            this.name = name;
            this.profilePath = profilePath;
            this.profile = FileSystems.getDefault().getPath(profilePath);
        }

        public Object readResolve() {
            this.profile = FileSystems.getDefault().getPath(this.profilePath);
            return this;
        }
    }
}
