package com.baeldung.keyword.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.baeldung.keyword.Circle;
import com.baeldung.keyword.Ring;
import com.baeldung.keyword.Round;
import com.baeldung.keyword.Shape;
import com.baeldung.keyword.Triangle;

public class InstanceOfUnitTest {

    @Test
    public void giveWhenInstanceIsCorrect_thenReturnTrue() {
        Ring ring = new Ring();
        Assert.assertTrue("ring is instance of Round ", ring instanceof Round);
    }

    @Test
    public void giveWhenObjectIsInstanceOfType_thenReturnTrue() {
        Circle circle = new Circle();
        Assert.assertTrue("circle is instance of Circle ", circle instanceof Circle);
    }

  
    @Test
    public void giveWhenInstanceIsOfSubtype_thenReturnTrue() {
        Circle circle = new Circle();
        Assert.assertTrue("circle is instance of Round", circle instanceof Round);
    }

    @Test
    public void giveWhenTypeIsInterface_thenReturnTrue() {
        Circle circle = new Circle();
        Assert.assertTrue("circle is instance of Shape", circle instanceof Shape);
    }
    
    @Test
    public void giveWhenTypeIsOfObjectType_thenReturnTrue() {
        Thread thread = new Thread();
        Assert.assertTrue("thread is instance of Object", thread instanceof Object);
    }

    @Test
    public void giveWhenInstanceValueIsNull_thenReturnFalse() {
        Circle circle = null;
        Assert.assertFalse("circle is instance of Round", circle instanceof Round);
    }

    @Test
    public void giveWhenComparingClassInDiffHierarchy_thenCompilationError() {
        // Assert.assertFalse("circle is instance of Triangle", circle instanceof Triangle);
    }
}
