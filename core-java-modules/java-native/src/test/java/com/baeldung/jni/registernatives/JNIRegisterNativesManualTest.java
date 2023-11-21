package com.baeldung.jni.registernatives;

import com.baeldung.jni.RegisterNativesHelloWorldJNI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JNIRegisterNativesManualTest {
    @Before
    public void setup() {
        System.loadLibrary("native");
    }

    @Test
    public void whenRegisteredNativeHelloWorld_thenOutputIsAsExpected() {
        RegisterNativesHelloWorldJNI helloWorld = new RegisterNativesHelloWorldJNI();
        helloWorld.register();

        String helloFromNative = helloWorld.sayHello();

        assertNotNull(helloFromNative);
        assertTrue(helloFromNative.equals("Hello from registered native C++ !!"));
    }
}
