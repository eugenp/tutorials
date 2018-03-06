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

import java.util.List;

import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.ResourceBundleELResolver;
import javax.servlet.jsp.el.ImplicitObjectELResolver;
import javax.servlet.jsp.el.ScopedAttributeELResolver;

/**
 * Jasper-specific CompositeELResolver that optimizes certain functions to avoid
 * unnecessary resolver calls.
 */
public class JasperELResolver extends CompositeELResolver {

    private int size;
    private ELResolver[] resolvers;
    private final int appResolversSize;

    public JasperELResolver(List<ELResolver> appResolvers) {
        appResolversSize = appResolvers.size();
        resolvers = new ELResolver[appResolversSize + 7];
        size = 0;

        add(new ImplicitObjectELResolver());
        for (ELResolver appResolver : appResolvers) {
            add(appResolver);
        }
        add(new MapELResolver());
        add(new ResourceBundleELResolver());
        add(new ListELResolver());
        add(new ArrayELResolver());
        add(new BeanELResolver());
        add(new ScopedAttributeELResolver());
    }

    @Override
    public synchronized void add(ELResolver elResolver) {
        super.add(elResolver);

        if (resolvers.length > size) {
            resolvers[size] = elResolver;
        } else {
            ELResolver[] nr = new ELResolver[size + 1];
            System.arraycopy(resolvers, 0, nr, 0, size);
            nr[size] = elResolver;

            resolvers = nr;
        }
        size ++;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property)
        throws NullPointerException, PropertyNotFoundException, ELException {
        context.setPropertyResolved(false);

        int start;
        Object result = null;

        if (base == null) {
            // call implicit and app resolvers
            int index = 1 /* implicit */ + appResolversSize;
            for (int i = 0; i < index; i++) {
                result = resolvers[i].getValue(context, base, property);
                if (context.isPropertyResolved()) {
                    return result;
                }
            }
            // skip collection-based resolvers (map, resource, list, array, and
            // bean)
            start = index + 5;
        } else {
            // skip implicit resolver only
            start = 1;
        }

        for (int i = start; i < size; i++) {
            result = resolvers[i].getValue(context, base, property);
            if (context.isPropertyResolved()) {
                return result;
            }
        }

        return null;
    }

    @Override
    public Object invoke(ELContext context, Object base, Object method,
            Class<?>[] paramTypes, Object[] params) {
        String targetMethod = coerceToString(method);
        if (targetMethod.length() == 0) {
            throw new ELException(new NoSuchMethodException());
        }

        context.setPropertyResolved(false);

        Object result = null;

        // skip implicit and call app resolvers
        int index = 1 /* implicit */ + appResolversSize;
        for (int i = 1; i < index; i++) {
            result = resolvers[i].invoke(
                    context, base, targetMethod, paramTypes, params);
            if (context.isPropertyResolved()) {
                return result;
            }
        }

        // skip map, resource, list, and array resolvers
        index += 4;
        // call bean and the rest of resolvers
        for (int i = index; i < size; i++) {
            result = resolvers[i].invoke(
                    context, base, targetMethod, paramTypes, params);
            if (context.isPropertyResolved()) {
                return result;
            }
        }

        return null;
    }

    /**
     * Copied from {@link org.apache.el.lang.ELSupport#coerceToString(Object)}.
     */
    private static final String coerceToString(final Object obj) {
        if (obj == null) {
            return "";
        } else if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof Enum<?>) {
            return ((Enum<?>) obj).name();
        } else {
            return obj.toString();
        }
    }
}