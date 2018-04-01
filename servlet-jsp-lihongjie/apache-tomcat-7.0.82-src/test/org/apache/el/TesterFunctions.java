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

package org.apache.el;

import java.lang.reflect.Method;

import javax.el.FunctionMapper;

public class TesterFunctions {
    public static String trim(String input) {
        return input.trim();
    }

    public static String concat(String... inputs) {
        if (inputs == null || inputs.length == 0) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (String input : inputs) {
            result.append(input);
        }
        return result.toString();
    }

    public static String concat2(String prefix, String... inputs) {
        StringBuilder result = new StringBuilder(prefix);
        for (String input : inputs) {
            result.append(input);
        }
        return result.toString();
    }

    public static String[] toArray(String a, String b) {
        return new String[] { a, b };
    }


    public static class Inner$Class {

        public static final String RETVAL = "Return from bug49555";
        public static String bug49555() {
            return RETVAL;
        }
    }


    public static class FMapper extends FunctionMapper {

        @Override
        public Method resolveFunction(String prefix, String localName) {
            if ("trim".equals(localName)) {
                Method m;
                try {
                    m = TesterFunctions.class.getMethod("trim", String.class);
                    return m;
                } catch (SecurityException e) {
                    // Ignore
                } catch (NoSuchMethodException e) {
                    // Ignore
                }
            } else if ("concat".equals(localName)) {
                Method m;
                try {
                    m = TesterFunctions.class.getMethod("concat", String[].class);
                    return m;
                } catch (SecurityException e) {
                    // Ignore
                } catch (NoSuchMethodException e) {
                    // Ignore
                }
            } else if ("concat2".equals(localName)) {
                Method m;
                try {
                    m = TesterFunctions.class.getMethod("concat2", String.class, String[].class);
                    return m;
                } catch (SecurityException e) {
                    // Ignore
                } catch (NoSuchMethodException e) {
                    // Ignore
                }
            } else if ("toArray".equals(localName)) {
                Method m;
                try {
                    m = TesterFunctions.class.getMethod("toArray", String.class, String.class);
                    return m;
                } catch (SecurityException e) {
                    // Ignore
                } catch (NoSuchMethodException e) {
                    // Ignore
                }
            }
            return null;
        }
    }
}
