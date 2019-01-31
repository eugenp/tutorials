package com.baeldung.arraycopy;

import com.baeldung.arraycopy.model.Address;
import com.baeldung.arraycopy.model.Employee;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertTrue;

/**
 * 数组拷贝测试
 */
public class ArrayCopyUtilUnitTest {
    private static Employee[] employees;
    private static final int MAX = 2;

    @BeforeClass
    public static void setup(){
      createEmployeesArray();
    }

    private static void createEmployeesArray() {
        employees = new Employee[MAX];
          Employee employee;
          for(int i = 0; i < MAX; i++) {
              employee = new Employee();
              employee.setName("Emp"+i);
              employee.setId(i);
              employees[i] = employee;
          }
    }

    /**
     * @see java.lang.System#arraycopy(Object, int, Object, int, int)
     */
    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaSystemsArrayCopy_thenSuccessful(){
        int[] array = {23, 43, 55};
        int[] copiedArray = new int[3];
        
        System.arraycopy(array, 0, copiedArray, 0, 3);
        
        Assert.assertArrayEquals(copiedArray, array);
    }

    /**
     * @see java.lang.System#arraycopy(Object, int, Object, int, int)
     */
    @Test
    public void givenArrayOfPrimitiveType_whenCopiedSubSequenceViaSystemsArrayCopy_thenSuccessful(){
        int[] array = {23, 43, 55, 12, 65, 88, 92};
        int[] copiedArray = new int[3];
        
        System.arraycopy(array, 2, copiedArray, 0, 3);
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        
        assertTrue(3 == copiedArray.length);
        assertTrue(copiedArray[0] == array[2]);
        assertTrue(copiedArray[1] == array[3]);
        assertTrue(copiedArray[2] == array[4]);
    }

    /**
     * @see java.util.Arrays#copyOfRange(int[] original, int from, int to)
     * 注意区间范围：[from , to)
     */
    @Test
    public void givenArrayOfPrimitiveType_whenCopiedSubSequenceViaArraysCopyOfRange_thenSuccessful(){
        int[] array = {23, 43, 55, 12, 65, 88, 92};
        
        int[] copiedArray = Arrays.copyOfRange(array, 1, 4);

        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        System.out.println(new int[]{43, 55, 12} == copiedArray);
        System.out.println(new int[]{43, 55, 12}.equals(copiedArray));
        Assert.assertArrayEquals(new int[]{43, 55, 12} , copiedArray);

        assertTrue(3 == copiedArray.length);
        assertTrue(copiedArray[0] == array[1]);
        assertTrue(copiedArray[1] == array[2]);
        assertTrue(copiedArray[2] == array[3]);
    }

    /**
     * @see java.util.Arrays#copyOf(int[] original, int newLength)
     * （1）数组深拷贝、浅拷贝{https://qindongliang.iteye.com/blog/2410770}
     */
    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaArraysCopyOf_thenValueChangeIsSuccessful(){
        int[] array = {23, 43, 55, 12};
        int newLength = array.length;
        int[] copiedArray = Arrays.copyOf(array, newLength);
        Assert.assertArrayEquals(copiedArray, array);

        //改变array和copiedArray之前的值：
        System.out.println("array:{}" + Arrays.toString(array));
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        System.out.println();

        //改变array中的值
        array[0] = 9;
        System.out.println("array:{}" + Arrays.toString(array));
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        Assert.assertTrue(copiedArray[0] != array[0]);
        System.out.println();

        //改变copiedArray中的值
        copiedArray[1] = 12;
        System.out.println("array:{}" + Arrays.toString(array));
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        Assert.assertTrue(copiedArray[1] != array[1]);
    }


    /**
     * 注意：（1）对于引用类型Employee是浅拷贝.
     * 注意：（2）对于（String,boolean,char,byte,short,float,double.long）的数组是深拷贝，注意String类型是因为其值不可变所以才可以使用。
     */
    @Test
    public void givenArrayOfNonPrimitiveType_whenCopiedViaArraysCopyOf_thenDoShallowCopy(){
        Employee[] copiedArray = Arrays.copyOf(employees, employees.length);
        System.out.println("employees:{}" + Arrays.toString(employees));
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        Assert.assertArrayEquals(copiedArray, employees);

        //修改employees里面的值
        employees[0].setName(employees[0].getName()+"_Changed");

        System.out.println("employees:{}" + Arrays.toString(employees));
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        //change in employees' element caused change in the copied array
        Assert.assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }

    /**
     * 测试：对于（String,boolean,char,byte,short,int,float,double,long）的数组是深、浅拷贝验证
     */
    @Test
    public void basicJavaDataType(){
        final Object[] arrayString = new Object[]{"23", "43", "55", "12"};
        final Object[] arrayBoolean = new Object[] {true, true, true, true};
        final Object[] arrayChar = new Object[] {'a', 'b', 'c', 'd'};
        final Object[] arrayByte = new Object[] {(byte)23, (byte)43, (byte)55, (byte)12};
        final Object[] arrayShort = new Object[] {(short)23, (short)43, (short)55, (short)12};
        final Object[] arrayInt = new Object[] {23, 43, 55, 12};
        final Object[] arrayFloat = new Object[] {23.0f, 43.0f, 55.0f, 12.0f};
        final Object[] arrayDouble = new Object[] {23.0d, 43.0d, 55.0d, 12.0d};
        final Object[] arrayLong = new Object[] {23l, 43l, 55l, 12l};

        final int sourceArrayIndex = 0;
        final int copySourceArrayIndex = 1;

        //String[]
        System.out.println("========String[]=============");
        basicJavaDataType(arrayString , sourceArrayIndex , "9" ,
                copySourceArrayIndex , "12");
        System.out.println();

        //boolean[]
        System.out.println("========boolean[]============");
        basicJavaDataType(arrayBoolean , sourceArrayIndex , false ,
                copySourceArrayIndex , false);
        System.out.println();

        //char[]
        System.out.println("========char[]=============");
        basicJavaDataType(arrayChar , sourceArrayIndex , "f" ,
                copySourceArrayIndex , "m");
        System.out.println();

        //byte[]
        System.out.println("========byte[]============");
        basicJavaDataType(arrayByte , sourceArrayIndex , (byte)9 ,
                copySourceArrayIndex , (byte)12);
        System.out.println();

        //short[]
        System.out.println("========short[]============");
        basicJavaDataType(arrayShort , sourceArrayIndex , (short)9 ,
                copySourceArrayIndex , (short)12);
        System.out.println();

        //int[]
        System.out.println("========int[]============");
        basicJavaDataType(arrayInt , sourceArrayIndex , 9,
                copySourceArrayIndex , 12);
        System.out.println();

        //float[]
        System.out.println("========float[]============");
        basicJavaDataType(arrayFloat , sourceArrayIndex , 9.01f ,
                copySourceArrayIndex , 12.01f);
        System.out.println();

        //double[]
        System.out.println("========double[]============");
        basicJavaDataType(arrayDouble , sourceArrayIndex , 9.01d ,
                copySourceArrayIndex , 12.01d);
        System.out.println();

        //long[]
        System.out.println("========long[]============");
        basicJavaDataType(arrayLong , sourceArrayIndex , 9l ,
                copySourceArrayIndex , 12l);
    }

    /**
     * 按类型执行基本类型（除了String外）数组的深、浅拷贝
     * @param sourceArray 原数组
     * @param sourceArrayIndex 原数组第i个位置
     * @param sourceArrayIndexValue 原数组第i个位置的值
     * @param copySourceArrayIndex 复制后的数组第i个位置
     * @param copySourceArrayIndexValue 复制后的数组第i个位置的值
     */
    private void basicJavaDataType(Object[] sourceArray , int sourceArrayIndex , Object sourceArrayIndexValue ,
                                   int copySourceArrayIndex ,  Object copySourceArrayIndexValue){

        Object[] array = sourceArray;
        int newLength = array.length;
        Object[] copiedArray = Arrays.copyOf(array, newLength);
        Assert.assertArrayEquals(copiedArray, array);

        //改变array和copiedArray之前的值：
        System.out.println("array:{}" + Arrays.toString(array));
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        System.out.println();


        //改变array中的值
        array[sourceArrayIndex] = sourceArrayIndexValue;
        System.out.println("array:{}" + Arrays.toString(array));
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        Assert.assertTrue(copiedArray[0] != array[0]);
        System.out.println();

        //改变copiedArray中的值
        copiedArray[copySourceArrayIndex] = copySourceArrayIndexValue;
        System.out.println("array:{}" + Arrays.toString(array));
        System.out.println("copiedArray:{}" + Arrays.toString(copiedArray));
        Assert.assertTrue(copiedArray[1] != array[1]);

    }


    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaArrayClone_thenValueChangeIsSuccessful(){
        int[] array = {23, 43, 55, 12};
        
        int[] copiedArray = array.clone();
        
        Assert.assertArrayEquals(copiedArray, array);
        array[0] = 9;
        assertTrue(copiedArray[0] != array[0]);
        copiedArray[1] = 12;
        assertTrue(copiedArray[1] != array[1]);
    }

    @Test
    public void givenArraysOfNonPrimitiveType_whenCopiedViaArrayClone_thenDoShallowCopy(){
        Employee[] copiedArray = employees.clone();
        
        Assert.assertArrayEquals(copiedArray, employees);;
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element changed the copied array
        assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }

    @Test
    public void givenArraysOfCloneableNonPrimitiveType_whenCopiedViaArrayClone_thenDoShallowCopy(){
        Address[] addresses = createAddressArray();
        
        Address[] copiedArray = addresses.clone();
        
        addresses[0].setCity(addresses[0].getCity()+"_Changed");
        Assert.assertArrayEquals(copiedArray, addresses);
    }

    @Test
    public void givenArraysOfSerializableNonPrimitiveType_whenCopiedViaSerializationUtils_thenDoDeepCopy(){
        Employee[] copiedArray = SerializationUtils.clone(employees);
       
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element didn't change in the copied array
        Assert.assertFalse(
            copiedArray[0].getName().equals(employees[0].getName()));
    }
    
    @Test
    public void givenArraysOfNonPrimitiveType_whenCopiedViaStream_thenDoShallowCopy(){
        Employee[] copiedArray = Arrays.stream(employees).toArray(Employee[]::new);
        
        Assert.assertArrayEquals(copiedArray, employees);
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element didn't change in the copied array
        assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }
    
    @Test
    public void givenArraysOfPrimitiveType_whenCopiedViaStream_thenSuccessful(){
        String[] strArray = {"orange", "red", "green'"};
        
        String[] copiedArray = Arrays.stream(strArray).toArray(String[]::new);
        
        Assert.assertArrayEquals(copiedArray, strArray);
    }
    
    private Address[] createAddressArray(){
        Address[] addresses = new Address[1];
        addresses[0] = createAddress();
        return addresses;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setCountry("USA");
        address.setState("CA");
        address.setCity("San Francisco");
        address.setStreet("Street 1");
        address.setZipcode("59999");
        return address;
    }
}
