package com.baeldung.lambdaserialization;

import org.slf4j.*;

import java.io.*;
import java.nio.file.Files;

public class SerializeLambda {
    private static final Logger logger = LoggerFactory.getLogger(SerializeLambda.class);
    
    public static void main(String[] a) throws IOException {
        File file = Files.createTempFile("lambda", "ser").toFile();
        
        serialize(file);
        deserialize(file);
        
        serializeAnonymous(file);
        deserialize(file);
        
        serializeByLambda(file);
        deserialize(file);
        
        serializeByLambdaMethodRef(file);
        deserialize(file);
    }
    
    private static String print() {
        String message = "serialized using lambda method reference";
        logger.info(message);
        return message;
    }
    
    private static void serialize(File file) throws IOException {
        
        try (ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(file))) {
            Runnable r = new MyTask();
            oo.writeObject(r);
        }
        
    }
    
    private static void serializeAnonymous(File file) throws IOException {
        
        try (ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(file))) {
            SerializableRunnable r = new SerializableRunnable() {
                @Override
                public void run() {
                    logger.info("Serialized using Anonymous classes");
                }
            };
            oo.writeObject(r);
        }
        
    }
    
    protected static void serializeByLambda(File file) throws IOException {
        try (ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(file))) {
            Runnable r = (Runnable & Serializable) () -> logger.info("Serialized using lambda");
            oo.writeObject(r);
        }
        
    }
    
    private static void serializeByLambdaMethodRef(File file) throws IOException {
        try (ObjectOutput oo = new ObjectOutputStream(new FileOutputStream(file))) {
            Runnable r = (Runnable & Serializable) SerializeLambda::print;
            oo.writeObject(r);
        }
    }
    
    private static void deserialize(File file) throws IOException {
        try (ObjectInput oi = new ObjectInputStream(new FileInputStream(file))) {
            Runnable r = (Runnable) oi.readObject();
            r.run();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}