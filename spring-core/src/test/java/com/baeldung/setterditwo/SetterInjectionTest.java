package com.baeldung.setterditwo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.baeldung.setterditwo.domain.Music;
import static com.baeldung.setterditwo.SpringRunner.getMusicFromJavaConfig;
import static com.baeldung.setterditwo.SpringRunner.getMusicFromXml;

@RunWith(SpringJUnit4ClassRunner.class)
public class SetterInjectionTest {
    
    @Test
    public void givenInjectAnnotation_WhenOnField_ThenValidDependency() {
        Music OnTheRadio = getMusicFromXml();

        assertNotNull(OnTheRadio);
        assertEquals("Artist: Yo-Yo Ma 61 Song: Joy to the World", OnTheRadio.toString());
        
        OnTheRadio = getMusicFromJavaConfig();
        
        assertNotNull(OnTheRadio);
        assertEquals("Artist: Yo-Yo Ma 61 Song: Joy to the World", OnTheRadio.toString());
    }

}
