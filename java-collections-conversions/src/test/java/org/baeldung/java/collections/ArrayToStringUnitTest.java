package org.baeldung.java.collections;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.common.base.Joiner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ArrayToStringUnitTest {

    @Test
    public void convertWithApacheCommons() {

        String[] strArray = { "Convert", "With", "Apache", "Commons" };
        String joinedString = StringUtils.join(strArray);

        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertWithApacheCommons", joinedString);
    }

    @Test
    public void convertWithGuava() {

        String[] strArray = { "Convert", "With", "Guava", null };
        String joinedString = Joiner.on("")
            .skipNulls()
            .join(strArray);

        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertWithGuava", joinedString);
    }

    @Test
    public void convertWithPlainJava() {

        String[] strArray = { "Convert", "Array", "With", "Plain", "Java" };

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArray.length; i++) {
            stringBuilder.append(String.valueOf(strArray[i]));
        }

        String joinedString = stringBuilder.toString();
        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertArrayWithPlainJava", joinedString);
    }

    @Test
    public void convertWithJava8() {

        String[] strArray = { "Convert", "With", "Java8" };
        String joinedString = Arrays.stream(strArray)
            .collect(Collectors.joining());
        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertWithJava8", joinedString);

        joinedString = String.join("", strArray);
        assertThat(joinedString, instanceOf(String.class));
        assertEquals("ConvertWithJava8", joinedString);
    }
}
