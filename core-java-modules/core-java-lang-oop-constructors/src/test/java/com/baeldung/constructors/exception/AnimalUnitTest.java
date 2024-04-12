package com.baeldung.constructors.exception;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AnimalUnitTest {

    @Test
    public void givenNoArgument_thenFails() {
        Assertions.assertThatThrownBy(() -> {
            new Animal();
        })
            .isInstanceOf(Exception.class);
    }

    @Test
    public void givenInvalidArg_thenFails() {
        Assertions.assertThatThrownBy(() -> {
            new Animal(null, 30);
        })
            .isInstanceOf(NullPointerException.class);
    }

    @Test(expected = Test.None.class)
    public void givenValidArg_thenSuccess() {
        new Animal("1234", 30);
    }

    @Test
    public void givenAbsolutePath_thenFails() {
        Assertions.assertThatThrownBy(() -> {
            new Animal(new File("temp.txt").getAbsoluteFile());
        })
            .isInstanceOf(SecurityException.class);
    }

    @Test
    public void givenDirectoryTraversalPath_thenFails() {
        Assertions.assertThatThrownBy(() -> {
            new Animal(new File(File.separator + ".." + File.separator + "temp.txt"));
        })
            .isInstanceOf(SecurityException.class);
    }

}
