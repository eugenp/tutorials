/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.http.fileupload.FileUtils;

public class TestWebappClassLoaderWeaving extends TomcatBaseTest {

    private static final String PACKAGE_PREFIX = "org/apache/catalina/loader";

    private static String WEBAPP_DOC_BASE;

    @BeforeClass
    public static void setUpClass() throws Exception {

        String webappDocBase = "test/tmpTestWebappClassLoaderWeaving";
        File webappDocBaseFile = new File(webappDocBase);
        WEBAPP_DOC_BASE = webappDocBaseFile.getCanonicalPath();
        File classes = new File(webappDocBaseFile, "/WEB-INF/classes/" + PACKAGE_PREFIX);
        classes.mkdirs();

        copyResource(PACKAGE_PREFIX + "/TesterNeverWeavedClass.class",
                new File(classes, "TesterNeverWeavedClass.class"));
        copyResource(PACKAGE_PREFIX + "/TesterUnweavedClass.class",
                new File(classes, "TesterUnweavedClass.class"));

    }

    @AfterClass
    public static void tearDownClass() throws Exception {

        FileUtils.deleteDirectory(new File(WEBAPP_DOC_BASE));

    }

    private Tomcat tomcat;
    private Context context;
    private WebappClassLoader loader;

    @Before
    @Override
    public void setUp() throws Exception {

        super.setUp();

        this.tomcat = getTomcatInstance();
        this.context = this.tomcat.addContext("/weaving", WEBAPP_DOC_BASE);
        this.tomcat.start();

        ClassLoader loader = this.context.getLoader().getClassLoader();
        assertNotNull("The class loader should not be null.", loader);
        assertSame("The class loader is not correct.", WebappClassLoader.class, loader.getClass());

        this.loader = (WebappClassLoader) loader;

    }

    @After
    @Override
    public void tearDown() throws Exception {

        try {
            this.loader = null;

            this.context.stop();
            this.tomcat.getHost().removeChild(this.context);
            this.context = null;
        } finally {
            super.tearDown();
        }

    }

    @Test
    public void testNoWeaving() throws Exception {

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Unweaved World!", result);

    }

    @Test
    public void testAddingNullTransformerThrowsException() throws Exception {

        try {
            this.loader.addTransformer(null);
            fail("Expected exception IllegalArgumentException, got no exception.");
        } catch (IllegalArgumentException ignore) {
            // good
        }

        // class loading should still work, no weaving
        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Unweaved World!", result);

    }

    @Test
    public void testAddedTransformerInstrumentsClass1() throws Exception {

        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_1));

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Weaver #1!", result);

    }

    @Test
    public void testAddedTransformerInstrumentsClass2() throws Exception {

        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_2));

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Weaver #2!", result);

    }

    @Test
    public void testTransformersExecuteInOrderAdded1() throws Exception {

        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_1));
        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_2));

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Weaver #2!", result);

    }

    @Test
    public void testTransformersExecuteInOrderAdded2() throws Exception {

        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_2));
        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_1));

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Weaver #1!", result);

    }

    @Test
    public void testRemovedTransformerNoLongerInstruments1() throws Exception {

        ReplacementTransformer removed = new ReplacementTransformer(WEAVED_REPLACEMENT_1);
        this.loader.addTransformer(removed);
        this.loader.removeTransformer(removed);

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Unweaved World!", result);

    }

    @Test
    public void testRemovedTransformerNoLongerInstruments2() throws Exception {

        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_1));

        ReplacementTransformer removed = new ReplacementTransformer(WEAVED_REPLACEMENT_2);
        this.loader.addTransformer(removed);
        this.loader.removeTransformer(removed);

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Weaver #1!", result);

    }

    @Test
    public void testRemovedTransformerNoLongerInstruments3() throws Exception {

        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_2));

        ReplacementTransformer removed = new ReplacementTransformer(WEAVED_REPLACEMENT_1);
        this.loader.addTransformer(removed);
        this.loader.removeTransformer(removed);

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Weaver #2!", result);

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCopiedClassLoaderExcludesResourcesAndTransformers() throws Exception {

        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_1));
        this.loader.addTransformer(new ReplacementTransformer(WEAVED_REPLACEMENT_2));

        String result = invokeDoMethodOnClass(this.loader, "TesterNeverWeavedClass");
        assertEquals("The first result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(this.loader, "TesterUnweavedClass");
        assertEquals("The second result is not correct.", "Hello, Weaver #2!", result);

        WebappClassLoader copiedLoader = this.loader.copyWithoutTransformers();

        result = invokeDoMethodOnClass(copiedLoader, "TesterNeverWeavedClass");
        assertEquals("The third result is not correct.", "This will never be weaved.", result);

        result = invokeDoMethodOnClass(copiedLoader, "TesterUnweavedClass");
        assertEquals("The fourth result is not correct.", "Hello, Unweaved World!", result);

        assertEquals("getAntiJARLocking did not match.",
                Boolean.valueOf(this.loader.getAntiJARLocking()),
                Boolean.valueOf(copiedLoader.getAntiJARLocking()));
        assertEquals("getClearReferencesHttpClientKeepAliveThread did not match.",
                Boolean.valueOf(this.loader.getClearReferencesHttpClientKeepAliveThread()),
                Boolean.valueOf(copiedLoader.getClearReferencesHttpClientKeepAliveThread()));
        assertEquals("getClearReferencesLogFactoryRelease did not match.",
                Boolean.valueOf(this.loader.getClearReferencesLogFactoryRelease()),
                Boolean.valueOf(copiedLoader.getClearReferencesLogFactoryRelease()));
        assertEquals("getClearReferencesStatic did not match.",
                Boolean.valueOf(this.loader.getClearReferencesStatic()),
                Boolean.valueOf(copiedLoader.getClearReferencesStatic()));
        assertEquals("getClearReferencesStopThreads did not match.",
                Boolean.valueOf(this.loader.getClearReferencesStopThreads()),
                Boolean.valueOf(copiedLoader.getClearReferencesStopThreads()));
        assertEquals("getClearReferencesStopTimerThreads did not match.",
                Boolean.valueOf(this.loader.getClearReferencesStopTimerThreads()),
                Boolean.valueOf(copiedLoader.getClearReferencesStopTimerThreads()));
        assertEquals("getContextName did not match.", this.loader.getContextName(),
                copiedLoader.getContextName());
        assertEquals("getDelegate did not match.",
                Boolean.valueOf(this.loader.getDelegate()),
                Boolean.valueOf(copiedLoader.getDelegate()));
        assertEquals("getJarPath did not match.", this.loader.getJarPath(),
                copiedLoader.getJarPath());
        assertEquals("getURLs did not match.", this.loader.getURLs().length,
                copiedLoader.getURLs().length);
        assertSame("getParent did not match.", this.loader.getParent(), copiedLoader.getParent());

    }

    private static void copyResource(String name, File file) throws Exception {

        InputStream is = TestWebappClassLoaderWeaving.class.getClassLoader()
                .getResourceAsStream(name);
        if (is == null) {
            throw new IOException("Resource " + name + " not found on classpath.");
        }

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            for (int b = is.read(); b >= 0; b = is.read()) {
                os.write(b);
            }
        } finally {
            try {
                is.close();
            } catch (IOException e1) {
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

    }

    private static String invokeDoMethodOnClass(WebappClassLoaderBase loader, String className)
            throws Exception {

        Class<?> c = loader.findClass("org.apache.catalina.loader." + className);
        assertNotNull("The loaded class should not be null.", c);

        Method m = c.getMethod("doMethod");

        Object o = c.newInstance();
        return (String) m.invoke(o);

    }

    private static class ReplacementTransformer implements ClassFileTransformer {

        private static final String CLASS_TO_WEAVE = PACKAGE_PREFIX + "/TesterUnweavedClass";

        private final byte[] replacement;

        ReplacementTransformer(byte[] replacement) {
            this.replacement = replacement;
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> x,
                                ProtectionDomain y, byte[] b) {

            if (CLASS_TO_WEAVE.equals(className)) {
                return this.replacement;
            }

            return null;

        }

    }

    /**
     * Compiled version of org.apache.catalina.loader.TesterUnweavedClass, except that
     * the doMethod method returns "Hello, Weaver #1!". Compiled with Oracle Java 1.6.0_51.
     */
    private static final byte[] WEAVED_REPLACEMENT_1 = new byte[] {
            -54, -2, -70, -66, 0, 0, 0, 50, 0, 17, 10, 0, 4, 0, 13, 8, 0, 14, 7, 0, 15, 7, 0, 16, 1,
            0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 1, 0,
            15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 8, 100,
            111, 77, 101, 116, 104, 111, 100, 1, 0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97,
            110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70,
            105, 108, 101, 1, 0, 24, 84, 101, 115, 116, 101, 114, 85, 110, 119, 101, 97, 118, 101,
            100, 67, 108, 97, 115, 115, 46, 106, 97, 118, 97, 12, 0, 5, 0, 6, 1, 0, 17, 72, 101,
            108, 108, 111, 44, 32, 87, 101, 97, 118, 101, 114, 32, 35, 49, 33, 1, 0, 46, 111, 114,
            103, 47, 97, 112, 97, 99, 104, 101, 47, 99, 97, 116, 97, 108, 105, 110, 97, 47, 108,
            111, 97, 100, 101, 114, 47, 84, 101, 115, 116, 101, 114, 85, 110, 119, 101, 97, 118,
            101, 100, 67, 108, 97, 115, 115, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47,
            79, 98, 106, 101, 99, 116, 0, 33, 0, 3, 0, 4, 0, 0, 0, 0, 0, 2, 0, 1, 0, 5, 0, 6, 0, 1,
            0, 7, 0, 0, 0, 29, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 1, 0, 8, 0, 0,
            0, 6, 0, 1, 0, 0, 0, 19, 0, 1, 0, 9, 0, 10, 0, 1, 0, 7, 0, 0, 0, 27, 0, 1, 0, 1, 0, 0,
            0, 3, 18, 2, -80, 0, 0, 0, 1, 0, 8, 0, 0, 0, 6, 0, 1, 0, 0, 0, 22, 0, 1, 0, 11, 0, 0, 0,
            2, 0, 12
    };

    /**
     * Compiled version of org.apache.catalina.loader.TesterUnweavedClass, except that
     * the doMethod method returns "Hello, Weaver #2!". Compiled with Oracle Java 1.6.0_51.
     */
    private static final byte[] WEAVED_REPLACEMENT_2 = new byte[] {
            -54, -2, -70, -66, 0, 0, 0, 50, 0, 17, 10, 0, 4, 0, 13, 8, 0, 14, 7, 0, 15, 7, 0, 16, 1,
            0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 1, 0,
            15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 8, 100,
            111, 77, 101, 116, 104, 111, 100, 1, 0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97,
            110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70,
            105, 108, 101, 1, 0, 24, 84, 101, 115, 116, 101, 114, 85, 110, 119, 101, 97, 118, 101,
            100, 67, 108, 97, 115, 115, 46, 106, 97, 118, 97, 12, 0, 5, 0, 6, 1, 0, 17, 72, 101,
            108, 108, 111, 44, 32, 87, 101, 97, 118, 101, 114, 32, 35, 50, 33, 1, 0, 46, 111, 114,
            103, 47, 97, 112, 97, 99, 104, 101, 47, 99, 97, 116, 97, 108, 105, 110, 97, 47, 108,
            111, 97, 100, 101, 114, 47, 84, 101, 115, 116, 101, 114, 85, 110, 119, 101, 97, 118,
            101, 100, 67, 108, 97, 115, 115, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47,
            79, 98, 106, 101, 99, 116, 0, 33, 0, 3, 0, 4, 0, 0, 0, 0, 0, 2, 0, 1, 0, 5, 0, 6, 0, 1,
            0, 7, 0, 0, 0, 29, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 1, 0, 8, 0, 0,
            0, 6, 0, 1, 0, 0, 0, 19, 0, 1, 0, 9, 0, 10, 0, 1, 0, 7, 0, 0, 0, 27, 0, 1, 0, 1, 0, 0,
            0, 3, 18, 2, -80, 0, 0, 0, 1, 0, 8, 0, 0, 0, 6, 0, 1, 0, 0, 0, 22, 0, 1, 0, 11, 0, 0, 0,
            2, 0, 12
    };

    /*
     * The WEAVED_REPLACEMENT_1 and WEAVED_REPLACEMENT_2 field contents are generated using the
     * following code. To regenerate them, alter the TesterUnweavedClass code as desired, recompile,
     * and run this main method.
     */
    public static void main(String... arguments) throws Exception {
        InputStream input = TestWebappClassLoaderWeaving.class.getClassLoader()
                .getResourceAsStream("org/apache/catalina/loader/TesterUnweavedClass.class");

        StringBuilder builder = new StringBuilder();
        builder.append("            ");

        System.out.println("    private static final byte[] WEAVED_REPLACEMENT_1 = new byte[] {");
        try {
            for (int i = 0, b = input.read(); b >= 0; i++, b = input.read()) {
                String value = "" + ((byte)b);
                if (builder.length() + value.length() > 97) {
                    builder.append(",");
                    System.out.println(builder.toString());
                    builder = new StringBuilder();
                    builder.append("            ").append(value);
                } else {
                    if (i > 0) {
                        builder.append(", ");
                    }
                    builder.append(value);
                }
            }
            System.out.println(builder.toString());
        } finally {
            input.close();
        }
        System.out.println("    }");
    }
}
