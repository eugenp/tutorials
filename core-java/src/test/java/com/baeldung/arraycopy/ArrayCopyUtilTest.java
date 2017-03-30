package com.baeldung.arraycopy;

import com.baeldung.arraycopy.model.Address;
import com.baeldung.arraycopy.model.Employee;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

public class ArrayCopyUtilTest {
    private static Employee[] employees;
    private static final int MAX = 2;

    @BeforeClass
    public static void setup(){
      employees = new Employee[MAX];
      Employee employee;
      for(int i = 0; i < MAX; i++) {
          employee = new Employee();
          employee.setName("Emp"+i);
          employee.setId(i);
          employees[i] = employee;
      }
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaSystemsArrayCopy_thenSuccessful(){
        int[] array = {23, 43, 55};
        int[] copiedArray = new int[3];
        
        System.arraycopy(array, 0, copiedArray, 0, 3);
        
        Assert.assertTrue(array.length == copiedArray.length);
        Assert.assertTrue(copiedArray[0] == array[0]);
        Assert.assertTrue(copiedArray[1] == array[1]);
        Assert.assertTrue(copiedArray[2] == array[2]);
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedSubSequenceViaSystemsArrayCopy_thenSuccessful(){
        int[] array = {23, 43, 55, 12, 65, 88, 92};
        int[] copiedArray = new int[3];
        
        System.arraycopy(array, 2, copiedArray, 0, 3);
        
        Assert.assertTrue(3 == copiedArray.length);
        Assert.assertTrue(copiedArray[0] == array[2]);
        Assert.assertTrue(copiedArray[1] == array[3]);
        Assert.assertTrue(copiedArray[2] == array[4]);
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedSubSequenceViaArraysCopyOfRange_thenSuccessful(){
        int[] array = {23, 43, 55, 12, 65, 88, 92};
        
        int[] copiedArray = Arrays.copyOfRange(array, 1, 4);
        
        Assert.assertTrue(3 == copiedArray.length);
        Assert.assertTrue(copiedArray[0] == array[1]);
        Assert.assertTrue(copiedArray[1] == array[2]);
        Assert.assertTrue(copiedArray[2] == array[3]);
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaArraysCopyOf_thenValueChangeIsSuccessful(){
        int[] array = {23, 43, 55, 12};
        int newLength = array.length;
        
        int[] copiedArray = Arrays.copyOf(array, newLength);
        
        Assert.assertNotNull(copiedArray);
        Assert.assertTrue(copiedArray.length == array.length);
        Assert.assertTrue(copiedArray[0] == array[0]);
        Assert.assertTrue(copiedArray[1] == array[1]);
        Assert.assertTrue(copiedArray[2] == array[2]);
        Assert.assertTrue(copiedArray[3] == array[3]);
        array[0] = 9;
        Assert.assertTrue(copiedArray[0] != array[0]);
        copiedArray[1] = 12;
        Assert.assertTrue(copiedArray[1] != array[1]);
    }

    @Test
    public void givenArrayOfNonPrimitiveType_whenCopiedViaArraysCopyOf_thenDoShallowCopy(){
        Employee[] copiedArray = Arrays.copyOf(employees, employees.length);
        
        Assert.assertNotNull(copiedArray);
        Assert.assertTrue(copiedArray.length == employees.length);
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element caused change in the copied array
        Assert.assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }

    @Test
    public void givenArrayOfPrimitiveType_whenCopiedViaArrayClone_thenValueChangeIsSuccessful(){
        int[] array = {23, 43, 55, 12};
        
        int[] copiedArray = array.clone();
        
        Assert.assertNotNull(copiedArray);
        Assert.assertTrue(copiedArray.length == array.length);
        Assert.assertTrue(copiedArray[0] == array[0]);
        Assert.assertTrue(copiedArray[1] == array[1]);
        Assert.assertTrue(copiedArray[2] == array[2]);
        Assert.assertTrue(copiedArray[3] == array[3]);
        array[0] = 9;
        Assert.assertTrue(copiedArray[0] != array[0]);
        copiedArray[1] = 12;
        Assert.assertTrue(copiedArray[1] != array[1]);
    }

    @Test
    public void givenArraysOfNonPrimitiveType_whenCopiedViaArrayClone_thenDoShallowCopy(){
        Employee[] copiedArray = employees.clone();
        
        Assert.assertNotNull(copiedArray);
        Assert.assertTrue(copiedArray.length == employees.length);
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element changed the copied array
        Assert.assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }

    @Test
    public void givenArraysOfCloneableNonPrimitiveType_whenCopiedViaArrayClone_thenDoShallowCopy(){
        Address[] addresses = createAddressArray();
        
        Address[] copiedArray = addresses.clone();
        
        Assert.assertNotNull(copiedArray);
        Assert.assertTrue(copiedArray.length == addresses.length);
        addresses[0].setCity(addresses[0].getCity()+"_Changed");
        Assert.assertTrue(copiedArray[0].getCity().equals(addresses[0].getCity()));
    }

    @Test
    public void givenArraysOfSerializableNonPrimitiveType_whenCopiedViaSerializationUtils_thenDoDeepCopy(){
        Employee[] copiedArray = SerializationUtils.clone(employees);
        
        Assert.assertNotNull(copiedArray);
        Assert.assertTrue(copiedArray.length == employees.length);
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element didn't change in the copied array
        Assert.assertFalse(copiedArray[0].getName().equals(employees[0].getName()));
    }
    
    @Test
    public void givenArraysOfNonPrimitiveType_whenCopiedViaStream_thenDoShallowCopy(){
        Employee[] copiedArray = Arrays.stream(employees).toArray(Employee[]::new);
        
        Assert.assertNotNull(copiedArray);
        Assert.assertTrue(copiedArray.length == employees.length);
        employees[0].setName(employees[0].getName()+"_Changed");
        //change in employees' element didn't change in the copied array
        Assert.assertTrue(copiedArray[0].getName().equals(employees[0].getName()));
    }
    
    @Test
    public void givenArraysOfPrimitiveType_whenCopiedViaStream_thenSuccessful(){
        String[] strArray = {"orange", "red", "green'"};
        
        String[] copiedArray = Arrays.stream(strArray).toArray(String[]::new);
        
        Assert.assertNotNull(copiedArray);
        Assert.assertTrue(copiedArray.length == strArray.length);
        Assert.assertTrue(copiedArray[0] == strArray[0]);
        Assert.assertTrue(copiedArray[1] == strArray[1]);
        Assert.assertTrue(copiedArray[2] == strArray[2]);
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
