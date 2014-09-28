package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import main.Foo;
import junit.framework.TestCase;

public class TestWriter extends TestCase {

    private String fileName1 = "test1.txt";
    private String fileName2 = "test2.txt";
    private String fileName3 = "test3.txt";
    private String fileName4 = "test4.csv";

    public TestWriter(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testWriteDouble_thenReadIt_shouldBeTheSame() {
        double value = 2.5;
        testWriteDouble(value);
        assertEquals(value, testReadDouble(), 0.0000001);
    }

    public void testWriteToPosition_editValueIfSpecificPos_shouldChange() {
        int data1 = 2014;
        int data2 = 1500;
        testWriteToPosition(data1, 4);
        assertEquals(data1, testReadFromPosition(4));
        testWriteToPosition(data2, 4);
        assertEquals(data2, testReadFromPosition(4));
    }

    public void testWriteObject_thenReadIt_instanceVariableValuesShouldBeTheSame() {
        Foo foo = new Foo(1, "John");
        testWriteObject(foo);
        Foo read = testReadObject();
        assertEquals(foo.getId(), read.getId());
        assertEquals(foo.getName(), read.getName());
    }

    public void testWriteCSVReport_thenReadAllData_shouldBeTheSame() {
        Foo[] arr = new Foo[3];
        arr[0] = new Foo(1, "John");
        arr[1] = new Foo(2, "Adam");
        arr[2] = new Foo(3, "Jane");
        testWriteCSVReport(arr);
        Foo[] read = testReadCSVReport();
        assertEquals(arr[0].getId(), read[0].getId());
        assertEquals(arr[2].getName(), read[2].getName());
        assertEquals(arr[1].getId(), read[1].getId());
    }
//================= writer methods========
 // use DataOutputStream to write primitive data types
    public void testWriteDouble(double value) {
        DataOutputStream stream;
        try {
            FileOutputStream fos = new FileOutputStream(fileName1);
            stream = new DataOutputStream(new BufferedOutputStream(fos));
            stream.writeDouble(value);
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
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

    // use ObjectOutputStream to write object
    public void testWriteObject(Foo foo) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fileName3);
            ObjectOutputStream writer = new ObjectOutputStream(fos);
            writer.writeObject(foo);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    // use CSVWriter to write CSV data
    public void testWriteCSVReport(Foo[] array) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fileName4, true), ',');
            int len = array.length;
            for (int i = 0; i < len; i++) {
                writer.writeNext(array[i].toStringArray());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    
    //=============== read methods ======
    // use DataInputStream
    public double testReadDouble() {
        DataInputStream stream;
        double result = 0;
        try {
            FileInputStream fis = new FileInputStream(fileName1);
            stream = new DataInputStream(new BufferedInputStream(fis));
            result = stream.readDouble();
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return result;
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

    // use ObjectInputStream to read object
    public Foo testReadObject() {
        FileInputStream fis;
        Foo result = null;
        try {
            fis = new FileInputStream(fileName3);
            ObjectInputStream reader = new ObjectInputStream(fis);
            result = (Foo) reader.readObject();
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return result;
    }

    // use CSVReader to read CSV data
    public Foo[] testReadCSVReport() {
        CSVReader reader;
        Foo[] result = null;
        try {
            reader = new CSVReader(new FileReader(fileName4));
            List list = reader.readAll();
            int size = list.size();
            result = new Foo[size];
            Foo temp;
            String[] currentItem;
            for (int i = 0; i < size; i++) {
                temp = new Foo();
                currentItem = (String[]) list.get(i);
                temp.setId(Long.parseLong(currentItem[0]));
                temp.setName(currentItem[1]);
                result[i] = temp;
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return result;
    }


    
}
