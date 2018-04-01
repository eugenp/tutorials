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

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompositeELResolver extends ELResolver {

    private static final Class<?> SCOPED_ATTRIBUTE_EL_RESOLVER;
    static {
        Class<?> clazz = null;
        try {
            clazz =
                Class.forName("javax.servlet.jsp.el.ScopedAttributeELResolver");
        } catch (ClassNotFoundException e) {
            // Ignore. This is expected if using the EL stand-alone 
        }
        SCOPED_ATTRIBUTE_EL_RESOLVER = clazz;
    }

    private int size;

    private ELResolver[] resolvers;

    public CompositeELResolver() {
        this.size = 0;
        this.resolvers = new ELResolver[8];
    }

    public void add(ELResolver elResolver) {
        if (elResolver == null) {
            throw new NullPointerException();
        }

        if (this.size >= this.resolvers.length) {
            ELResolver[] nr = new ELResolver[this.size * 2];
            System.arraycopy(this.resolvers, 0, nr, 0, this.size);
            this.resolvers = nr;
        }
        this.resolvers[this.size++] = elResolver;
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property)
            throws NullPointerException, PropertyNotFoundException, ELException {
        context.setPropertyResolved(false);
        int sz = this.size;
        Object result = null;
        for (int i = 0; i < sz; i++) {
            result = this.resolvers[i].getValue(context, base, property);
            if (context.isPropertyResolved()) {
                return result;
            }
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property,
            Object value) throws NullPointerException,
            PropertyNotFoundException, PropertyNotWritableException,
            ELException {
        context.setPropertyResolved(false);
        int sz = this.size;
        for (int i = 0; i < sz; i++) {
            this.resolvers[i].setValue(context, base, property, value);
            if (context.isPropertyResolved()) {
                return;
            }
        }
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property)
            throws NullPointerException, PropertyNotFoundException, ELException {
        context.setPropertyResolved(false);
        int sz = this.size;
        boolean readOnly = false;
        for (int i = 0; i < sz; i++) {
            readOnly = this.resolvers[i].isReadOnly(context, base, property);
            if (context.isPropertyResolved()) {
                return readOnly;
            }
        }
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return new FeatureIterator(context, base, this.resolvers, this.size);
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        int sz = this.size;
        Class<?> commonType = null, type = null;
        for (int i = 0; i < sz; i++) {
            type = this.resolvers[i].getCommonPropertyType(context, base);
            if (type != null
                    && (commonType == null || commonType.isAssignableFrom(type))) {
                commonType = type;
            }
        }
        return commonType;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property)
            throws NullPointerException, PropertyNotFoundException, ELException {
        context.setPropertyResolved(false);
        int sz = this.size;
        Class<?> type;
        for (int i = 0; i < sz; i++) {
            type = this.resolvers[i].getType(context, base, property);
            if (context.isPropertyResolved()) {
                if (SCOPED_ATTRIBUTE_EL_RESOLVER != null &&
                        SCOPED_ATTRIBUTE_EL_RESOLVER.isAssignableFrom(
                                resolvers[i].getClass())) {
                    // Special case since
                    // javax.servlet.jsp.el.ScopedAttributeELResolver will
                    // always return Object.class for type
                    Object value =
                        resolvers[i].getValue(context, base, property);
                    if (value != null) {
                        return value.getClass();
                    }
                }
                return type;
            }
        }
        return null;
    }

    /**
     * @since EL 2.2
     */
    @Override
    public Object invoke(ELContext context, Object base, Object method,
            Class<?>[] paramTypes, Object[] params) {
        context.setPropertyResolved(false);
        int sz = this.size;
        Object obj;
        for (int i = 0; i < sz; i++) {
            obj = this.resolvers[i].invoke(context, base, method, paramTypes,
                    params);
            if (context.isPropertyResolved()) {
                return obj;
            }
        }
        return null;
    }

    private static final class FeatureIterator implements Iterator<FeatureDescriptor> {

        private final ELContext context;

        private final Object base;

        private final ELResolver[] resolvers;

        private final int size;

        private Iterator<FeatureDescriptor> itr;

        private int idx;

        private FeatureDescriptor next;

        public FeatureIterator(ELContext context, Object base,
                ELResolver[] resolvers, int size) {
            this.context = context;
            this.base = base;
            this.resolvers = resolvers;
            this.size = size;

            this.idx = 0;
            this.guaranteeIterator();
        }
        
        private void guaranteeIterator() {
            while (this.itr == null && this.idx < this.size) {
                this.itr = this.resolvers[this.idx].getFeatureDescriptors(
                        this.context, this.base);
                this.idx++;
            }
        }

        @Override
        public boolean hasNext() {          
            if (this.next != null)
                return true;
            if (this.itr != null){
                while (this.next == null && itr.hasNext()) {
                    this.next = itr.next();
                }
            } else {
                return false;
            }
            if (this.next == null) {
                this.itr = null;
                this.guaranteeIterator();
            }
            return hasNext();
        }

        @Override
        public FeatureDescriptor next() {
            if (!hasNext())
                throw new NoSuchElementException();
            FeatureDescriptor result = this.next;
            this.next = null;
            return result;

        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
