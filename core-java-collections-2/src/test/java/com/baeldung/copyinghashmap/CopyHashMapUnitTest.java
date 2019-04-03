package com.baeldung.copyinghashmap;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class CopyHashMapUnitTest {
    
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
    public void givenHashMapWithValuesInCms_whenCopy_thenCopyMapShouldHaveValuesInInches() {
        
        HashMap<String, Integer> heightMap = new HashMap<>();
        heightMap.put("emp1", 160);
        heightMap.put("emp2", 165);
        heightMap.put("emp3", 163);
        HashMap heightMapInInches = CopyHashMap.copyMapAndConvertCmsToInches(heightMap);
        
        assertThat(heightMap).isNotEqualTo(heightMapInInches);
        
        assertThat(heightMap.get("emp1")/2.54).isEqualTo(heightMapInInches.get("emp1"));
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
