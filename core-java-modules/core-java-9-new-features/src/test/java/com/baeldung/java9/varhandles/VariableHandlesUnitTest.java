package com.baeldung.java9.varhandles;

import org.junit.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

import static org.junit.Assert.assertEquals;

public class VariableHandlesUnitTest {

    public int publicTestVariable = 1;
    private int privateTestVariable = 1;
    public int variableToSet = 1;
    public int variableToCompareAndSet = 1;
    public int variableToGetAndAdd = 0;
    public byte variableToBitwiseOr = 0;

    @Test
    public void whenVariableHandleForPublicVariableIsCreated_ThenItIsInitializedProperly() throws NoSuchFieldException, IllegalAccessException {
        VarHandle PUBLIC_TEST_VARIABLE = MethodHandles
          .lookup()
          .in(VariableHandlesUnitTest.class)
          .findVarHandle(VariableHandlesUnitTest.class, "publicTestVariable", int.class);

        assertEquals(1, PUBLIC_TEST_VARIABLE.coordinateTypes().size());
        assertEquals(VariableHandlesUnitTest.class, PUBLIC_TEST_VARIABLE.coordinateTypes().get(0));
    }

    @Test
    public void whenVariableHandleForPrivateVariableIsCreated_ThenItIsInitializedProperly() throws NoSuchFieldException, IllegalAccessException {
        VarHandle PRIVATE_TEST_VARIABLE = MethodHandles
          .privateLookupIn(VariableHandlesUnitTest.class, MethodHandles.lookup())
          .findVarHandle(VariableHandlesUnitTest.class, "privateTestVariable", int.class);

        assertEquals(1, PRIVATE_TEST_VARIABLE.coordinateTypes().size());
        assertEquals(VariableHandlesUnitTest.class, PRIVATE_TEST_VARIABLE.coordinateTypes().get(0));
    }

    @Test
    public void whenVariableHandleForArrayVariableIsCreated_ThenItIsInitializedProperly() throws NoSuchFieldException, IllegalAccessException {
        VarHandle arrayVarHandle = MethodHandles
          .arrayElementVarHandle(int[].class);

        assertEquals(2, arrayVarHandle.coordinateTypes().size());
        assertEquals(int[].class, arrayVarHandle.coordinateTypes().get(0));
    }

    @Test
    public void givenVarHandle_whenGetIsInvoked_ThenValueOfVariableIsReturned() throws NoSuchFieldException, IllegalAccessException {
        VarHandle PUBLIC_TEST_VARIABLE = MethodHandles
          .lookup()
          .in(VariableHandlesUnitTest.class)
          .findVarHandle(VariableHandlesUnitTest.class, "publicTestVariable", int.class);

        assertEquals(1, (int) PUBLIC_TEST_VARIABLE.get(this));
    }

    @Test
    public void givenVarHandle_whenSetIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle VARIABLE_TO_SET = MethodHandles
          .lookup()
          .in(VariableHandlesUnitTest.class)
          .findVarHandle(VariableHandlesUnitTest.class, "variableToSet", int.class);

        VARIABLE_TO_SET.set(this, 15);
        assertEquals(15, (int) VARIABLE_TO_SET.get(this));
    }

    @Test
    public void givenVarHandle_whenCompareAndSetIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle VARIABLE_TO_COMPARE_AND_SET = MethodHandles
          .lookup()
          .in(VariableHandlesUnitTest.class)
          .findVarHandle(VariableHandlesUnitTest.class, "variableToCompareAndSet", int.class);

        VARIABLE_TO_COMPARE_AND_SET.compareAndSet(this, 1, 100);
        assertEquals(100, (int) VARIABLE_TO_COMPARE_AND_SET.get(this));
    }

    @Test
    public void givenVarHandle_whenGetAndAddIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle VARIABLE_TO_GET_AND_ADD = MethodHandles
          .lookup()
          .in(VariableHandlesUnitTest.class)
          .findVarHandle(VariableHandlesUnitTest.class, "variableToGetAndAdd", int.class);

        int before = (int) VARIABLE_TO_GET_AND_ADD.getAndAdd(this, 200);

        assertEquals(0, before);
        assertEquals(200, (int) VARIABLE_TO_GET_AND_ADD.get(this));
    }

    @Test
    public void givenVarHandle_whenGetAndBitwiseOrIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle VARIABLE_TO_BITWISE_OR = MethodHandles
          .lookup()
          .in(VariableHandlesUnitTest.class)
          .findVarHandle(VariableHandlesUnitTest.class, "variableToBitwiseOr", byte.class);
        byte before = (byte) VARIABLE_TO_BITWISE_OR.getAndBitwiseOr(this, (byte) 127);

        assertEquals(0, before);
        assertEquals(127, (byte) VARIABLE_TO_BITWISE_OR.get(this));
    }
}
