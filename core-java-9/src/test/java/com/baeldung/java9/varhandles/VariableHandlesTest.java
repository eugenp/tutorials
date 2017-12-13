package com.baeldung.java9.varhandles;

import org.junit.Assert;
import org.junit.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VariableHandlesTest {
    public int publicTestVariable = 1;
    private int privateTestVariable = 1;
    public int variableToSet = 1;
    public int variableToCompareAndSet = 1;
    public int variableToGetAndAdd = 0;
    public byte variableToBitwiseOr = 0;
    @Test
    public void whenVariableHandleForPublicVariableIsCreated_ThenItIsInitializedProperly()
            throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles.lookup()
                .in(VariableHandlesTest.class)
                .findVarHandle(VariableHandlesTest.class,"publicTestVariable", int.class);
        Assert.assertEquals(1, publicIntHandle.coordinateTypes().size());
        Assert.assertEquals(VariableHandlesTest.class, publicIntHandle.coordinateTypes().get(0));

    }

    @Test
    public void whenVariableHandleForPrivateVariableIsCreated_ThenItIsInitializedProperly()
            throws NoSuchFieldException, IllegalAccessException {
        VarHandle privateIntHandle = MethodHandles.privateLookupIn(VariableHandlesTest.class, MethodHandles.lookup())
                .findVarHandle(VariableHandlesTest.class,"privateTestVariable", int.class);
        Assert.assertNotNull(privateIntHandle);
        Assert.assertEquals(1, privateIntHandle.coordinateTypes().size());
        Assert.assertEquals(VariableHandlesTest.class, privateIntHandle.coordinateTypes().get(0));

    }

    @Test
    public void whenVariableHandleForArrayVariableIsCreated_ThenItIsInitializedProperly()
            throws NoSuchFieldException, IllegalAccessException {
        VarHandle arrayVarHandle = MethodHandles.arrayElementVarHandle(int[].class);
        Assert.assertEquals(2, arrayVarHandle.coordinateTypes().size());
        Assert.assertEquals(int[].class, arrayVarHandle.coordinateTypes().get(0));
    }

    @Test
    public void givenVarHandle_whenGetIsInvoked_ThenValueOfVariableIsReturned() throws NoSuchFieldException, IllegalAccessException {
            VarHandle publicIntHandle = MethodHandles.lookup()
                    .in(VariableHandlesTest.class)
                    .findVarHandle(VariableHandlesTest.class,"publicTestVariable", int.class);
            Assert.assertEquals(1, publicIntHandle.get(this));
    }

    @Test
    public void givenVarHandle_whenSetIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles.lookup()
                .in(VariableHandlesTest.class)
                .findVarHandle(VariableHandlesTest.class,"variableToSet", int.class);
        publicIntHandle.set(this, 15);
        Assert.assertEquals(15, publicIntHandle.get(this));
    }

    @Test
    public void givenVarHandle_whenCompareAndSetIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles.lookup()
                .in(VariableHandlesTest.class)
                .findVarHandle(VariableHandlesTest.class,"variableToCompareAndSet", int.class);
        publicIntHandle.compareAndSet(this, 1,100);
        Assert.assertEquals(100, publicIntHandle.get(this));
    }

    @Test
    public void givenVarHandle_whenGetAndAddIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles.lookup()
                .in(VariableHandlesTest.class)
                .findVarHandle(VariableHandlesTest.class,"variableToGetAndAdd", int.class);
        int before = (int) publicIntHandle.getAndAdd(this, 200);
        Assert.assertEquals(0, before);
        Assert.assertEquals(200, publicIntHandle.get(this));
    }

    @Test
    public void givenVarHandle_whenGetAndBitwiseOrIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles.lookup()
                .in(VariableHandlesTest.class)
                .findVarHandle(VariableHandlesTest.class,"variableToBitwiseOr", byte.class);
        byte before = (byte) publicIntHandle.getAndBitwiseOr(this, (byte) 127);
        Assert.assertEquals(0, before);
        Assert.assertEquals(127, variableToBitwiseOr);
    }
}
