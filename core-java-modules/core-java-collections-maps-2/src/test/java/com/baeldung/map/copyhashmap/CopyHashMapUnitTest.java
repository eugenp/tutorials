package com.baeldung.map.copyhashmap;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CopyHashMapUnitTest {
    
    @Test
    public void givenHashMap_whenShallowCopy_thenCopyisNotSameAsOriginal() {
        
        HashMap<String, Employee> map = new HashMap<>();
        Employee emp1 = new Employee("John");
        Employee emp2 = new Employee("Norman");
        map.put("emp1",emp1);
        map.put("emp2",emp2);
    
        HashMap<String, Employee> shallowCopy = CopyHashMap.shallowCopy(map);
        
        assertThat(shallowCopy).isNotSameAs(map);
        
    }
    
    @Test
    public void givenHashMap_whenShallowCopyModifyingOriginalObject_thenCopyShouldChange() {
        
        HashMap<String, Employee> map = new HashMap<>();
        Employee emp1 = new Employee("John");
        Employee emp2 = new Employee("Norman");
        map.put("emp1",emp1);
        map.put("emp2",emp2);
        
        HashMap<String, Employee> shallowCopy = CopyHashMap.shallowCopy(map);
        
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
        HashMap<String, Employee> deepCopy = CopyHashMap.deepCopy(map);
        
        emp1.setName("Johny");
        
        assertThat(deepCopy.get("emp1")).isNotEqualTo(map.get("emp1"));
        
    }
    
    @Test
    public void givenImmutableMap_whenCopyUsingGuava_thenCopyShouldNotChange() {
        Employee emp1 = new Employee("John");
        Employee emp2 = new Employee("Norman");
        
        Map<String, Employee> map = ImmutableMap.<String, Employee> builder()
            .put("emp1",emp1)
            .put("emp2",emp2)
            .build();
        Map<String, Employee> shallowCopy = ImmutableMap.copyOf(map);
        
        assertThat(shallowCopy).isSameAs(map);
        
    }

}
