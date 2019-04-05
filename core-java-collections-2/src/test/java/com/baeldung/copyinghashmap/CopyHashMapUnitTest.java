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
        
        HashMap<String, Employee> map = new HashMap<>();
        Employee emp1 = new Employee("John");
        Employee emp2 = new Employee("Norman");
        map.put("emp1",emp1);
        map.put("emp2",emp2);
    
        HashMap shallowCopy = CopyHashMap.shallowCopy(map);
        
        assertThat(shallowCopy).isNotSameAs(map);
        
    }
    
    @Test
    public void givenHashMap_whenShallowCopyModifyingOriginalObject_thenCopyShouldChange() {
        
        HashMap<String, Employee> map = new HashMap<>();
        Employee emp1 = new Employee("John");
        Employee emp2 = new Employee("Norman");
        map.put("emp1",emp1);
        map.put("emp2",emp2);
        
        HashMap shallowCopy = CopyHashMap.shallowCopy(map);
        
        emp1.setName("Johny");
        
        assertThat(shallowCopy.get("emp1")).isEqualTo(map.get("emp1"));
        
    }
    
    @Test
    public void givenHashMap_whenDeepCopyModifyingOriginalObject_thenCopyShouldNotChange() {
        
        HashMap<String, Employee> map = new HashMap<>();
        Employee emp1 = new Employee("John");
        Employee emp2 = new Employee("Norman");
        map.put("emp1",emp1);
        map.put("emp2",emp2);
        HashMap deepCopy = CopyHashMap.deepCopy(map);
        
        emp1.setName("Johny");
        
        assertThat(deepCopy.get("emp1")).isNotEqualTo(map.get("emp1"));
        
    } 
    
    @Test
    public void givenHashMap_whenCopy_thenCopyMapShouldHaveValuesDivideBy2() {
        
        HashMap<String, Integer> heightMap = new HashMap<>();
        heightMap.put("emp1", 160);
        heightMap.put("emp2", 165);
        heightMap.put("emp3", 163);
        HashMap heightMapCopy = CopyHashMap.copyMapAndDivideValuesBy2(heightMap);
        
        assertThat(heightMap).isNotEqualTo(heightMapCopy);
        
        assertThat(heightMap.get("emp1")/2).isEqualTo(heightMapCopy.get("emp1"));
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
