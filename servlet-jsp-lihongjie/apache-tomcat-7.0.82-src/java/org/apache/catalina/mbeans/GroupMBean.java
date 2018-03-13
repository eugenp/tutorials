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
import org.apache.tomcat.util.modeler.BaseModelMBean;
import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.Registry;

/**
 * <p>A <strong>ModelMBean</strong> implementation for the
 * <code>org.apache.catalina.Group</code> component.</p>
 *
 * @author Craig R. McClanahan
 */
public class GroupMBean extends BaseModelMBean {


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
    public GroupMBean()
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
        registry.findManagedBean("Group");


    // ------------------------------------------------------------- Attributes


    /**
     * Return the MBean Names of all authorized roles for this group.
     */
    public String[] getRoles() {

        Group group = (Group) this.resource;
        ArrayList<String> results = new ArrayList<String>();
        Iterator<Role> roles = group.getRoles();
        while (roles.hasNext()) {
            Role role = null;
            try {
                role = roles.next();
                ObjectName oname =
                    MBeanUtils.createObjectName(managed.getDomain(), role);
                results.add(oname.toString());
            } catch (MalformedObjectNameException e) {
                IllegalArgumentException iae = new IllegalArgumentException
                    ("Cannot create object name for role " + role);
                iae.initCause(e);
                throw iae;
            }
        }
        return results.toArray(new String[results.size()]);

    }


    /**
     * Return the MBean Names of all users that are members of this group.
     */
    public String[] getUsers() {

        Group group = (Group) this.resource;
        ArrayList<String> results = new ArrayList<String>();
        Iterator<User> users = group.getUsers();
        while (users.hasNext()) {
            User user = null;
            try {
                user = users.next();
                ObjectName oname =
                    MBeanUtils.createObjectName(managed.getDomain(), user);
                results.add(oname.toString());
            } catch (MalformedObjectNameException e) {
                IllegalArgumentException iae = new IllegalArgumentException
                    ("Cannot create object name for user " + user);
                iae.initCause(e);
                throw iae;
            }
        }
        return results.toArray(new String[results.size()]);

    }


    // ------------------------------------------------------------- Operations


    /**
     * Add a new {@link Role} to those this group belongs to.
     *
     * @param rolename Role name of the new role
     */
    public void addRole(String rolename) {

        Group group = (Group) this.resource;
        if (group == null) {
            return;
        }
        Role role = group.getUserDatabase().findRole(rolename);
        if (role == null) {
            throw new IllegalArgumentException
                ("Invalid role name '" + rolename + "'");
        }
        group.addRole(role);

    }


    /**
     * Remove a {@link Role} from those this group belongs to.
     *
     * @param rolename Role name of the old role
     */
    public void removeRole(String rolename) {

        Group group = (Group) this.resource;
        if (group == null) {
            return;
        }
        Role role = group.getUserDatabase().findRole(rolename);
        if (role == null) {
            throw new IllegalArgumentException
                ("Invalid role name '" + rolename + "'");
        }
        group.removeRole(role);

    }


}
