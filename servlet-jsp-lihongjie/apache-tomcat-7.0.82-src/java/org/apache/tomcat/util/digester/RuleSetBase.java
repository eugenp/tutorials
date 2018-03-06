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


package org.apache.tomcat.util.digester;


/**
 * <p>Convenience base class that implements the {@link RuleSet} interface.
 * Concrete implementations should list all of their actual rule creation
 * logic in the <code>addRuleSet()</code> implementation.</p>
 */

public abstract class RuleSetBase implements RuleSet {


    // ----------------------------------------------------- Instance Variables


    /**
     * The namespace URI that all Rule instances created by this RuleSet
     * will be associated with.
     */
    protected String namespaceURI = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the namespace URI that will be applied to all Rule instances
     * created from this RuleSet.
     */
    @Override
    public String getNamespaceURI() {

        return (this.namespaceURI);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Add the set of Rule instances defined in this RuleSet to the
     * specified <code>Digester</code> instance, associating them with
     * our namespace URI (if any).  This method should only be called
     * by a Digester instance.
     *
     * @param digester Digester instance to which the new Rule instances
     *  should be added.
     */
    @Override
    public abstract void addRuleInstances(Digester digester);


}
