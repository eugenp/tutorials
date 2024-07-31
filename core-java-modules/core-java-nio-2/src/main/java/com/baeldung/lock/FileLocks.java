package com.baeldung.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileLocks {

    private static final Logger LOG = LoggerFactory.getLogger(FileLocks.class);

    // Write locks

    /**
     * Trying to get an exclusive lock on a read-only FileChannel won't work.
     */
    static void getExclusiveLockFromInputStream() throws IOException {
        Path path = Files.createTempFile("foo", "txt");
        try (FileInputStream fis = new FileInputStream(path.toFile());
            FileLock lock = fis.getChannel()
                .lock()) {
            LOG.debug("This won't happen");
        } catch (NonWritableChannelException e) {
            LOG.error("The channel obtained through a FileInputStream isn't writable. You can't obtain an exclusive lock on it!");
            throw e;
        }
    }

    /**
     * Gets an exclusive lock from a RandomAccessFile. Works because the file is writable.
     * @param from beginning of the locked region
     * @param size how many bytes to lock
     * @return A lock object representing the newly-acquired lock
     * @throws IOException if there is a problem creating the temporary file
     */
    static FileLock getExclusiveLockFromRandomAccessFile(long from, long size) throws IOException {
        Path path = Files.createTempFile("foo", "txt");
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "rw");
            FileLock lock = file.getChannel()
                .lock(from, size, false)) {
            if (lock.isValid()) {
                LOG.debug("This is a valid exclusive lock");
                return lock;
            }
            return null;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    /**
     * Acquires an exclusive lock on a file region.
     * @param from beginning of the locked region
     * @param size how many bytes to lock
     * @return A lock object representing the newly-acquired lock
     * @throws IOException if there is a problem creating the temporary file
     */
    static FileLock getExclusiveLockFromFileChannelOpen(long from, long size) throws IOException {
        Path path = Files.createTempFile("foo", "txt");
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.APPEND); FileLock lock = channel.lock(from, size, false)) {
            String text = "Hello, world.";
            ByteBuffer buffer = ByteBuffer.allocate(text.length() + System.lineSeparator()
                .length());
            buffer.put((text + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            while (buffer.hasRemaining()) {
                channel.write(buffer, channel.size());
            }
            
            return lock;
        }
    }

    // Read locks

    /**
     * Trying to get a shared lock on a write-only FileChannel won't work.
     */
    static void getReadLockFromOutputStream() throws IOException {
        Path path = Files.createTempFile("foo", "txt");
        try (FileOutputStream fis = new FileOutputStream(path.toFile());
            FileLock lock = fis.getChannel()
                .lock(0, Long.MAX_VALUE, true)) {
            LOG.debug("This won't happen");
        } catch (NonReadableChannelException e) {
            LOG.error("The channel obtained through a FileOutputStream isn't readable. " + "You can't obtain an shared lock on it!");
            throw e;
        }
    }

    /**
     * Gets a lock from an <tt>InputStream</tt>.
     * @param from beginning of the locked region
     * @param size how many bytes to lock
     * @return A lock object representing the newly-acquired lock
     * @throws IOException if there is a problem creating the temporary file
     */
    static FileLock getReadLockFromInputStream(long from, long size) throws IOException {
        Path path = Files.createTempFile("foo", "txt");
        try (FileInputStream fis = new FileInputStream(path.toFile());
            FileLock lock = fis.getChannel()
                .lock(from, size, true)) {
            if (lock.isValid()) {
                LOG.debug("This is a valid shared lock");
                return lock;
            }
            return null;
        }
    }

    /**
     * Gets an exclusive lock from a RandomAccessFile. Works because the file is readable.
     * @param from beginning of the locked region
     * @param size how many bytes to lock
     * @return A lock object representing the newly-acquired lock
     * @throws IOException if there is a problem creating the temporary file
     */
    static FileLock getReadLockFromRandomAccessFile(long from, long size) throws IOException {
        Path path = Files.createTempFile("foo", "txt");
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r"); // could also be "rw", but "r" is sufficient for reading
            FileLock lock = file.getChannel()
                .lock(from, size, true)) {
            if (lock.isValid()) {
                LOG.debug("This is a valid shared lock");
                return lock;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

}
