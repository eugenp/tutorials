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

package org.apache.catalina.mbeans;


import java.util.ArrayList;
import java.util.Iterator;

import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;

import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.apache.tomcat.util.modeler.BaseModelMBean;
import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.Registry;

/**
 * <p>A <strong>ModelMBean</strong> implementation for the
 * <code>org.apache.catalina.users.MemoryUserDatabase</code> component.</p>
 *
 * @author Craig R. McClanahan
 */
public class MemoryUserDatabaseMBean extends BaseModelMBean {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a <code>ModelMBean</code> with default
     * <code>ModelMBeanInfo</code> information.
     *
     * @exception MBeanException if the initializer of an object
     *  throws an exception
     * @exception RuntimeOperationsException if an IllegalArgumentException
     *  occurs
     */
    public MemoryUserDatabaseMBean()
        throws MBeanException, RuntimeOperationsException {

        super();

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The configuration information registry for our managed beans.
     */
    protected Registry registry = MBeanUtils.createRegistry();


    /**
     * The <code>ManagedBean</code> information describing this MBean.
     */
    protected ManagedBean managed =
        registry.findManagedBean("MemoryUserDatabase");


    /**
     * The <code>ManagedBean</code> information describing Group MBeans.
     */
    protected ManagedBean managedGroup =
        registry.findManagedBean("Group");


    /**
     * The <code>ManagedBean</code> information describing Group MBeans.
     */
    protected ManagedBean managedRole =
        registry.findManagedBean("Role");


    /**
     * The <code>ManagedBean</code> information describing User MBeans.
     */
    protected ManagedBean managedUser =
        registry.findManagedBean("User");


    // ------------------------------------------------------------- Attributes


    /**
     * Return the MBean Names of all groups defined in this database.
     */
    public String[] getGroups() {

        UserDatabase database = (UserDatabase) this.resource;
        ArrayList<String> results = new ArrayList<String>();
        Iterator<Group> groups = database.getGroups();
        while (groups.hasNext()) {
            Group group = groups.next();
            results.add(findGroup(group.getGroupname()));
        }
        return results.toArray(new String[results.size()]);

    }


    /**
     * Return the MBean Names of all roles defined in this database.
     */
    public String[] getRoles() {

        UserDatabase database = (UserDatabase) this.resource;
        ArrayList<String> results = new ArrayList<String>();
        Iterator<Role> roles = database.getRoles();
        while (roles.hasNext()) {
            Role role = roles.next();
            results.add(findRole(role.getRolename()));
        }
        return results.toArray(new String[results.size()]);

    }


    /**
     * Return the MBean Names of all users defined in this database.
     */
    public String[] getUsers() {

        UserDatabase database = (UserDatabase) this.resource;
        ArrayList<String> results = new ArrayList<String>();
        Iterator<User> users = database.getUsers();
        while (users.hasNext()) {
            User user = users.next();
            results.add(findUser(user.getUsername()));
        }
        return results.toArray(new String[results.size()]);

    }


    // ------------------------------------------------------------- Operations


    /**
     * Create a new Group and return the corresponding MBean Name.
     *
     * @param groupname Group name of the new group
     * @param description Description of the new group
     */
    public String createGroup(String groupname, String description) {

        UserDatabase database = (UserDatabase) this.resource;
        Group group = database.createGroup(groupname, description);
        try {
            MBeanUtils.createMBean(group);
        } catch (Exception e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Exception creating group [" + groupname + "] MBean");
            iae.initCause(e);
            throw iae;
        }
        return (findGroup(groupname));

    }


    /**
     * Create a new Role and return the corresponding MBean Name.
     *
     * @param rolename Group name of the new group
     * @param description Description of the new group
     */
    public String createRole(String rolename, String description) {

        UserDatabase database = (UserDatabase) this.resource;
        Role role = database.createRole(rolename, description);
        try {
            MBeanUtils.createMBean(role);
        } catch (Exception e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Exception creating role [" + rolename + "] MBean");
            iae.initCause(e);
            throw iae;
        }
        return (findRole(rolename));

    }


    /**
     * Create a new User and return the corresponding MBean Name.
     *
     * @param username User name of the new user
     * @param password Password for the new user
     * @param fullName Full name for the new user
     */
    public String createUser(String username, String password,
                             String fullName) {

        UserDatabase database = (UserDatabase) this.resource;
        User user = database.createUser(username, password, fullName);
        try {
            MBeanUtils.createMBean(user);
        } catch (Exception e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Exception creating user [" + username + "] MBean");
            iae.initCause(e);
            throw iae;
        }
        return (findUser(username));

    }


    /**
     * Return the MBean Name for the specified group name (if any);
     * otherwise return <code>null</code>.
     *
     * @param groupname Group name to look up
     */
    public String findGroup(String groupname) {

        UserDatabase database = (UserDatabase) this.resource;
        Group group = database.findGroup(groupname);
        if (group == null) {
            return (null);
        }
        try {
            ObjectName oname =
                MBeanUtils.createObjectName(managedGroup.getDomain(), group);
            return (oname.toString());
        } catch (MalformedObjectNameException e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Cannot create object name for group [" + groupname + "]");
            iae.initCause(e);
            throw iae;
        }

    }


    /**
     * Return the MBean Name for the specified role name (if any);
     * otherwise return <code>null</code>.
     *
     * @param rolename Role name to look up
     */
    public String findRole(String rolename) {

        UserDatabase database = (UserDatabase) this.resource;
        Role role = database.findRole(rolename);
        if (role == null) {
            return (null);
        }
        try {
            ObjectName oname =
                MBeanUtils.createObjectName(managedRole.getDomain(), role);
            return (oname.toString());
        } catch (MalformedObjectNameException e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Cannot create object name for role [" + rolename + "]");
            iae.initCause(e);
            throw iae;
        }

    }


    /**
     * Return the MBean Name for the specified user name (if any);
     * otherwise return <code>null</code>.
     *
     * @param username User name to look up
     */
    public String findUser(String username) {

        UserDatabase database = (UserDatabase) this.resource;
        User user = database.findUser(username);
        if (user == null) {
            return (null);
        }
        try {
            ObjectName oname =
                MBeanUtils.createObjectName(managedUser.getDomain(), user);
            return (oname.toString());
        } catch (MalformedObjectNameException e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Cannot create object name for user [" + username + "]");
            iae.initCause(e);
            throw iae;
        }

    }


    /**
     * Remove an existing group and destroy the corresponding MBean.
     *
     * @param groupname Group name to remove
     */
    public void removeGroup(String groupname) {

        UserDatabase database = (UserDatabase) this.resource;
        Group group = database.findGroup(groupname);
        if (group == null) {
            return;
        }
        try {
            MBeanUtils.destroyMBean(group);
            database.removeGroup(group);
        } catch (Exception e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Exception destroying group [" + groupname + "] MBean");
            iae.initCause(e);
            throw iae;
        }

    }


    /**
     * Remove an existing role and destroy the corresponding MBean.
     *
     * @param rolename Role name to remove
     */
    public void removeRole(String rolename) {

        UserDatabase database = (UserDatabase) this.resource;
        Role role = database.findRole(rolename);
        if (role == null) {
            return;
        }
        try {
            MBeanUtils.destroyMBean(role);
            database.removeRole(role);
        } catch (Exception e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Exception destroying role [" + rolename + "] MBean");
            iae.initCause(e);
            throw iae;
        }

    }


    /**
     * Remove an existing user and destroy the corresponding MBean.
     *
     * @param username User name to remove
     */
    public void removeUser(String username) {

        UserDatabase database = (UserDatabase) this.resource;
        User user = database.findUser(username);
        if (user == null) {
            return;
        }
        try {
            MBeanUtils.destroyMBean(user);
            database.removeUser(user);
        } catch (Exception e) {
            IllegalArgumentException iae = new IllegalArgumentException
                ("Exception destroying user [" + username + "] MBean");
            iae.initCause(e);
            throw iae;
        }

    }


}
