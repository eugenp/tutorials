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
package javax.el;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestBeanELResolverVarargsInvocation {
    public static class Foo {

        public String joinDelimited(String delim, String... strings) {
            StringBuilder result = new StringBuilder();
            if (strings != null) {
                for (String s : strings) {
                    if (delim != null && result.length() > 0) {
                        result.append(delim);
                    }
                    result.append(s);
                }
            }
            return result.toString();
        }

        public String join(String... strings) {
            return joinDelimited(null, strings);
        }
    }

    private Foo foo;
    private ELContext elContext;
    private BeanELResolver beanELResolver;

    @Before
    public void setup() {
        foo = new Foo();
        beanELResolver = new BeanELResolver();
        elContext = new ELContext() {
            private VariableMapper variableMapper = new VariableMapper() {
                private Map<String, ValueExpression> vars =
                    new HashMap<String, ValueExpression>();

                @Override
                public ValueExpression setVariable(String arg0,
                        ValueExpression arg1) {
                    return vars.put(arg0, arg1);
                }

                @Override
                public ValueExpression resolveVariable(String arg0) {
                    return vars.get(arg0);
                }
            };
            private FunctionMapper functionMapper = new FunctionMapper() {

                @Override
                public Method resolveFunction(String arg0, String arg1) {
                    return null;
                }
            };

            @Override
            public VariableMapper getVariableMapper() {
                return variableMapper;
            }

            @Override
            public FunctionMapper getFunctionMapper() {
                return functionMapper;
            }

            @Override
            public ELResolver getELResolver() {
                return beanELResolver;
            }
        };
    }

    /**
     * Tests varargs that come after an opening argument.
     */
    @Test
    public void testJoinDelimited() {
        Assert.assertEquals(foo.joinDelimited("-", "foo", "bar", "baz"),
            beanELResolver.invoke(elContext, foo, "joinDelimited", null,
                    new Object[] { "-", "foo", "bar", "baz" }));
    }

    /**
     * Tests varargs that constitute a method's only parameters, as well as
     * bogus results due to improper matching of ANY vararg method, and
     * depending on the order in which reflected methods are encountered.
     */
    @Test
    public void testJoin() {
        Assert.assertEquals(foo.join("foo", "bar", "baz"),
            beanELResolver.invoke(elContext, foo, "join", null,
                    new Object[] { "foo", "bar", "baz" }));
    }

}