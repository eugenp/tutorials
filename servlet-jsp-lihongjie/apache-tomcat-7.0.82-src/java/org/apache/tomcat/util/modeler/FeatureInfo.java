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


import java.io.Serializable;

import javax.management.MBeanFeatureInfo;


/**
 * <p>Convenience base class for <code>AttributeInfo</code>,
 * <code>ConstructorInfo</code>, and <code>OperationInfo</code> classes
 * that will be used to collect configuration information for the
 * <code>ModelMBean</code> beans exposed for management.</p>
 *
 * @author Craig R. McClanahan
 */
public class FeatureInfo implements Serializable {
    static final long serialVersionUID = -911529176124712296L;
    
    protected String description = null;
    protected String name = null;
    protected MBeanFeatureInfo info = null;
    
    // all have type except Constructor
    protected String type = null;

    
    // ------------------------------------------------------------- Properties

    /**
     * The human-readable description of this feature.
     */
    public String getDescription() {
        return (this.description);
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * The name of this feature, which must be unique among features in the
     * same collection.
     */
    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The fully qualified Java class name of this element.
     */
    public String getType() {
        return (this.type);
    }

    public void setType(String type) {
        this.type = type;
    }


}
