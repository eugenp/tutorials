package com.baeldung.java17;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.baeldung.java8to17.Address;
import com.baeldung.java8to17.Circle;
import com.baeldung.java8to17.Person;
import com.baeldung.java8to17.Rectangle;
import com.baeldung.java8to17.Student;

public class Java8to17ExampleUnitTest {

    @Test
    void givenMultiLineText_whenUsingTextBlock_thenStringIsReturned() {
        String value = """
            This is a
            Multi-line
            Text
            """;
        
        assertThat(value).isEqualTo("This is a\nMulti-line\nText\n");
    }
    
    @Test
    void givenString_whenUsingUtilFunctions_thenReturnsExpectedResult() {
        assertThat("  ".isBlank());
        assertThat("Twinkle ".repeat(2)).isEqualTo("Twinkle Twinkle ");
        assertThat("Format Line".indent(4)).isEqualTo("    Format Line\n");
        assertThat("Line 1 \n Line2".lines()).asList().size().isEqualTo(2);
        assertThat(" Text with white spaces   ".strip()).isEqualTo("Text with white spaces");
        assertThat("Car, Bus, Train".transform(s1 -> Arrays.asList(s1.split(","))).get(0)).isEqualTo("Car");
    }
    
    @Test
    void givenDataModel_whenUsingRecordType_thenBehavesLikeDTO() {
        Student student = new Student(10, "Priya");
        Student student2 = new Student(10, "Priya");
        
        assertThat(student.rollNo()).isEqualTo(10);
        assertThat(student.name()).isEqualTo("Priya");
        assertThat(student.equals(student2));
        assertThat(student.hashCode()).isEqualTo(student2.hashCode());
    }
    
    @Test 
    void givenObject_whenThrowingNPE_thenReturnsHelpfulMessage() {
        Person student = new Person("Lakshmi", new Address("35, West Street", null, null));
        
        Exception exception = assertThrows(NullPointerException.class, () -> {
            student.getAddress().getCity().toLowerCase();
        });
        
        assertThat(exception.getMessage()).isEqualTo(
            "Cannot invoke \"String.toLowerCase()\" because the return value of \"com.baeldung.java8to17.Address.getCity()\" is null");
    }
    
    @Test 
    void givenGenericObject_whenUsingPatternMatching_thenReturnsTargetType() {
        String city = null;
        Object obj = new Address("35, West Street", "Chennai", "6000041");

        if (obj instanceof Address address) {
            city = address.getCity();
        }
        
        assertThat(city).isEqualTo("Chennai");
    }
    
    @Test 
    void givenGenericObject_whenUsingSwitchExpression_thenPatternMatchesRightObject() {
        Object shape = new Rectangle(10, 20);
        
        double circumference = switch (shape) {
            case Rectangle r -> 2 * r.length() + 2 * r.width();
            case Circle c -> 2 * c.radius() * Math.PI;
            default -> throw new IllegalArgumentException("Unknown shape");
        };
        
        assertThat(circumference).isEqualTo(60);
    }

}
