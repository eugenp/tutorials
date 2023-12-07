package com.baeldung.jni;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class JNINativeManualTest {
    
    @Before
    public void setup() {
        System.loadLibrary("native");
    }
    
    @Test
    public void whenNativeHelloWorld_thenOutputIsAsExpected() {
        HelloWorldJNI helloWorld = new HelloWorldJNI();
        String helloFromNative = helloWorld.sayHello();
        assertTrue(!helloFromNative.isEmpty() && helloFromNative.equals("Hello from C++ !!"));
    }
    
    @Test
    public void whenSumNative_thenResultIsArithmeticallyCorrect() {
        ExampleParametersJNI parametersNativeMethods = new ExampleParametersJNI();
        assertTrue(parametersNativeMethods.sumIntegers(200, 400) == 600L);
    }
    
    @Test
    public void whenSayingNativeHelloToMe_thenResultIsAsExpected() {
        ExampleParametersJNI parametersNativeMethods = new ExampleParametersJNI();
        assertEquals(parametersNativeMethods.sayHelloToMe("Orange", true), "Ms. Orange");
    }

    @Test
    public void whenCreatingNativeObject_thenObjectIsNotNullAndHasCorrectData() {
        String name = "Iker Casillas";
        double balance = 2378.78;
        ExampleObjectsJNI objectsNativeMethods = new ExampleObjectsJNI();
        UserData userFromNative = objectsNativeMethods.createUser(name, balance);
        assertNotNull(userFromNative);
        assertEquals(userFromNative.name, name);
        assertTrue(userFromNative.balance == balance);
    }
    
    @Test
    public void whenNativeCallingObjectMethod_thenResultIsAsExpected() {
        String name = "Sergio Ramos";
        double balance = 666.77;
        ExampleObjectsJNI objectsNativeMethods = new ExampleObjectsJNI();
        UserData userData = new UserData();
        userData.name = name;
        userData.balance = balance;
        assertEquals(objectsNativeMethods.printUserData(userData), "[name]=" + name + ", [balance]=" + balance);
    }
    
}
