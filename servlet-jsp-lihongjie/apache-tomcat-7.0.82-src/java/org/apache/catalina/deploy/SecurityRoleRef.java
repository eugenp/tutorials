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

import java.io.Serializable;

/**
 * <p>Representation of a security role reference for a web application, as
 * represented in a <code>&lt;security-role-ref&gt;</code> element
 * in the deployment descriptor.</p>
 *
 * @author Mark Thomas
 * @since Tomcat 5.5
 */
public class SecurityRoleRef implements Serializable {

    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------------- Properties


    /**
     * The (required) role name.
     */
    private String name = null;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * The optional role link.
     */
    private String link = null;

    public String getLink() {
        return (this.link);
    }

    public void setLink(String link) {
        this.link = link;
    }



    // --------------------------------------------------------- Public Methods


    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("SecurityRoleRef[");
        sb.append("name=");
        sb.append(name);
        if (link != null) {
            sb.append(", link=");
            sb.append(link);
        }
        sb.append("]");
        return (sb.toString());

    }


}
