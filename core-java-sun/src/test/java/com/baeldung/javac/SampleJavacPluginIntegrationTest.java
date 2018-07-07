package com.baeldung.javac;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SampleJavacPluginIntegrationTest {

    private static final String CLASS_TEMPLATE =
            "package com.baeldung.javac;\n" +
            "\n" +
            "public class Test {\n" +
            "    public static %1$s service(@Positive %1$s i) {\n" +
            "        return i;\n" +
            "    }\n" +
            "}\n" +
            "";

    private TestCompiler compiler = new TestCompiler();
    private TestRunner   runner   = new TestRunner();

    @Test(expected = IllegalArgumentException.class)
    public void givenInt_whenNegative_thenThrowsException() throws Throwable {
        compileAndRun(double.class,-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInt_whenZero_thenThrowsException() throws Throwable {
        compileAndRun(int.class,0);
    }

    @Test
    public void givenInt_whenPositive_thenSuccess() throws Throwable {
        assertEquals(1, compileAndRun(int.class, 1));
    }

    private Object compileAndRun(Class<?> argumentType, Object argument) throws Throwable {
        String qualifiedClassName = "com.baeldung.javac.Test";
        byte[] byteCode = compiler.compile(qualifiedClassName, String.format(CLASS_TEMPLATE, argumentType.getName()));
        return runner.run(byteCode, qualifiedClassName, "service", new Class[] {argumentType}, argument);
    }
}
