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

import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.servlet.jsp.el.VariableResolver;

@Deprecated
public final class ELResolverImpl extends ELResolver {

    private final VariableResolver variableResolver;
    private final ELResolver elResolver;

    public ELResolverImpl(VariableResolver variableResolver) {
        this.variableResolver = variableResolver;
        this.elResolver = ELContextImpl.getDefaultResolver();
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property)
            throws NullPointerException, PropertyNotFoundException, ELException {
        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            context.setPropertyResolved(true);
            if (property != null) {
                try {
                    return this.variableResolver.resolveVariable(property
                            .toString());
                } catch (javax.servlet.jsp.el.ELException e) {
                    throw new ELException(e.getMessage(), e.getCause());
                }
            }
        }

        if (!context.isPropertyResolved()) {
            return elResolver.getValue(context, base, property);
        }
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property)
            throws NullPointerException, PropertyNotFoundException, ELException {
        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            context.setPropertyResolved(true);
            if (property != null) {
                try {
                    Object obj = this.variableResolver.resolveVariable(property
                            .toString());
                    return (obj != null) ? obj.getClass() : null;
                } catch (javax.servlet.jsp.el.ELException e) {
                    throw new ELException(e.getMessage(), e.getCause());
                }
            }
        }

        if (!context.isPropertyResolved()) {
            return elResolver.getType(context, base, property);
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property,
            Object value) throws NullPointerException,
            PropertyNotFoundException, PropertyNotWritableException,
            ELException {
        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            context.setPropertyResolved(true);
            throw new PropertyNotWritableException(
                    "Legacy VariableResolver wrapped, not writable");
        }

        if (!context.isPropertyResolved()) {
            elResolver.setValue(context, base, property, value);
        }
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property)
            throws NullPointerException, PropertyNotFoundException, ELException {
        if (context == null) {
            throw new NullPointerException();
        }

        if (base == null) {
            context.setPropertyResolved(true);
            return true;
        }

        return elResolver.isReadOnly(context, base, property);
    }

    @Override
    public Iterator<java.beans.FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return elResolver.getFeatureDescriptors(context, base);
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base == null) {
            return String.class;
        }
        return elResolver.getCommonPropertyType(context, base);
    }

    /**
     * @deprecated  Use {@link ELContextImpl#getDefaultResolver()} instead. This
     *              method will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public static ELResolver getDefaultResolver() {
        return ELContextImpl.getDefaultResolver();
    }
}
