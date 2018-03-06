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
 * <p>Abstract representation of a user in a {@link UserDatabase}.  Each user
 * is optionally associated with a set of {@link Group}s through which he or
 * she inherits additional security roles, and is optionally assigned a set
 * of specific {@link Role}s.</p>
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public interface User extends Principal {


    // ------------------------------------------------------------- Properties


    /**
     * Return the full name of this user.
     */
    public String getFullName();


    /**
     * Set the full name of this user.
     *
     * @param fullName The new full name
     */
    public void setFullName(String fullName);


    /**
     * Return the set of {@link Group}s to which this user belongs.
     */
    public Iterator<Group> getGroups();


    /**
     * Return the logon password of this user, optionally prefixed with the
     * identifier of an encoding scheme surrounded by curly braces, such as
     * <code>{md5}xxxxx</code>.
     */
    public String getPassword();


    /**
     * Set the logon password of this user, optionally prefixed with the
     * identifier of an encoding scheme surrounded by curly braces, such as
     * <code>{md5}xxxxx</code>.
     *
     * @param password The new logon password
     */
    public void setPassword(String password);


    /**
     * Return the set of {@link Role}s assigned specifically to this user.
     */
    public Iterator<Role> getRoles();


    /**
     * Return the {@link UserDatabase} within which this User is defined.
     */
    public UserDatabase getUserDatabase();


    /**
     * Return the logon username of this user, which must be unique
     * within the scope of a {@link UserDatabase}.
     */
    public String getUsername();


    /**
     * Set the logon username of this user, which must be unique within
     * the scope of a {@link UserDatabase}.
     *
     * @param username The new logon username
     */
    public void setUsername(String username);


    // --------------------------------------------------------- Public Methods


    /**
     * Add a new {@link Group} to those this user belongs to.
     *
     * @param group The new group
     */
    public void addGroup(Group group);


    /**
     * Add a {@link Role} to those assigned specifically to this user.
     *
     * @param role The new role
     */
    public void addRole(Role role);


    /**
     * Is this user in the specified {@link Group}?
     *
     * @param group The group to check
     */
    public boolean isInGroup(Group group);


    /**
     * Is this user specifically assigned the specified {@link Role}?  This
     * method does <strong>NOT</strong> check for roles inherited based on
     * {@link Group} membership.
     *
     * @param role The role to check
     */
    public boolean isInRole(Role role);


    /**
     * Remove a {@link Group} from those this user belongs to.
     *
     * @param group The old group
     */
    public void removeGroup(Group group);


    /**
     * Remove all {@link Group}s from those this user belongs to.
     */
    public void removeGroups();


    /**
     * Remove a {@link Role} from those assigned to this user.
     *
     * @param role The old role
     */
    public void removeRole(Role role);


    /**
     * Remove all {@link Role}s from those assigned to this user.
     */
    public void removeRoles();


}
