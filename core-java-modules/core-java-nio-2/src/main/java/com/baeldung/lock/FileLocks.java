package com.baeldung.lock;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.types.pid_t;

public class FileLocks {

	public static interface LibC {

		public static final int O_NONBLOCK = jnr.constants.platform.OpenFlags.O_NONBLOCK.intValue();
		public static final int O_RDWR = jnr.constants.platform.OpenFlags.O_RDWR.intValue();
		public static final int O_EXLOCK = jnr.constants.platform.OpenFlags.O_EXLOCK.intValue();

		public long write(int fd, Pointer data, long len);

		@pid_t
		long getpid();

		int open(String filename, int flags);

		int close(int fd);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		
		Path path = Paths.get("/tmp/foo");
		
		// Delete the file if it exists
		Files.deleteIfExists(path);
		
		// Start with a fresh empty file
		Files.createFile(path);
		
		// Prepare some external libc calls. Will only work on systems that have libc.
		LibC libc = LibraryLoader.create(LibC.class).load("c");
		byte[] bytes = "Hello from C\n".getBytes("UTF-8");
		jnr.ffi.Runtime runtime = jnr.ffi.Runtime.getRuntime(libc);
		Pointer buffer = Memory.allocateDirect(runtime, bytes.length);
		buffer.put(0, bytes, 0, bytes.length);
		
		// Open the file through a libc call. This returns a file descriptor.
		int fd = libc.open(path.toString(), libc.O_RDWR + libc.O_EXLOCK + libc.O_NONBLOCK);
		System.out.println("Opened the file through a libc call that locks it.");
		
		// Our java method will see the lock. Itd will be well behaved and won't write to the file.
		// Note that external processes on POSIX systems would still be able to write to this file ignoring any locks.
		writeToRandomAccessFile(path, "I won't write this", 0L);
		
		// Libc opened the file, it can write to its corresponding file handle.
		libc.write(fd, buffer, bytes.length);
		
		// Now let's close the file through a libc call, to release its lock.	
		libc.close(fd);
		System.out.println("Invoked libc's close() method");
		
		// This time our method won't see the lock and will write to the file.
		writeToRandomAccessFile(path, "Hello from java", bytes.length);
		
		System.out.println("Now that all the locks are gone, here are the file contents:");
		System.out.println("------------------------------------------------------------");
		Files.lines(path).forEach(System.out::println);

	}
	
	public static RandomAccessFile writeToRandomAccessFile(Path path, String data, long position) {
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(path.toFile(), "rws");
			FileChannel channel = file.getChannel();
			// Try to acquire a lock
			try (FileLock lock = channel.tryLock()) {
				if (lock == null) {
					System.out.println("Tried to lock through the FileChannel's lock() method. This file is already locked! It's my responsibility to not write to it, even if the OS would let me!");
				} else {
					System.out.println("I don't see a lock on this file anymore. Now I can write to it.");
					int i = 0;
					channel.write(
							ByteBuffer.wrap((data).getBytes(StandardCharsets.UTF_8)), position);
				}
			} catch (Exception e) {
				System.out.println("Error while locking");
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Other Error.");
			e.printStackTrace();
		}
		return file;
	}
	
	

}
