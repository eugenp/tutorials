package test;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import main.Foo;
import junit.framework.TestCase;

public class TestWriter extends TestCase {

    private String fileName = "test.txt";
    private String fileName1 = "test1.txt";
    private String fileName2 = "test2.txt";
    private String fileName3 = "test3.txt";
    private String fileName4 = "test4.txt";
    private String fileName5 = "test5.txt";

    public TestWriter(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testWriteFormattedStringUsingPrintWriter() throws IOException{
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("Product name is %s and its price is %d $","iPhone",1000);
        printWriter.close();
    }

    public void testWriteStringWithDataOutputStream_thenReadIt_shouldBeTheSame() throws IOException {
        String value = "Hello";
        FileOutputStream fos = new FileOutputStream(fileName1);
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        outStream.writeUTF(value);
        outStream.close();
        
        String result;
        FileInputStream fis = new FileInputStream(fileName1);
        DataInputStream reader = new DataInputStream(fis);
        result = reader.readUTF();
        reader.close();
        
        assertEquals(value, result);
    }

    public void testWriteToPosition_editValueIfSpecificPos_shouldChange() {
        int data1 = 2014;
        int data2 = 1500;
        testWriteToPosition(data1, 4);
        assertEquals(data1, testReadFromPosition(4));
        testWriteToPosition(data2, 4);
        assertEquals(data2, testReadFromPosition(4));
    }

    public void testWriteObject_thenReadIt_instanceVariableValuesShouldBeTheSame() throws Exception {
        Foo foo = new Foo(1, "John");
        FileOutputStream fos = new FileOutputStream(fileName3);
        ObjectOutputStream writer = new ObjectOutputStream(fos);
        writer.writeObject(foo);
        writer.close();
        Foo result = null;

        FileInputStream fis = new FileInputStream(fileName3);
        ObjectInputStream reader = new ObjectInputStream(fis);
        result = (Foo) reader.readObject();
        reader.close();

        assertEquals(foo.getId(), result.getId());
        assertEquals(foo.getName(), result.getName());
    }

    public void testFileLock() throws IOException {
        RandomAccessFile stream = new RandomAccessFile(fileName4, "rw");
        FileChannel channel = stream.getChannel();

        FileLock lock = channel.tryLock();
        stream.writeChars("test lock");
        lock.release();
        
        stream.close();
        channel.close();
    }

    
    public void testCreateFile_thenCheckIfItExists_thenDeleteAndCheckIfExist() throws IOException{
        File file = new File("test_create.txt");
        file.createNewFile();
        assertTrue(file.exists());
        file.delete();
        assertFalse(file.exists());
    }
    
    public void testWriteStringUsingFileChannel() throws IOException{
        RandomAccessFile stream = new RandomAccessFile(fileName5, "rw");
        FileChannel channel = stream.getChannel();
        String value = "Hello";
        byte[] strBytes = value.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
        buffer.put(strBytes);
        buffer.flip();
        channel.write(buffer);        
        stream.close();
        channel.close();
        
        RandomAccessFile reader = new RandomAccessFile(fileName5, "r");
        assertEquals(value , reader.readLine());
        reader.close();
        
    }
    
    // use RandomAccessFile to write data at specific position in the file
    public void testWriteToPosition(int data, long position) {
        try {
            RandomAccessFile writer = new RandomAccessFile(fileName2, "rw");
            writer.seek(position);
            writer.writeInt(data);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }



    // use RandomAccessFile to read data from specific position in the file
    public int testReadFromPosition(long position) {
        int result = 0;
        try {
            RandomAccessFile reader = new RandomAccessFile(fileName2, "r");
            reader.seek(position);
            result = reader.readInt();
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return result;
    }

   
   

    
}
