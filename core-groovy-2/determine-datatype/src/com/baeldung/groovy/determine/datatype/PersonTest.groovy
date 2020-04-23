package com.baeldung.groovy.determine.datatype;

import org.junit.Assert
import org.junit.Test;

public class PersonTest {
    
    @Test
    public void givenWhenParameterTypeIsInteger_thenReturnTrue() {
        Person personObj = new Person(10)
        Assert.assertTrue(personObj.ageAsInt instanceof Integer);
    }
    
    @Test
    public void givenWhenParameterTypeIsDouble_thenReturnTrue() {
        Person personObj = new Person(10.0)
        Assert.assertTrue((personObj.ageAsDouble).getClass() == Double)
    }
    
    @Test
    public void givenWhenParameterTypeIsString_thenReturnTrue() {
        Person personObj = new Person("10 years")
        Assert.assertTrue(personObj.ageAsString.class == String)
    }
    
    @Test
    public void givenClassName_WhenParameterIsInteger_thenReturnTrue() {
        Assert.assertTrue(Person.class.getDeclaredField('ageAsInt').type == int.class)
    }
    
    @Test
    public void givenWhenObjectIsInstanceOfType_thenReturnTrue() {
        Person personObj = new Person()
        Assert.assertTrue(personObj instanceof Person)
    }

    @Test
    public void givenWhenInstanceIsOfSubtype_thenReturnTrue() {
        Student studentObj = new Student()
        Assert.assertTrue(studentObj in Person)
    }
    
    @Test
    public void givenGroovyList_WhenFindClassName_thenReturnTrue() {
      def ageList = ['ageAsString','ageAsDouble', 10]   
      Assert.assertTrue(ageList.class == ArrayList)     
      Assert.assertTrue(ageList.getClass() == ArrayList)
    }
    
    @Test
    public void givenGrooyMap_WhenFindClassName_thenReturnTrue() {
      def ageMap = [ageAsString: '10 years', ageAsDouble: 10.0]
      Assert.assertFalse(ageMap.class == LinkedHashMap)
    }
}