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

public class ProducerAppWithSpinLock {

    public static void main(String[] args) {
        try {
            run(args);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.console()
              .printf("Press <enter> to continue");
            System.console()
              .readLine();
        }
    }


    public static void run(String[] args) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA1");
        digest.digest(new byte[256]);
        byte[] dummy = digest.digest();
        int hashLen = dummy.length;


        long size = Long.parseLong(args[1]);
        MappedByteBuffer shm = createSharedMemory(args[0],  size + hashLen);

        // Cleanup lock area
        shm.putInt(0,0);

        long addr = getBufferAddress(shm);
        System.out.printf("Buffer address: 0x%08x\n",addr);
        Random rnd = new Random();

        long start = System.currentTimeMillis();
        long iterations = 0;
        int capacity = shm.capacity();
        System.out.println("Starting producer iterations...");
        SpinLock lock = new SpinLock(addr);
        while(System.currentTimeMillis() - start < 30000) {

            if(!lock.tryLock(5000)) {
                throw new RuntimeException("Unable to acquire lock");
            }

            try {
                // Skip the first 4 bytes, as they're used by the lock
                for (int i = 4; i < capacity - hashLen; i++) {
                    byte value = (byte) (rnd.nextInt(256) & 0x00ff);
                    digest.update(value);
                    shm.put(i, value);
                }

                // Write hash at the end
                byte[] hash = digest.digest();
                shm.position(capacity-hashLen);
                shm.put(hash);
                iterations++;
            }
            finally {
                lock.unlock();
            }
        }

        System.out.printf("%d iterations run\n", iterations);

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

            return  fc.map(FileChannel.MapMode.READ_WRITE, 0, size);

        }
        catch( IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }

}
