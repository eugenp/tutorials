package com.baeldung.constructorditwo;

import static com.baeldung.constructorditwo.SpringRunner.getMusicFromJavaConfig;
import static com.baeldung.constructorditwo.SpringRunner.getMusicFromXml;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.baeldung.constructorditwo.domain.Music;

@RunWith(SpringJUnit4ClassRunner.class)
public class ConstructorInjectionTest {
    
    @Test
    public void givenConstructorInjection_ThenValidDependency() {
        Music OnTheRadio = getMusicFromXml();

        assertNotNull(OnTheRadio);
        assertEquals("Artist: Yo-Yo Ma 61 Song: Joy to the World", OnTheRadio.toString());
        
        OnTheRadio = getMusicFromJavaConfig();
        
        assertNotNull(OnTheRadio);
        assertEquals("Artist: Yo-Yo Ma 61 Song: Joy to the World", OnTheRadio.toString());
    }

}
