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


import java.util.ArrayList;
import java.util.Iterator;

import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;


/**
 * <p>Concrete implementation of {@link org.apache.catalina.Group} for the
 * {@link MemoryUserDatabase} implementation of {@link UserDatabase}.</p>
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public class MemoryGroup extends AbstractGroup {


    // ----------------------------------------------------------- Constructors


    /**
     * Package-private constructor used by the factory method in
     * {@link MemoryUserDatabase}.
     *
     * @param database The {@link MemoryUserDatabase} that owns this group
     * @param groupname Group name of this group
     * @param description Description of this group
     */
    MemoryGroup(MemoryUserDatabase database,
                String groupname, String description) {

        super();
        this.database = database;
        setGroupname(groupname);
        setDescription(description);

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The {@link MemoryUserDatabase} that owns this group.
     */
    protected MemoryUserDatabase database = null;


    /**
     * The set of {@link Role}s associated with this group.
     */
    protected ArrayList<Role> roles = new ArrayList<Role>();


    // ------------------------------------------------------------- Properties


    /**
     * Return the set of {@link Role}s assigned specifically to this group.
     */
    @Override
    public Iterator<Role> getRoles() {

        synchronized (roles) {
            return (roles.iterator());
        }

    }


    /**
     * Return the {@link UserDatabase} within which this Group is defined.
     */
    @Override
    public UserDatabase getUserDatabase() {

        return (this.database);

    }


    /**
     * Return the set of {@link org.apache.catalina.User}s that are members of this group.
     */
    @Override
    public Iterator<User> getUsers() {

        ArrayList<User> results = new ArrayList<User>();
        Iterator<User> users = database.getUsers();
        while (users.hasNext()) {
            User user = users.next();
            if (user.isInGroup(this)) {
                results.add(user);
            }
        }
        return (results.iterator());

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Add a new {@link Role} to those assigned specifically to this group.
     *
     * @param role The new role
     */
    @Override
    public void addRole(Role role) {

        synchronized (roles) {
            if (!roles.contains(role)) {
                roles.add(role);
            }
        }

    }


    /**
     * Is this group specifically assigned the specified {@link Role}?
     *
     * @param role The role to check
     */
    @Override
    public boolean isInRole(Role role) {

        synchronized (roles) {
            return (roles.contains(role));
        }

    }


    /**
     * Remove a {@link Role} from those assigned to this group.
     *
     * @param role The old role
     */
    @Override
    public void removeRole(Role role) {

        synchronized (roles) {
            roles.remove(role);
        }

    }


    /**
     * Remove all {@link Role}s from those assigned to this group.
     */
    @Override
    public void removeRoles() {

        synchronized (roles) {
            roles.clear();
        }

    }


    /**
     * <p>Return a String representation of this group in XML format.</p>
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("<group groupname=\"");
        sb.append(groupname);
        sb.append("\"");
        if (description != null) {
            sb.append(" description=\"");
            sb.append(description);
            sb.append("\"");
        }
        synchronized (roles) {
            if (roles.size() > 0) {
                sb.append(" roles=\"");
                int n = 0;
                Iterator<Role> values = roles.iterator();
                while (values.hasNext()) {
                    if (n > 0) {
                        sb.append(',');
                    }
                    n++;
                    sb.append((values.next()).getRolename());
                }
                sb.append("\"");
            }
        }
        sb.append("/>");
        return (sb.toString());

    }


}
