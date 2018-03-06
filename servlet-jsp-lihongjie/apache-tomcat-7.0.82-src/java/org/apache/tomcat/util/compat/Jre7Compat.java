/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.compat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

class Jre7Compat extends JreCompat {

    private static final Method forLanguageTagMethod;


    static {
        Method m = null;
        try {
            m = Locale.class.getMethod("forLanguageTag", String.class);
        } catch (SecurityException e) {
            // Should never happen
        } catch (NoSuchMethodException e) {
            // Expected on Java < 7
        }
        forLanguageTagMethod = m;
    }


    static boolean isSupported() {
        return forLanguageTagMethod != null;
    }


    @Override
    public Locale forLanguageTag(String languageTag) {
        try {
            return (Locale) forLanguageTagMethod.invoke(null, languageTag);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }
}
