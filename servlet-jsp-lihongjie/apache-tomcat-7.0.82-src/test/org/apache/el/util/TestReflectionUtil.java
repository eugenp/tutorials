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
package org.apache.el.util;

import javax.el.MethodNotFoundException;

import org.junit.Test;

public class TestReflectionUtil {

    private static final Tester BASE = new Tester();

    /*
     * Expect failure as it is not possible to identify which method named
     * "testA()" is intended.
     */
    @Test(expected=MethodNotFoundException.class)
    public void testBug54370a() {
        ReflectionUtil.getMethod(BASE, "testA",
                new Class[] {null, String.class},
                new Object[] {null, ""});
    }

    /*
     * Expect failure as it is not possible to identify which method named
     * "testB()" is intended. Note: In EL null can always be coerced to a valid
     * value for a primative.
     */
    @Test(expected=MethodNotFoundException.class)
    public void testBug54370b() {
        ReflectionUtil.getMethod(BASE, "testB",
                new Class[] {null, String.class},
                new Object[] {null, ""});
    }

    @Test
    public void testBug54370c() {
        ReflectionUtil.getMethod(BASE, "testC",
                new Class[] {null},
                new Object[] {null});
    }

    @Test
    public void testBug54370d() {
        ReflectionUtil.getMethod(BASE, "testD",
                new Class[] {null},
                new Object[] {null});
    }
}
