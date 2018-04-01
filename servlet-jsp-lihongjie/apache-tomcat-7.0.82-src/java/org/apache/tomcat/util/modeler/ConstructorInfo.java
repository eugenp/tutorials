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

package org.apache.tomcat.util.modeler;


import javax.management.MBeanConstructorInfo;


/**
 * <p>Internal configuration information for a <code>Constructor</code>
 * descriptor.</p>
 *
 * @author Craig R. McClanahan
 */
public class ConstructorInfo extends OperationInfo {
    static final long serialVersionUID = -5735336213417238238L;
    // ------------------------------------------------------------- Properties

    public ConstructorInfo() {
    }

    // --------------------------------------------------------- Public Methods


    /**
     * Create and return a <code>ModelMBeanConstructorInfo</code> object that
     * corresponds to the attribute described by this instance.
     */
    public MBeanConstructorInfo createConstructorInfo() {
        // Return our cached information (if any)
        if (info == null) {
            info = new MBeanConstructorInfo(getName(), getDescription(), 
                    getMBeanParameterInfo());
        }
        return (MBeanConstructorInfo)info;
    }

}
