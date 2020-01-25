package com.baeldung.lock;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;


public class FileLocks {
	
	private static Logger log = LoggerFactory.getLogger(FileLocks.class);

	// Write locks
	/**
	 * Trying to get an exclusive lock on a read-only FileChannel won't work.
	 */
	static void getExclusiveLockFromInputStream() throws IOException, NonWritableChannelException {
		Path path = Files.createTempFile("foo", "txt");
		try (FileInputStream fis = new FileInputStream(path.toFile()); FileLock lock = fis.getChannel().lock()) {
			log.debug("This won't happen");
		} catch (NonWritableChannelException e) {
			log.error(
					"The channel obtained through a FileInputStream isn't writable. "
					+ "You can't obtain an exclusive lock on it!");
			throw e;
		}
	}

	
	/**
	 * Getting an exclusive lock from a RandomAccessFile works if the file is in write mode.
	 * @param from beginning of the locked region
	 * @param size how many bytes to lock
	 * @return
	 * @throws IOException
	 */
	static FileLock getExclusiveLockFromRandomAccessFile(long from, long size) throws IOException {
		Path path = Files.createTempFile("foo", "txt");
		try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "rw");
				FileLock lock = file.getChannel().lock(from, size, false)) {
			if (lock.isValid()) {
				log.debug("This is a valid exclusive lock");
				return lock;
			}
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Getting a write lock on a file region
	 */
	static FileLock getExclusiveLockFromFileChannelOpen(long from, long size) throws IOException {
		Path path = Files.createTempFile("foo", "txt");
		try (FileChannel channel = FileChannel.open(path, StandardOpenOption.APPEND);
				FileLock lock = channel.lock(from, size, false)) {
			String text = "Hello, world.";
			ByteBuffer buffer = ByteBuffer.allocate(text.length() + System.lineSeparator().length());
			buffer.put((text + System.lineSeparator()).getBytes(Charsets.UTF_8));
			buffer.flip();
			while (buffer.hasRemaining()) {
				channel.write(buffer, channel.size());
			}
			log.debug("This was written to the file");
			Files.lines(path).forEach(System.out::println);
			return lock;
		}
	}

	// Read locks
	/**
	 * Trying to get a shared lock on a write-only FileChannel won't work.
	 */
	static void getReadLockFromOutputStream(long from, long size) throws IOException {
		Path path = Files.createTempFile("foo", "txt");
		try (FileOutputStream fis = new FileOutputStream(path.toFile());
				FileLock lock = fis.getChannel().lock(0, Long.MAX_VALUE, true)) {
			log.debug("This won't happen");
		} catch (NonReadableChannelException e) {
			log.error(
					"The channel obtained through a FileOutputStream isn't readable. "
					+ "You can't obtain an shared lock on it!");
			throw e;
		}
	}
	
	/**
	 * Locking a file for reading doesn't require a writable FileChannel.
	 * 
	 * @param from beginning of the locked region
	 * @param size how many bytes to lock
	 * @return
	 * @throws IOException
	 */
	static FileLock getReadLockFromInputStream(long from, long size) throws IOException {
		Path path = Files.createTempFile("foo", "txt");
		try (FileInputStream fis = new FileInputStream(path.toFile());
				FileLock lock = fis.getChannel().lock(from, size, true)) {
			if (lock.isValid()) {
				log.debug("This is a valid shared lock");
				return lock;
			}
			return null;
		}
	}
	
	
	/**
	 * Getting an exclusive lock from a RandomAccessFile works if the file is in read mode.
	 * @param from beginning of the locked region
	 * @param size how many bytes to lock
	 * @return
	 * @throws IOException
	 */
	static FileLock getReadLockFromRandomAccessFile(long from, long size) throws IOException {
		Path path = Files.createTempFile("foo", "txt");
		try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r"); // could also be "rw", but "r" is sufficient for reading
				FileLock lock = file.getChannel().lock(from, size, true)) {
			if (lock.isValid()) {
				log.debug("This is a valid shared lock");
				return lock;
			}
			return null;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	

	static class Writer implements Runnable {

		private Path path;

		private volatile RandomAccessFile file;

		private String text;

		private volatile CountDownLatch countDownLatch;

		/**
		 * 
		 * @param path           The path to the file we will write into
		 * @param text           The text to write
		 * @param countDownLatch A counter for thread synchronization
		 * 
		 */
		public Writer(Path path, String text, CountDownLatch countDownLatch) {
			this.path = path;
			this.text = text;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				lockAndWrite();
			} catch (InterruptedException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			countDownLatch.countDown();
		}

		private void lockAndWrite() throws InterruptedException, FileNotFoundException {
			ByteBuffer buffer = null;
			if (file == null) {
				file = new RandomAccessFile(path.toFile(), "rw");
			}
			try (FileChannel channel = file.getChannel()) {

				try (FileLock lock = channel.tryLock(channel.size(),
						channel.size() + text.length() + System.lineSeparator().length(), true)) {
					if (lock != null) {
						String text = "Hello, world.";
						buffer = ByteBuffer.allocate(text.length() + System.lineSeparator().length());
						buffer.put((text + System.lineSeparator()).getBytes(Charsets.UTF_8));
						buffer.flip();
						while (buffer.hasRemaining()) {
							channel.write(buffer, channel.size());
						}
					}
				} catch (OverlappingFileLockException e) {
					// Failed to lock. Try again later.
					Thread.sleep(50);
					lockAndWrite();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) throws InterruptedException, IOException {
		Path path = Paths.get("/tmp/foo");
		Files.deleteIfExists(path);
		Files.createFile(path);
		int concurrentWriters = 5;
		CountDownLatch countDownLatch = new CountDownLatch(concurrentWriters);
		// Launch 10 writers in parallel
		final AtomicInteger count = new AtomicInteger(0);
		Stream.generate(() -> new Thread(new Writer(path, "foo " + count.incrementAndGet(), countDownLatch)))
				.limit(concurrentWriters).forEach(Thread::start);

		countDownLatch.await();
		AtomicInteger lineCount = new AtomicInteger(0);
		Files.lines(path).forEach((line) -> {
			lineCount.incrementAndGet();
			System.out.println(line);
		});
		log.info("Total lines written = " + lineCount.get());

	}
}
