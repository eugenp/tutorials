package com.baeldung.java.map.initialize;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class MapInitializerUnitTest {
    
    @Test
    public void givenStaticMap_whenUpdated_thenCorrect() {
        
        MapInitializer.articleMapOne.put("NewArticle1", "Convert array to List");
        
        assertEquals(MapInitializer.articleMapOne.get("NewArticle1"), "Convert array to List");
        
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void givenSingleTonMap_whenEntriesAdded_throwsException() {
        
        Map<String, String> map = MapInitializer.createSingletonMap();
        map.put("username2", "password2");
    }

}
