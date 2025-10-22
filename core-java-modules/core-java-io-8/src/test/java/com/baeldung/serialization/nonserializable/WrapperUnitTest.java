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

public class WrapperUnitTest {

    @Test
    void whenSerializingAWrapper_thenTheClassIsSerialized() throws Exception {
        User user = new User("Graham", "/graham.png");
        assertNotNull(user.profile);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(new UserWrapper(user));

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object read = ois.readObject();

        assertTrue(read instanceof UserWrapper);
        UserWrapper wrapper = (UserWrapper) read;
        User readUser = wrapper.user;
        assertEquals(user.name, readUser.name);
        assertEquals(user.profile, readUser.profile);
    }

    static class User {
        private String name;
        private transient Path profile;

        public User(String name, String profilePath) {
            this.name = name;
            this.profile = FileSystems.getDefault().getPath(profilePath);
        }
    }

    static class UserWrapper implements Serializable {
        private User user;

        public UserWrapper(User user) {
            this.user = user;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(user.name);
            out.writeObject(user.profile.toString());
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            String nameTemp = (String) in.readObject();
            String profilePathTemp = (String) in.readObject();

            this.user = new User(nameTemp, profilePathTemp);
        }
    }
}
