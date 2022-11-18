/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.deepvsshallow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Mantas
 */
public class StudentConstructorExampleObjTest {

    public StudentConstructorExampleObjTest() {
    }

    /**
     * Test of getName method, of class StudentConstructorExampleObj.
     */
    @org.junit.jupiter.api.Test
    public void tesGetFullInfo() {
        StudentConstructorExampleObj jane = new StudentConstructorExampleObj(new Name("Jane", "j"), 15);
        StudentConstructorExampleObj kate = new StudentConstructorExampleObj(jane);

        jane.setAge(0);
        jane.name.name = "TEST";
        assertEquals(jane.printInfo(), kate.printInfo());//AssertionFailedError: expected: <Name= TEST, Surname= j, age= 0> but was: <Name= TEST, Surname= j, age= 15>
    }

}
