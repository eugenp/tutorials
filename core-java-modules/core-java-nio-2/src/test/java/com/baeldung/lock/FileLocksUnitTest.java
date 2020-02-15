package com.baeldung.lock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;

import org.junit.jupiter.api.Test;

class FileLocksUnitTest {

    @Test
    void givenAnInputStream_whenGetWriteLock_thenThrowNonWritableChannelException() {
        assertThrows(NonWritableChannelException.class, () -> FileLocks.getExclusiveLockFromInputStream());
    }

    @Test
    void givenARandomAccessFileWithReadWriteAccess_whenGetWriteLock_lock() throws IOException {
        FileLock lock = FileLocks.getExclusiveLockFromRandomAccessFile(0, 10);
        assertNotNull(lock);
        assertFalse(lock.isShared());
    }

    @Test
    void givenAPath_whenGetExclusiveLock_lock() throws IOException {
        FileLock lock = FileLocks.getExclusiveLockFromFileChannelOpen(0, 10);
        assertNotNull(lock);
        assertFalse(lock.isShared());
    }

    @Test
    void givenAFileOutputStream_whenGetSharedLock_throwNonReadableChannelException() {
        assertThrows(NonReadableChannelException.class, FileLocks::getReadLockFromOutputStream);
    }

    @Test
    void givenAnInputStream_whenGetSharedLock_lock() throws IOException {
        FileLock lock = FileLocks.getReadLockFromInputStream(0, 10);
        assertNotNull(lock);
        assertTrue(lock.isShared());
    }

    @Test
    void givenARandomAccessFile_whenGetSharedLock_lock() throws IOException {
        FileLock lock = FileLocks.getReadLockFromRandomAccessFile(0, 10);
        assertNotNull(lock);
        assertTrue(lock.isShared());
    }
}
