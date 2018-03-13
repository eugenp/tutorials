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
package org.apache.catalina.deploy;


/**
 * Representation of a resource link for a web application, as
 * represented in a <code>&lt;ResourceLink&gt;</code> element in the
 * server configuration file.
 *
 * @author Remy Maucherat
 * @author Peter Rossbach (Peter Rossbach (pero@apache.org))
 */
public class ContextResourceLink extends ResourceBase {

    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------------- Properties

   /**
     * The global name of this resource.
     */
    private String global = null;
    /**
     * The factory to be used for creating the object
     */
    private String factory = null;

    public String getGlobal() {
        return (this.global);
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }
    // --------------------------------------------------------- Public Methods


    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("ContextResourceLink[");
        sb.append("name=");
        sb.append(getName());
        if (getType() != null) {
            sb.append(", type=");
            sb.append(getType());
        }
        if (getGlobal() != null) {
            sb.append(", global=");
            sb.append(getGlobal());
        }
        sb.append("]");
        return (sb.toString());
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((factory == null) ? 0 : factory.hashCode());
        result = prime * result + ((global == null) ? 0 : global.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ContextResourceLink other = (ContextResourceLink) obj;
        if (factory == null) {
            if (other.factory != null) {
                return false;
            }
        } else if (!factory.equals(other.factory)) {
            return false;
        }
        if (global == null) {
            if (other.global != null) {
                return false;
            }
        } else if (!global.equals(other.global)) {
            return false;
        }
        return true;
    }
}
