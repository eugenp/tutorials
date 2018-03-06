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


package org.apache.catalina;


import java.security.Principal;
import java.util.Iterator;


/**
 * <p>Abstract representation of a group of {@link User}s in a
 * {@link UserDatabase}.  Each user that is a member of this group
 * inherits the {@link Role}s assigned to the group.</p>
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public interface Group extends Principal {


    // ------------------------------------------------------------- Properties


    /**
     * Return the description of this group.
     */
    public String getDescription();


    /**
     * Set the description of this group.
     *
     * @param description The new description
     */
    public void setDescription(String description);


    /**
     * Return the group name of this group, which must be unique
     * within the scope of a {@link UserDatabase}.
     */
    public String getGroupname();


    /**
     * Set the group name of this group, which must be unique
     * within the scope of a {@link UserDatabase}.
     *
     * @param groupname The new group name
     */
    public void setGroupname(String groupname);


    /**
     * Return the set of {@link Role}s assigned specifically to this group.
     */
    public Iterator<Role> getRoles();


    /**
     * Return the {@link UserDatabase} within which this Group is defined.
     */
    public UserDatabase getUserDatabase();


    /**
     * Return the set of {@link User}s that are members of this group.
     */
    public Iterator<User> getUsers();


    // --------------------------------------------------------- Public Methods


    /**
     * Add a new {@link Role} to those assigned specifically to this group.
     *
     * @param role The new role
     */
    public void addRole(Role role);


    /**
     * Is this group specifically assigned the specified {@link Role}?
     *
     * @param role The role to check
     */
    public boolean isInRole(Role role);


    /**
     * Remove a {@link Role} from those assigned to this group.
     *
     * @param role The old role
     */
    public void removeRole(Role role);


    /**
     * Remove all {@link Role}s from those assigned to this group.
     */
    public void removeRoles();


}
