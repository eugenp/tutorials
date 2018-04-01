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
 * <p>Public interface defining a shorthand means of configuring a complete
 * set of related <code>Rule</code> definitions, possibly associated with
 * a particular namespace URI, in one operation.  To use an instance of a
 * class that implements this interface:</p>
 * <ul>
 * <li>Create a concrete implementation of this interface.</li>
 * <li>Optionally, you can configure a <code>RuleSet</code> to be relevant
 *     only for a particular namespace URI by configuring the value to be
 *     returned by <code>getNamespaceURI()</code>.</li>
 * <li>As you are configuring your Digester instance, call
 *     <code>digester.addRuleSet()</code> and pass the RuleSet instance.</li>
 * <li>Digester will call the <code>addRuleInstances()</code> method of
 *     your RuleSet to configure the necessary rules.</li>
 * </ul>
 */

public interface RuleSet {


    // ------------------------------------------------------------- Properties


    /**
     * Return the namespace URI that will be applied to all Rule instances
     * created from this RuleSet.
     */
    public String getNamespaceURI();


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
    public void addRuleInstances(Digester digester);


}
