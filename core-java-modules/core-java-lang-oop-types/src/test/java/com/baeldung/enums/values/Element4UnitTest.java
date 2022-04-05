/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.enums.values;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chris
 */
public class Element4UnitTest {
    
    public Element4UnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void whenLocatebyLabel_thenReturnCorrectValue() {
        for (Element4 e4 : Element4.values()) {
            assertSame(e4, Element4.valueOfLabel(e4.label));
        }
    }

    @Test
    public void whenLocatebyAtmNum_thenReturnCorrectValue() {
        for (Element4 e4 : Element4.values()) {
            assertSame(e4, Element4.valueOfAtomicNumber(e4.atomicNumber));
        }
    }

    @Test
    public void whenLocatebyAtmWt_thenReturnCorrectValue() {
        for (Element4 e4 : Element4.values()) {
            assertSame(e4, Element4.valueOfAtomicWeight(e4.atomicWeight));
        }
    }

    /**
     * Test of toString method, of class Element4.
     */
    @Test
    public void whenCallingToString_thenReturnLabel() {
        for (Element4 e4 : Element4.values()) {
            assertEquals(e4.label, e4.toString());
        }
    }
    
}
