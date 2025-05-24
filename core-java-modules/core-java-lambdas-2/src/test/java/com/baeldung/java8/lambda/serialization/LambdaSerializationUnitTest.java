package com.baeldung.java8.lambda.serialization;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;

public class LambdaSerializationUnitTest {
    @Test(expected = NotSerializableException.class)
    public void givenRunnable_whenNoCapturing_thenSerializationFailed() throws IOException, ClassNotFoundException {
        Object obj = NotSerializableLambdaExpression.getLambdaExpressionObject();
        writeAndReadObject(obj, Runnable.class);
    }

    @Test
    public void givenIntersectionType_whenNoCapturing_thenSerializationSuccess() throws IOException, ClassNotFoundException {
        Object obj = SerializableLambdaExpression.getLambdaExpressionObject();
        writeAndReadObject(obj, Runnable.class);
    }

    @Test
    public void givenSerializableRunnable_whenNoCapturing_thenSerializationSuccess() throws IOException, ClassNotFoundException {
        SerializableRunnable obj = () -> System.out.println("please serialize this message");
        writeAndReadObject(obj, SerializableRunnable.class);
    }

    @Test
    public void givenSerializableFunction_whenNoCapturing_thenSerializationSuccess() throws IOException, ClassNotFoundException {
        SerializableFunction<String, String> obj = message -> String.format("Hello %s", message);
        writeAndReadObject(obj, SerializableFunction.class);
    }

    @Test
    public void givenSerializableConsumer_whenNoCapturing_thenSerializationSuccess() throws IOException, ClassNotFoundException {
        SerializableConsumer<String> obj = message -> System.out.println(message);
        writeAndReadObject(obj, SerializableConsumer.class);
    }

    @Test(expected = NotSerializableException.class)
    public void givenSerializableConsumer_whenCapturingNotSerializable_thenSerializationFailed() throws IOException, ClassNotFoundException {
        SerializableConsumer<String> obj = System.out::println;
        writeAndReadObject(obj, SerializableConsumer.class);
    }

    private <T> void writeAndReadObject(Object obj, Class<T> clazz) throws IOException, ClassNotFoundException {
        File file = Files.createTempFile("lambda", "ser").toFile();
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(obj);
        }

        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            Object newObj = ois.readObject();
            boolean isInstance = clazz.isInstance(newObj);

            assertTrue(isInstance);
        }
    }

    interface SerializableRunnable extends Runnable, Serializable {
    }

    interface SerializableFunction<T, R> extends Function<T, R>, Serializable {
    }

    interface SerializableConsumer<T> extends Consumer<T>, Serializable {
    }
}
