package com.baeldung.convertarraytostring;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ArrayToStringUnitTest {
    
    // convert with Java
    
    @Test
    public void givenAStringArray_whenConvertBeforeJava8_thenReturnString() {

        String[] strArray = { "Convert", "Array", "With", "Java" };
        StringBuilder stringBuilder = new StringBuilder();
        
        for (int i = 0; i < strArray.length; i++) {
            stringBuilder.append(strArray[i]);
        }
        String joinedString = stringBuilder.toString();
        
        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertArrayWithJava", joinedString);
    }

    @Test
    public void givenAString_whenConvertBeforeJava8_thenReturnStringArray() {

        String input = "lorem ipsum dolor sit amet";
        String[] strArray = input.split(" ");

        assertThat(strArray, instanceOf(String[].class));
        assertEquals(5, strArray.length);
        
        input = "loremipsum";
        strArray = input.split("");
        assertThat(strArray, instanceOf(String[].class));
        assertEquals(10, strArray.length);        
    }    
    
    @Test
    public void givenAnIntArray_whenConvertBeforeJava8_thenReturnString() {

        int[] strArray = { 1, 2, 3, 4, 5 };
        StringBuilder stringBuilder = new StringBuilder();
        
        for (int i = 0; i < strArray.length; i++) {
            stringBuilder.append(Integer.valueOf(strArray[i]));
        }
        String joinedString = stringBuilder.toString();
        
        assertThat(joinedString, instanceOf(String.class));
        assertEquals("12345", joinedString);
    }    
    
    // convert with Java Stream API
    
    @Test
    public void givenAStringArray_whenConvertWithJavaStream_thenReturnString() {

        String[] strArray = { "Convert", "With", "Java", "Streams" };
        String joinedString = Arrays.stream(strArray)
            .collect(Collectors.joining());
        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertWithJavaStreams", joinedString);
        
        joinedString = Arrays.stream(strArray)
            .collect(Collectors.joining(","));
        assertThat(joinedString, instanceOf(String.class));
        assertEquals("Convert,With,Java,Streams", joinedString);
    }    
    
    
    // convert with Apache Commons

    @Test
    public void givenAStringArray_whenConvertWithApacheCommons_thenReturnString() {

        String[] strArray = { "Convert", "With", "Apache", "Commons" };
        String joinedString = StringUtils.join(strArray);

        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertWithApacheCommons", joinedString);
    }
    
    @Test
    public void givenAString_whenConvertWithApacheCommons_thenReturnStringArray() {

        String input = "lorem ipsum dolor sit amet";
        String[] strArray = StringUtils.split(input, " ");
        
        assertThat(strArray, instanceOf(String[].class));
        assertEquals(5, strArray.length);
    }  

    
    // convert with Guava

    @Test
    public void givenAStringArray_whenConvertWithGuava_thenReturnString() {

        String[] strArray = { "Convert", "With", "Guava", null };
        String joinedString = Joiner.on("")
            .skipNulls()
            .join(strArray);

        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertWithGuava", joinedString);
    }
    
    
    @Test
    public void givenAString_whenConvertWithGuava_thenReturnStringArray() {

        String input = "lorem ipsum dolor sit amet";
                        
        List<String> resultList = Splitter.on(' ')
            .trimResults()
            .omitEmptyStrings()
            .splitToList(input);   
        String[] strArray = resultList.toArray(new String[0]);
        
        assertThat(strArray, instanceOf(String[].class));
        assertEquals(5, strArray.length);    
    }  
}
