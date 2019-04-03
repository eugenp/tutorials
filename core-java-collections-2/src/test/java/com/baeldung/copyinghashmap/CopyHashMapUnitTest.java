package com.baeldung.copyinghashmap;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class CopyHashMapUnitTest {
    
    @Test
    public void givenHashmap_whenCopy_thenCopyContainsAllMappings() {
        
        HashMap<String, String> colorMap = new HashMap<>();
        colorMap.put("1", "Red");
        colorMap.put("2", "Blue");
        colorMap.put("3", "Green");
        
        System.out.println("ColorMap content : " + colorMap);
        
        HashMap<String, String> colorMapCopy = new HashMap<>();
        colorMapCopy.put("1", "Orange");
        colorMapCopy.put("4", "Black");
        
        colorMapCopy = CopyHashMap.basicCopy(colorMap, colorMapCopy);
        
        System.out.println("ColorMapCopy content : " +colorMapCopy);
        
        assertEquals(4, colorMapCopy.size());
    }
    
    @Test
    public void givenHashMap_whenShallowCopy_thenCopyisNotSameAsOriginal() {
        
        HashMap<String, Employee> employeeMap = new HashMap<>();
        Employee emp1 = new Employee("John", "Smith");
        Employee emp2 = new Employee("Norman", "Lewis");
        employeeMap.put("employee1",emp1);
        employeeMap.put("employee2",emp2);
    
        HashMap employeeMapShallowCopy = CopyHashMap.shallowCopy(employeeMap);
        
        assertThat(employeeMapShallowCopy).isNotSameAs(employeeMap);
        
    }
    
    @Test
    public void givenHashMap_whenShallowCopyModifyingOriginalObject_thenCopyShouldChange() {
        
        HashMap<String, Employee> employeeMap = new HashMap<>();
        Employee emp1 = new Employee("John", "Smith");
        Employee emp2 = new Employee("Norman", "Lewis");
        employeeMap.put("employee1",emp1);
        employeeMap.put("employee2",emp2);
        HashMap employeeMapShallowCopy = CopyHashMap.shallowCopy(employeeMap);
        
        emp1.setFirstName("Johny");
        
        assertThat(employeeMapShallowCopy.get("employee1"))
        .isEqualTo(employeeMap.get("employee1"));
        
    }
    
    @Test
    public void givenHashMap_whenDeepCopyModifyingOriginalObject_thenCopyShouldNotChange() {
        
        HashMap<String, Employee> employeeMap = new HashMap<>();
        Employee emp1 = new Employee("John", "Smith");
        Employee emp2 = new Employee("Norman", "Lewis");
        employeeMap.put("employee1",emp1);
        employeeMap.put("employee2",emp2);
        HashMap employeeMapDeepCopy = CopyHashMap.deepCopy(employeeMap);
        
        emp1.setFirstName("Johny");
        
        assertThat(employeeMapDeepCopy.get("employee1"))
        .isNotEqualTo(employeeMap.get("employee1"));
        
    }  
    
    @Test
    public void givenImmutableMap_whenCopyUsingGuava_thenCopyShouldNotChange() {
        
        Map<String, Integer> heightMap = ImmutableMap.<String, Integer> builder()
            .put("emp1", 160)
            .put("emp2", 165)
            .put("emp3", 163)
            .build();
        Map<String, Integer> heightMapCopy = ImmutableMap.copyOf(heightMap);
        
        assertThat(heightMapCopy).isSameAs(heightMap);
        
    }
    

}
