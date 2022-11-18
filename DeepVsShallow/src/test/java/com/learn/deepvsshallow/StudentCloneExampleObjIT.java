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
public class StudentCloneExampleObjIT {
    
    public StudentCloneExampleObjIT() {
    }

    /**
     * Test of clone method, of class StudentCloneExampleObj.
     */
    @Test
    public void testClone() {
      StudentCloneExampleObj janeClone = new StudentCloneExampleObj(new Name("Jane", "j"), 15);
        StudentCloneExampleObj kateClone = janeClone.clone();
        janeClone.name.name = "TEST";
        assertEquals(janeClone.getName().name, kateClone.getName().name);
    }
    
}
