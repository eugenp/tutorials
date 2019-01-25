package com.baeldung.inheritancecomposition.test;

import com.baeldung.inheritancecomposition.model.Actress;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * Actress测试类，测试继承关系
 */
public class ActressUnitTest {
    
    private static Actress actress;
    
    @BeforeClass
    public static void setUpActressInstance() {
        actress = new Actress("Susan", "susan@domain.com", 30);
    }
    
    @Test
    public void givenActressInstance_whenCalledgetName_thenEqual() {
        System.out.println("actress.getName():{}" + actress.getName());
        assertThat(actress.getName()).isEqualTo("Susan");
    }
    
    @Test
    public void givenActressInstance_whenCalledgetEmail_thenEqual() {
        System.out.println("actress.getEmail():{}" + actress.getEmail());
        assertThat(actress.getEmail()).isEqualTo("susan@domain.com");
    }
    
    @Test
    public void givenActressInstance_whenCalledgetAge_thenEqual() {
        System.out.println("actress.getAge():{}" + actress.getAge());
        assertThat(actress.getAge()).isEqualTo(30);
    }
    
    @Test
    public void givenActressInstance_whenCalledreadScript_thenEqual() {
        assertThat(actress.readScript("Psycho")).isEqualTo("Reading the script of Psycho");
    }
    
    @Test
    public void givenActressInstance_whenCalledperfomRole_thenEqual() {
        assertThat(actress.performRole()).isEqualTo("Performing a role");
    }
}
