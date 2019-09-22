package org.baeldung.guava.maps.initialize;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import java.util.Map;
import org.junit.Test;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class GuavaMapInitializeUnitTest {

    @Test
    public void givenKeyValuesShoudInitializeMap() {
        Map<String, String> articles = ImmutableMap.of("Title", "My New Article", "Title2", "Second Article");
        
        assertThat(articles.get("Title"), equalTo("My New Article"));
        assertThat(articles.get("Title2"), equalTo("Second Article"));

    }
    
    
    @Test
    public void givenKeyValuesShouldCreateMutableMap() {
        Map<String, String> articles = Maps.newHashMap(ImmutableMap.of("Title", "My New Article", "Title2", "Second Article"));

        assertThat(articles.get("Title"), equalTo("My New Article"));
        assertThat(articles.get("Title2"), equalTo("Second Article"));
    }   
}
