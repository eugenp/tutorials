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


import java.util.Iterator;

import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;


/**
 * <p>Convenience base class for {@link Group} implementations.</p>
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public abstract class AbstractGroup implements Group {


    // ----------------------------------------------------- Instance Variables


    /**
     * The description of this group.
     */
    protected String description = null;


    /**
     * The group name of this group.
     */
    protected String groupname = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the description of this group.
     */
    @Override
    public String getDescription() {

        return (this.description);

    }


    /**
     * Set the description of this group.
     *
     * @param description The new description
     */
    @Override
    public void setDescription(String description) {

        this.description = description;

    }


    /**
     * Return the group name of this group, which must be unique
     * within the scope of a {@link UserDatabase}.
     */
    @Override
    public String getGroupname() {

        return (this.groupname);

    }


    /**
     * Set the group name of this group, which must be unique
     * within the scope of a {@link UserDatabase}.
     *
     * @param groupname The new group name
     */
    @Override
    public void setGroupname(String groupname) {

        this.groupname = groupname;

    }


    /**
     * Return the set of {@link Role}s assigned specifically to this group.
     */
    @Override
    public abstract Iterator<Role> getRoles();


    /**
     * Return the {@link UserDatabase} within which this Group is defined.
     */
    @Override
    public abstract UserDatabase getUserDatabase();


    /**
     * Return an Iterator over the set of {@link org.apache.catalina.User}s that 
     * are members of this group.
     */
    @Override
    public abstract Iterator<User> getUsers();


    // --------------------------------------------------------- Public Methods


    /**
     * Add a new {@link Role} to those assigned specifically to this group.
     *
     * @param role The new role
     */
    @Override
    public abstract void addRole(Role role);


    /**
     * Is this group specifically assigned the specified {@link Role}?
     *
     * @param role The role to check
     */
    @Override
    public abstract boolean isInRole(Role role);


    /**
     * Remove a {@link Role} from those assigned to this group.
     *
     * @param role The old role
     */
    @Override
    public abstract void removeRole(Role role);


    /**
     * Remove all {@link Role}s from those assigned to this group.
     */
    @Override
    public abstract void removeRoles();


    // ------------------------------------------------------ Principal Methods


    /**
     * Make the principal name the same as the group name.
     */
    @Override
    public String getName() {

        return (getGroupname());

    }


}
