package com.baeldung.sharedmem;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.EnumSet;
import java.util.Random;

public class ProducerApp {


    public static void main(String[] args) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA1");
        digest.digest(new byte[256]);
        byte[] dummy = digest.digest();
        int hashLen = dummy.length;


        long size = Long.parseLong(args[1]);
        MappedByteBuffer shm = createSharedMemory(args[0],  size + hashLen);

        System.out.println("Starting producer iterations...");

        long start = System.currentTimeMillis();
        long iterations = 0;
        int capacity = shm.capacity();
        Random rnd = new Random();

        while(System.currentTimeMillis() - start < 30000) {

            for (int i = 0; i < capacity - hashLen; i++) {
                byte value = (byte) (rnd.nextInt(256) & 0x00ff);
                digest.update(value);
                shm.put(i, value);
            }

            // Write hash at the end
            byte[] hash = digest.digest();
            shm.position(capacity - hashLen);
            shm.put(hash);
            iterations++;
        }

        System.out.printf("%d iterations run\n", iterations);
        System.out.println("Press <enter> to exit");
        System.console().readLine();

    }

    private static long getBufferAddress(MappedByteBuffer shm) {
        try {
            Class<?> cls = shm.getClass();
            Method maddr = cls.getMethod("address");
            maddr.setAccessible(true);
            Long addr = (Long) maddr.invoke(shm);
            if ( addr == null ) {
                throw new RuntimeException("Unable to retrieve buffer's address");
            }
            return addr;
        }
        catch( NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static MappedByteBuffer createSharedMemory(String path, long size) {

        try (FileChannel fc = (FileChannel)Files.newByteChannel(new File(path).toPath(),
          EnumSet.of(
            StandardOpenOption.CREATE,
            StandardOpenOption.SPARSE,
            StandardOpenOption.WRITE,
            StandardOpenOption.READ))) {

            return fc.map(FileChannel.MapMode.READ_WRITE, 0, size);
        }
        catch( IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
