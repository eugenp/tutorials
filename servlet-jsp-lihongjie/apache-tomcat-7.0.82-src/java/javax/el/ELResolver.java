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

import java.util.Iterator;

/**
 * @author Jacob Hookom [jacob/hookom.net]
 *
 */
public abstract class ELResolver {

    public static final String RESOLVABLE_AT_DESIGN_TIME = "resolvableAtDesignTime";
    
    public static final String TYPE = "type";
    
    public abstract Object getValue(ELContext context, Object base, Object property) throws NullPointerException, PropertyNotFoundException, ELException;
    
    /**
     * @param context The EL context for this evaluation
     * @param base The base object on which the property is to be found
     * @param property The property whose type is to be returned
     * @return the type of the provided property
     * @throws NullPointerException
     *              If the supplied context is <code>null</code>
     * @throws PropertyNotFoundException
     *              If the base/property combination provided to the resolver is
     *              one that the resolver can handle but no match was found or a
     *              match was found but was not readable
     * @throws ELException
     *              Wraps any exception throw whilst resolving the property
     */
    public abstract Class<?> getType(ELContext context, Object base, Object property) throws NullPointerException, PropertyNotFoundException, ELException;
    
    /**
     * @param context  The EL context for this evaluation
     * @param base     The base object on which the property is to be found
     * @param property The property whose value is to be set
     * @param value    The value to set the property to
     * @throws NullPointerException
     *              If the supplied context is <code>null</code>
     * @throws PropertyNotFoundException
     *              If the base/property combination provided to the resolver is
     *              one that the resolver can handle but no match was found
     * @throws PropertyNotWritableException
     *              If the base/property combination provided to the resolver is
     *              one that the resolver can handle but the property was not
     *              writable
     * @throws ELException
     *              Wraps any exception throw whilst resolving the property
     */
    public abstract void setValue(ELContext context, Object base, Object property, Object value) throws NullPointerException, PropertyNotFoundException, PropertyNotWritableException, ELException;

    /**
     * @param context The EL context for this evaluation
     * @param base The base object on which the property is to be found
     * @param property The property to be checked for read only status
     * @return <code>true</code> if the identified property is read only,
     *         otherwise <code>false</code>
     * @throws NullPointerException
     *              If the supplied context is <code>null</code>
     * @throws PropertyNotFoundException
     *              If the base/property combination provided to the resolver is
     *              one that the resolver can handle but no match was found
     * @throws ELException
     *              Wraps any exception throw whilst resolving the property
     */
    public abstract boolean isReadOnly(ELContext context, Object base, Object property) throws NullPointerException, PropertyNotFoundException, ELException;
    
    public abstract Iterator<java.beans.FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base);
    
    public abstract Class<?> getCommonPropertyType(ELContext context, Object base);
    
    /**
     * Invokes a method on the the given object. This default implementation
     * always returns <code>null</code>.
     *
     * @param context    The EL context for this evaluation
     * @param base       The base object on which the method is to be found
     * @param method     The method to invoke
     * @param paramTypes The types of the parameters of the method to invoke
     * @param params     The parameters with which to invoke the method
     *
     * @return Always <code>null</code>
     *
     * @since EL 2.2
     */
    public Object invoke(ELContext context, Object base, Object method,
            Class<?>[] paramTypes, Object[] params) {
        return null;
    }

}
