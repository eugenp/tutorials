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

/**
 *
 */
public abstract class MethodExpression extends Expression {

    private static final long serialVersionUID = 8163925562047324656L;

    /**
     * @param context The EL context for this evaluation
     *
     * @return Information about the method that this expression resolves to
     *
     * @throws NullPointerException
     *              If the supplied context is <code>null</code>
     * @throws PropertyNotFoundException
     *              If a property/variable resolution failed because no match
     *              was found or a match was found but was not readable
     * @throws MethodNotFoundException
     *              If no matching method can be found
     * @throws ELException
     *              Wraps any exception throw whilst resolving the property
     */
    public abstract MethodInfo getMethodInfo(ELContext context) throws NullPointerException, PropertyNotFoundException, MethodNotFoundException, ELException;
    
    /**
     * @param context The EL context for this evaluation
     * @param params  The parameters with which to invoke this method expression
     *
     * @return The result of invoking this method expression
     *
     * @throws NullPointerException
     *              If the supplied context is <code>null</code>
     * @throws PropertyNotFoundException
     *              If a property/variable resolution failed because no match
     *              was found or a match was found but was not readable
     * @throws MethodNotFoundException
     *              If no matching method can be found
     * @throws ELException
     *              Wraps any exception throw whilst resolving the property or
     *              coercion of the result to the expected return type fails
     */
    public abstract Object invoke(ELContext context, Object[] params) throws NullPointerException, PropertyNotFoundException, MethodNotFoundException, ELException;
    
    /**
     * @since EL 2.2
     * 
     * Note: The spelling mistake is deliberate.
     * isParmetersProvided()  - Specification definition
     * isParametersProvided() - Corrected spelling
     */
    public boolean isParmetersProvided() {
        // Expected to be over-ridden by implementation
        return false;
    }
}
