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


import javax.management.MBeanParameterInfo;


/**
 * <p>Internal configuration information for a <code>Parameter</code>
 * descriptor.</p>
 *
 * @author Craig R. McClanahan
 */
public class ParameterInfo extends FeatureInfo {
    static final long serialVersionUID = 2222796006787664020L;
    // ----------------------------------------------------------- Constructors


    /**
     * Standard zero-arguments constructor.
     */
    public ParameterInfo() {
        super();
    }

    /**
     * Create and return a <code>MBeanParameterInfo</code> object that
     * corresponds to the parameter described by this instance.
     */
    public MBeanParameterInfo createParameterInfo() {

        // Return our cached information (if any)
        if (info == null) {
            info = new MBeanParameterInfo
                (getName(), getType(), getDescription());
        }
        return (MBeanParameterInfo)info;
    }
}
