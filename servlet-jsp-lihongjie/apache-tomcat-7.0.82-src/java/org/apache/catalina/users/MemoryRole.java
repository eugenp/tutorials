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


package org.apache.catalina.users;


import org.apache.catalina.UserDatabase;


/**
 * <p>Concrete implementation of {@link org.apache.catalina.Role} for the
 * {@link MemoryUserDatabase} implementation of {@link UserDatabase}.</p>
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public class MemoryRole extends AbstractRole {


    // ----------------------------------------------------------- Constructors


    /**
     * Package-private constructor used by the factory method in
     * {@link MemoryUserDatabase}.
     *
     * @param database The {@link MemoryUserDatabase} that owns this role
     * @param rolename Role name of this role
     * @param description Description of this role
     */
    MemoryRole(MemoryUserDatabase database,
               String rolename, String description) {

        super();
        this.database = database;
        setRolename(rolename);
        setDescription(description);

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The {@link MemoryUserDatabase} that owns this role.
     */
    protected MemoryUserDatabase database = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the {@link UserDatabase} within which this role is defined.
     */
    @Override
    public UserDatabase getUserDatabase() {

        return (this.database);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * <p>Return a String representation of this role in XML format.</p>
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("<role rolename=\"");
        sb.append(rolename);
        sb.append("\"");
        if (description != null) {
            sb.append(" description=\"");
            sb.append(description);
            sb.append("\"");
        }
        sb.append("/>");
        return (sb.toString());

    }


}
