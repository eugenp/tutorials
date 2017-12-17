package com.baeldung.java9.varhandles;

import org.junit.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

import static org.assertj.core.api.Assertions.assertThat;

public class VariableHandlesTest {

    public int publicTestVariable = 1;
    private int privateTestVariable = 1;
    public int variableToSet = 1;
    public int variableToCompareAndSet = 1;
    public int variableToGetAndAdd = 0;
    public byte variableToBitwiseOr = 0;

    @Test
    public void whenVariableHandleForPublicVariableIsCreated_ThenItIsInitializedProperly() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles
          .lookup()
          .in(VariableHandlesTest.class)
          .findVarHandle(VariableHandlesTest.class, "publicTestVariable", int.class);

        assertThat(publicIntHandle.coordinateTypes().size() == 1);
        assertThat(publicIntHandle.coordinateTypes().get(0) == VariableHandles.class);

    }

    @Test
    public void whenVariableHandleForPrivateVariableIsCreated_ThenItIsInitializedProperly() throws NoSuchFieldException, IllegalAccessException {
        VarHandle privateIntHandle = MethodHandles
          .privateLookupIn(VariableHandlesTest.class, MethodHandles.lookup())
          .findVarHandle(VariableHandlesTest.class, "privateTestVariable", int.class);

        assertThat(privateIntHandle.coordinateTypes().size() == 1);
        assertThat(privateIntHandle.coordinateTypes().get(0) == VariableHandlesTest.class);

    }

    @Test
    public void whenVariableHandleForArrayVariableIsCreated_ThenItIsInitializedProperly() throws NoSuchFieldException, IllegalAccessException {
        VarHandle arrayVarHandle = MethodHandles
          .arrayElementVarHandle(int[].class);

        assertThat(arrayVarHandle.coordinateTypes().size() == 2);
        assertThat(arrayVarHandle.coordinateTypes().get(0) == int[].class);
    }

    @Test
    public void givenVarHandle_whenGetIsInvoked_ThenValueOfVariableIsReturned() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles
          .lookup()
          .in(VariableHandlesTest.class)
          .findVarHandle(VariableHandlesTest.class, "publicTestVariable", int.class);

        assertThat((int) publicIntHandle.get(this) == 1);
    }

    @Test
    public void givenVarHandle_whenSetIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles
          .lookup()
          .in(VariableHandlesTest.class)
          .findVarHandle(VariableHandlesTest.class, "variableToSet", int.class);
        publicIntHandle.set(this, 15);

        assertThat((int) publicIntHandle.get(this) == 15);
    }

    @Test
    public void givenVarHandle_whenCompareAndSetIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles
          .lookup()
          .in(VariableHandlesTest.class)
          .findVarHandle(VariableHandlesTest.class, "variableToCompareAndSet", int.class);
        publicIntHandle.compareAndSet(this, 1, 100);

        assertThat((int) publicIntHandle.get(this) == 100);
    }

    @Test
    public void givenVarHandle_whenGetAndAddIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles
          .lookup()
          .in(VariableHandlesTest.class)
          .findVarHandle(VariableHandlesTest.class, "variableToGetAndAdd", int.class);
        int before = (int) publicIntHandle.getAndAdd(this, 200);

        assertThat(before == 0);
        assertThat((int) publicIntHandle.get(this) == 200);
    }

    @Test
    public void givenVarHandle_whenGetAndBitwiseOrIsInvoked_ThenValueOfVariableIsChanged() throws NoSuchFieldException, IllegalAccessException {
        VarHandle publicIntHandle = MethodHandles
          .lookup()
          .in(VariableHandlesTest.class)
          .findVarHandle(VariableHandlesTest.class, "variableToBitwiseOr", byte.class);
        byte before = (byte) publicIntHandle.getAndBitwiseOr(this, (byte) 127);

        assertThat(before == 0);
        assertThat(variableToBitwiseOr == 127);
    }
}
