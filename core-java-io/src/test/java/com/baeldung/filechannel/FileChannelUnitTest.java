package com.baeldung.filechannel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class FileChannelUnitTest {

	@Test
	public void givenFile_whenReadWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {
		String expected_value = "Hello world";
		String file = "src/test/resources/test_read.in";
		
		try (RandomAccessFile reader = new RandomAccessFile(file, "r");
				FileChannel channel = reader.getChannel();
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			int bufferSize = 1024;
			if (bufferSize > channel.size()) {
				bufferSize = (int) channel.size();
			}
			ByteBuffer buff = ByteBuffer.allocate(bufferSize);

			while (channel.read(buff) > 0) {
				if (buff.hasArray()) {
					out.write(buff.array(), 0, buff.position());
					buff.clear();
				}
			}

			assertEquals(expected_value, new String(out.toByteArray(), StandardCharsets.UTF_8));
		}
	}

	
	@Test
	public void givenFile_whenReadWithFileChannelUsingFileInputStream_thenCorrect() throws IOException {
		String expected_value = "Hello world";
		String file = "src/test/resources/test_read.in";

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
			FileInputStream fin = new FileInputStream(file);
			FileChannel channel = fin.getChannel();) {

			int bufferSize = 1024;
			if (bufferSize > channel.size()) {
				bufferSize = (int) channel.size();
			}
			ByteBuffer buff = ByteBuffer.allocate(bufferSize);

			while (channel.read(buff) > 0) {
				if (buff.hasArray()) {
					out.write(buff.array(), 0, buff.position());
					buff.clear();
				}
			}

			assertEquals(expected_value, new String(out.toByteArray(), StandardCharsets.UTF_8));

		}
	}
	
	
	@Test
	public void givenFile_whenReadAFileSectionIntoMemoryWithFileChannel_thenCorrect() throws IOException {
		String expected_value = "world";
		String file = "src/test/resources/test_read.in";

		try (RandomAccessFile reader = new RandomAccessFile(file, "r");
				FileChannel channel = reader.getChannel();
				ByteArrayOutputStream out = new ByteArrayOutputStream();) {

			MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 6, 5);

			if(buff.hasRemaining()) {
				byte[] data = new byte[buff.remaining()];
				buff.get(data);
				assertEquals(expected_value, new String(data, StandardCharsets.UTF_8));
			
			}
			
		}
	}
	
	
	@Test
	public void whenWriteWithFileChannelUsingRandomAccessFile_thenCorrect()   
	  throws IOException {    
	    String expected = "Hello world";    
	    String file = "src/test/resources/test_write_using_filechannel.txt";   
	    
	    try (RandomAccessFile writer = new RandomAccessFile(file, "rw");    
	        FileChannel channel = writer.getChannel();){    
	        ByteBuffer buff = ByteBuffer.wrap("Hello world".getBytes(StandardCharsets.UTF_8));    
	        
	        if (buff.hasRemaining()) {         
	           channel.write(buff);    
	        }    
	             
	        // verify    
	        RandomAccessFile reader = new RandomAccessFile(file, "r");    
	        assertEquals(expected, reader.readLine());    
	        reader.close();
	    }
	}
	
	
	@Test
	public void givenFile_whenWriteAFileUsingLockAFileSectionWithFileChannel_thenCorrect() throws IOException {
		String file = "src/test/resources/test_read.in";

		try (RandomAccessFile reader = new RandomAccessFile(file, "rw");
		    FileChannel channel = reader.getChannel();
			FileLock fileLock = channel.tryLock(6, 5, Boolean.FALSE );){
		
				 
		    assertNotNull(fileLock);
			
		}catch(OverlappingFileLockException | IOException ex) {
			
			
		}
	
	}
	

	@Test
	public void givenFile_whenReadWithFileChannelGetPosition_thenCorrect() throws IOException {
		long expected_value = 11;
		String file = "src/test/resources/test_read.in";

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
			RandomAccessFile reader = new RandomAccessFile(file, "r");
			FileChannel channel = reader.getChannel();) {

			int bufferSize = 1024;
			if (bufferSize > channel.size()) {
				bufferSize = (int) channel.size();
			}
			ByteBuffer buff = ByteBuffer.allocate(bufferSize);

			while (channel.read(buff) > 0) {
				out.write(buff.array(), 0, buff.position());
				buff.clear();
			}

			assertEquals(expected_value, channel.position());

			channel.position(4);
			assertEquals(4, channel.position());

		}
	}

	
	@Test
	public void whenGetFileSize_thenCorrect() throws IOException {
		long expectedSize = 11;
		String file = "src/test/resources/test_read.in";
		
		try (RandomAccessFile reader = new RandomAccessFile(file, "r"); 
			FileChannel channel = reader.getChannel();) {

			long imageFileSize = channel.size();
			assertEquals(expectedSize, imageFileSize);
		}

	}

	@Test
	public void whenTruncateFile_thenCorrect() throws IOException {
		long expectedSize = 5;
		String input = "this is a test input";
		String file = "src/test/resources/test_truncate.txt";

		FileOutputStream fout = new FileOutputStream(file);
		FileChannel channel = fout.getChannel();

		ByteBuffer buff = ByteBuffer.wrap(input.getBytes());
		channel.write(buff);
		buff.flip();

		channel = channel.truncate(5);
		assertEquals(expectedSize, channel.size());

		fout.close();
		channel.close();
	}
}
