package com.baeldung.lock;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;

import org.junit.jupiter.api.Test;

class FileLocksTest {

	// Exclusive locks
	/**
	 * Getting an exclusive (write) lock on the entire file.
	 * Fails when getting the lock from a FileChannel obtained through a FileInputStream.
	 */
	@Test
	void givenAnInputStream_whenGetWriteLock_throwNonWritableChannelException() {
		assertThrows(NonWritableChannelException.class, 
				() -> FileLocks.getExclusiveLockFromInputStream());
	}
	
	/**
	 * Getting and exclusive lock from a RandomAccessFile
	 * @throws IOException
	 */
	@Test
	void givenARandomAccessFileWithReadWriteAccess_whenGetWriteLock_lock() throws IOException {
		FileLock lock = FileLocks.getExclusiveLockFromRandomAccessFile(0, 10);
		assertNotNull(lock);
		assertFalse(lock.isShared());
	}
	
	/**
	 * Getting an exclusive lock from FileChannel::open
	 * @throws IOException
	 */
	@Test
	void givenAPath_whenGetExclusiveLock_lock() throws IOException {
		FileLock lock = FileLocks.getExclusiveLockFromFileChannelOpen(0, 10);
		assertNotNull(lock);
		assertFalse(lock.isShared());
	}
	
	
	/**
	 * Getting a shared (read) lock works fine when getting the lock from a FileChannel obtained through a FileInputStream.
	 * @throws IOException
	 */
	@Test
	void givenAnInputStream_whenGetSharedLock_lock() throws IOException {
		FileLock lock = FileLocks.getReadLockFromInputStream(0, 10);
		assertNotNull(lock);
		assertTrue(lock.isShared());
		
	}
	
	
	@Test
	void givenAFileOutputStream_whenGetSharedLock_throwNonReadableChannelException() {
		assertThrows(NonReadableChannelException.class, 
				() -> FileLocks.getReadLockFromOutputStream(0, 10));
	}
	
	@Test
	void givenARandomAccessFile_whenGetSharedLock_lock() throws IOException {
		FileLock lock = FileLocks.getReadLockFromRandomAccessFile(0,  10);
		assertNotNull(lock);
		assertTrue(lock.isShared());
		
	}

}
