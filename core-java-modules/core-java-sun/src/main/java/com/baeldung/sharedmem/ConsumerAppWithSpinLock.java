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
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;

public class ConsumerAppWithSpinLock {

    public static void main(String[] args) {
        try {
            // Small wait to ensure the Producer gets the first round. Otherwise the hash will be invalid
            Thread.sleep(1000);
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

    private static void run(String args[]) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA1");
        digest.digest(new byte[256]);
        byte[] dummy = digest.digest();
        int hashLen = dummy.length;

        long size = Long.parseLong(args[1]);
        MappedByteBuffer shm = createSharedMemory(args[0], size + hashLen);
        long addr = getBufferAddress(shm);

        System.out.printf("Buffer address: 0x%08x\n", addr);

        Random rnd = new Random();

        long start = System.currentTimeMillis();
        long iterations = 0;
        int capacity = shm.capacity();
        System.out.println("Starting consumer iterations...");

        long matchCount = 0;
        long mismatchCount = 0;
        byte[] expectedHash = new byte[hashLen];
        SpinLock lock = new SpinLock(addr);

        while (System.currentTimeMillis() - start < 30_000) {

            if (!lock.tryLock(5_000)) {
                throw new RuntimeException("Unable to acquire lock");
            }

            try {
                for (int i = 4; i < capacity - hashLen; i++) {
                    byte value = shm.get(i);
                    digest.update(value);
                }

                byte[] hash = digest.digest();
                shm.position(capacity-hashLen);
                shm.get(expectedHash);

                if (Arrays.equals(hash, expectedHash)) {
                    matchCount++;
                } else {
                    mismatchCount++;
                }

                iterations++;
            } finally {
                lock.unlock();
            }
        }

        System.out.printf("%d iteractions run. matches=%d, mismatches=%d\n", iterations, matchCount, mismatchCount);
    }

    private static MappedByteBuffer createSharedMemory(String path, long size) {

        try (FileChannel fc = (FileChannel) Files.newByteChannel(
          new File(path).toPath(),
          EnumSet.of(
            StandardOpenOption.CREATE,
            StandardOpenOption.SPARSE,
            StandardOpenOption.WRITE,
            StandardOpenOption.READ))) {
            return fc.map(FileChannel.MapMode.READ_WRITE, 0, size);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static long getBufferAddress(MappedByteBuffer shm) {
        try {
            Class<?> cls = shm.getClass();
            Method maddr = cls.getMethod("address");
            maddr.setAccessible(true);
            Long addr = (Long) maddr.invoke(shm);
            if (addr == null) {
                throw new RuntimeException("Unable to retrieve buffer's address");
            }
            return addr;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

}