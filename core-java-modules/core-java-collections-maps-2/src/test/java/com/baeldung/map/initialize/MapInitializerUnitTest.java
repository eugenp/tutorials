package com.baeldung.map.initialize;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

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
