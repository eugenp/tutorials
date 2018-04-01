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


/**
 * <p>Convenience base class for {@link User} implementations.</p>
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public abstract class AbstractUser implements User {


    // ----------------------------------------------------- Instance Variables


    /**
     * The full name of this user.
     */
    protected String fullName = null;


    /**
     * The logon password of this user.
     */
    protected String password = null;


    /**
     * The logon username of this user.
     */
    protected String username = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the full name of this user.
     */
    @Override
    public String getFullName() {

        return (this.fullName);

    }


    /**
     * Set the full name of this user.
     *
     * @param fullName The new full name
     */
    @Override
    public void setFullName(String fullName) {

        this.fullName = fullName;

    }


    /**
     * Return the set of {@link Group}s to which this user belongs.
     */
    @Override
    public abstract Iterator<Group> getGroups();


    /**
     * Return the logon password of this user, optionally prefixed with the
     * identifier of an encoding scheme surrounded by curly braces, such as
     * <code>{md5}xxxxx</code>.
     */
    @Override
    public String getPassword() {

        return (this.password);

    }


    /**
     * Set the logon password of this user, optionally prefixed with the
     * identifier of an encoding scheme surrounded by curly braces, such as
     * <code>{md5}xxxxx</code>.
     *
     * @param password The new logon password
     */
    @Override
    public void setPassword(String password) {

        this.password = password;

    }


    /**
     * Return the set of {@link Role}s assigned specifically to this user.
     */
    @Override
    public abstract Iterator<Role> getRoles();


    /**
     * Return the logon username of this user, which must be unique
     * within the scope of a {@link org.apache.catalina.UserDatabase}.
     */
    @Override
    public String getUsername() {

        return (this.username);

    }


    /**
     * Set the logon username of this user, which must be unique within
     * the scope of a {@link org.apache.catalina.UserDatabase}.
     *
     * @param username The new logon username
     */
    @Override
    public void setUsername(String username) {

        this.username = username;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Add a new {@link Group} to those this user belongs to.
     *
     * @param group The new group
     */
    @Override
    public abstract void addGroup(Group group);


    /**
     * Add a new {@link Role} to those assigned specifically to this user.
     *
     * @param role The new role
     */
    @Override
    public abstract void addRole(Role role);


    /**
     * Is this user in the specified {@link Group}?
     *
     * @param group The group to check
     */
    @Override
    public abstract boolean isInGroup(Group group);


    /**
     * Is this user specifically assigned the specified {@link Role}?  This
     * method does <strong>NOT</strong> check for roles inherited based on
     * {@link Group} membership.
     *
     * @param role The role to check
     */
    @Override
    public abstract boolean isInRole(Role role);


    /**
     * Remove a {@link Group} from those this user belongs to.
     *
     * @param group The old group
     */
    @Override
    public abstract void removeGroup(Group group);


    /**
     * Remove all {@link Group}s from those this user belongs to.
     */
    @Override
    public abstract void removeGroups();


    /**
     * Remove a {@link Role} from those assigned to this user.
     *
     * @param role The old role
     */
    @Override
    public abstract void removeRole(Role role);


    /**
     * Remove all {@link Role}s from those assigned to this user.
     */
    @Override
    public abstract void removeRoles();


    // ------------------------------------------------------ Principal Methods


    /**
     * Make the principal name the same as the group name.
     */
    @Override
    public String getName() {

        return (getUsername());

    }


}
