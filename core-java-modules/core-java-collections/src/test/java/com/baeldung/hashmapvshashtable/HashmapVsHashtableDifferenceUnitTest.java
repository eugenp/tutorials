package com.baeldung.hashmapvshashtable;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;

public class HashmapVsHashtableDifferenceUnitTest {

    // null values
    @Test(expected = NullPointerException.class)
    public void givenHashtable_whenAddNullKey_thenNullPointerExceptionThrown() {
        Hashtable<String, String> table = new Hashtable<String, String>();        
        table.put(null, "value");
    }
    
    @Test(expected = NullPointerException.class)
    public void givenHashtable_whenAddNullValue_thenNullPointerExceptionThrown() {
        Hashtable<String, String> table = new Hashtable<String, String>();        
        table.put("key", null);
    }
    
    @Test
    public void givenHashmap_whenAddNullKeyAndValue_thenObjectAdded() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(null, "value");
        map.put("key1", null);
        map.put("key2", null);
        
        assertEquals(3, map.size());
    }
    
    // fail-fast iterator
    @Test(expected = ConcurrentModificationException.class)
    public void givenHashmap_whenModifyUnderlyingCollection_thenConcurrentModificationExceptionThrown() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        
        Iterator<String> iterator = map.keySet().iterator();
        while(iterator.hasNext()){ 
            iterator.next();
            map.put("key4", "value4");
        }        
    }

    @Test
    public void givenHashtable_whenModifyUnderlyingCollection_thenItHasNoEffectOnIteratedCollection() {
        Hashtable<String, String> table = new Hashtable<String, String>();        
        table.put("key1", "value1");
        table.put("key2", "value2");
        
        List<String> keysSelected = Lists.newArrayList(); 
        Enumeration<String> keys = table.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            keysSelected.add(key);
            
            if (key.equals("key1")) {
                table.put("key3", "value3");
            }
        }
        
        assertEquals(2, keysSelected.size());
    }
    
    // synchronized map
    @Test
    public void givenHashmap_thenCreateSynchronizedMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        
        Set<Entry<String, String>> set = map.entrySet();
        synchronized (map) {
            Iterator<Entry<String, String>> it = set.iterator();
            while(it.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>)it.next();
             }            
        }
        
        Map<String, String> syncMap = Collections.synchronizedMap(map);
    }
}
