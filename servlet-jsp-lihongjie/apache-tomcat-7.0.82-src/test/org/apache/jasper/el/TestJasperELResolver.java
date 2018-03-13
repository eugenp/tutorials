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
package org.apache.jasper.el;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.el.ELResolver;
import javax.servlet.jsp.el.ImplicitObjectELResolver;

import org.junit.Assert;
import org.junit.Test;

public class TestJasperELResolver {

    @Test
    public void testConstructorNone() throws Exception {
        doTestConstructor(0);
    }

    @Test
    public void testConstructorOne() throws Exception {
        doTestConstructor(1);
    }

    @Test
    public void testConstructorFive() throws Exception {
        doTestConstructor(5);
    }

    private void doTestConstructor(int count) throws Exception {

        List<ELResolver> list = new ArrayList<ELResolver>();
        for (int i = 0; i < count; i++) {
            list.add(new ImplicitObjectELResolver());
        }

        JasperELResolver resolver = new JasperELResolver(list);


        Assert.assertEquals(Integer.valueOf(count),
                getField("appResolversSize", resolver));
        Assert.assertEquals(7 + count,
                ((ELResolver[])getField("resolvers", resolver)).length);
        Assert.assertEquals(Integer.valueOf(7 + count),
                getField("size", resolver));
    }

    private static final Object getField(String name, Object target)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(target);
    }
}
