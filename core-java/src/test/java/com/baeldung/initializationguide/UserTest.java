package com.baeldung.initializationguide;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserTest {


    Path userData;

    @Before
    public void init() throws IOException {
        userData = Paths.get("src/test/resources/user.data");
        if (!Files.exists(userData))
            Files.createFile(userData);
    }

    @Test
    public void givenUserInstance_whenIntializedWithNew_thenInstanceIsNotNull() {
        User user = new User("Alice", 1);
        assertThat(user).isNotNull();
    }

    @Test
    public void givenUserInstance_whenInitializedWithReflection_thenInstanceIsNotNull() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        User user = User.class.getConstructor(String.class, int.class)
            .newInstance("Alice", 2);
        assertThat(user).isNotNull();
    }

    @Test
    public void givenUserInstance_whenCopiedWithClone_thenExactMatchIsCreated() throws CloneNotSupportedException {
        User user = new User("Alice", 3);
        User clonedUser = (User) user.clone();
        assertThat(clonedUser).isEqualTo(user);
    }

    @Test
    public void givenFileWithBitsRepresentingUser_whenDeserialized_thenUserIsCreated() throws FileNotFoundException, IOException, ClassNotFoundException {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(userData.toFile())))) {
            User user = new User("Tom", 0);
            out.writeObject(user);
        }
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(userData.toFile())))) {
            User deserializedUser = (User) in.readObject();
            assertThat(deserializedUser).isNotNull();
        }
    }

    @Test
    public void givenUserInstance_whenValuesAreNotInitialized_thenUserNameAndIdReturnDefault() {
        User user = new User();
        assertThat(user.getName()).isNull();
        assertThat(user.getId() == 0);
    }
}
