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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.catalina.Globals;
import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.digester.AbstractObjectCreationFactory;
import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.file.ConfigFileLoader;
import org.apache.tomcat.util.res.StringManager;
import org.xml.sax.Attributes;

/**
 * <p>Concrete implementation of {@link UserDatabase} that loads all
 * defined users, groups, and roles into an in-memory data structure,
 * and uses a specified XML file for its persistent storage.</p>
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public class MemoryUserDatabase implements UserDatabase {


    private static final Log log = LogFactory.getLog(MemoryUserDatabase.class);

    // ----------------------------------------------------------- Constructors


    /**
     * Create a new instance with default values.
     */
    public MemoryUserDatabase() {
        this(null);
    }


    /**
     * Create a new instance with the specified values.
     *
     * @param id Unique global identifier of this user database
     */
    public MemoryUserDatabase(String id) {
        this.id = id;
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The set of {@link Group}s defined in this database, keyed by
     * group name.
     */
    protected final HashMap<String,Group> groups = new HashMap<String,Group>();


    /**
     * The unique global identifier of this user database.
     */
    protected final String id;


    /**
     * The relative (to <code>catalina.base</code>) or absolute pathname to
     * the XML file in which we will save our persistent information.
     */
    protected String pathname = "conf/tomcat-users.xml";


    /**
     * The relative or absolute pathname to the file in which our old
     * information is stored while renaming is in progress.
     */
    protected String pathnameOld = pathname + ".old";


    /**
     * The relative or absolute pathname of the file in which we write
     * our new information prior to renaming.
     */
    protected String pathnameNew = pathname + ".new";


    /**
     * A flag, indicating if the user database is read only.
     */
    protected boolean readonly = true;

    /**
     * The set of {@link Role}s defined in this database, keyed by
     * role name.
     */
    protected final HashMap<String,Role> roles = new HashMap<String,Role>();


    /**
     * The string manager for this package.
     */
    private static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * The set of {@link User}s defined in this database, keyed by
     * user name.
     */
    protected final HashMap<String,User> users = new HashMap<String,User>();


    // ------------------------------------------------------------- Properties


    /**
     * Return the set of {@link Group}s defined in this user database.
     */
    @Override
    public Iterator<Group> getGroups() {

        synchronized (groups) {
            return (groups.values().iterator());
        }

    }


    /**
     * Return the unique global identifier of this user database.
     */
    @Override
    public String getId() {

        return (this.id);

    }


    /**
     * Return the relative or absolute pathname to the persistent storage file.
     */
    public String getPathname() {

        return (this.pathname);

    }


    /**
     * Set the relative or absolute pathname to the persistent storage file.
     *
     * @param pathname The new pathname
     */
    public void setPathname(String pathname) {

        this.pathname = pathname;
        this.pathnameOld = pathname + ".old";
        this.pathnameNew = pathname + ".new";

    }


    /**
     * Returning the readonly status of the user database
     */
    public boolean getReadonly() {

        return (this.readonly);

    }


    /**
     * Setting the readonly status of the user database
     *
     * @param readonly the new status
     */
    public void setReadonly(boolean readonly) {

        this.readonly = readonly;

    }


    /**
     * Return the set of {@link Role}s defined in this user database.
     */
    @Override
    public Iterator<Role> getRoles() {

        synchronized (roles) {
            return (roles.values().iterator());
        }

    }


    /**
     * Return the set of {@link User}s defined in this user database.
     */
    @Override
    public Iterator<User> getUsers() {

        synchronized (users) {
            return (users.values().iterator());
        }

    }



    // --------------------------------------------------------- Public Methods


    /**
     * Finalize access to this user database.
     *
     * @exception Exception if any exception is thrown during closing
     */
    @Override
    public void close() throws Exception {

        save();

        synchronized (groups) {
            synchronized (users) {
                users.clear();
                groups.clear();
            }
        }

    }


    /**
     * Create and return a new {@link Group} defined in this user database.
     *
     * @param groupname The group name of the new group (must be unique)
     * @param description The description of this group
     */
    @Override
    public Group createGroup(String groupname, String description) {

        if (groupname == null || groupname.length() == 0) {
            String msg = sm.getString("memoryUserDatabase.nullGroup");
            log.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        MemoryGroup group = new MemoryGroup(this, groupname, description);
        synchronized (groups) {
            groups.put(group.getGroupname(), group);
        }
        return (group);

    }


    /**
     * Create and return a new {@link Role} defined in this user database.
     *
     * @param rolename The role name of the new group (must be unique)
     * @param description The description of this group
     */
    @Override
    public Role createRole(String rolename, String description) {

        if (rolename == null || rolename.length() == 0) {
            String msg = sm.getString("memoryUserDatabase.nullRole");
            log.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        MemoryRole role = new MemoryRole(this, rolename, description);
        synchronized (roles) {
            roles.put(role.getRolename(), role);
        }
        return (role);

    }


    /**
     * Create and return a new {@link User} defined in this user database.
     *
     * @param username The logon username of the new user (must be unique)
     * @param password The logon password of the new user
     * @param fullName The full name of the new user
     */
    @Override
    public User createUser(String username, String password,
                           String fullName) {

        if (username == null || username.length() == 0) {
            String msg = sm.getString("memoryUserDatabase.nullUser");
            log.warn(msg);
            throw new IllegalArgumentException(msg);
        }

        MemoryUser user = new MemoryUser(this, username, password, fullName);
        synchronized (users) {
            users.put(user.getUsername(), user);
        }
        return (user);
    }


    /**
     * Return the {@link Group} with the specified group name, if any;
     * otherwise return <code>null</code>.
     *
     * @param groupname Name of the group to return
     */
    @Override
    public Group findGroup(String groupname) {

        synchronized (groups) {
            return groups.get(groupname);
        }

    }


    /**
     * Return the {@link Role} with the specified role name, if any;
     * otherwise return <code>null</code>.
     *
     * @param rolename Name of the role to return
     */
    @Override
    public Role findRole(String rolename) {

        synchronized (roles) {
            return roles.get(rolename);
        }

    }


    /**
     * Return the {@link User} with the specified user name, if any;
     * otherwise return <code>null</code>.
     *
     * @param username Name of the user to return
     */
    @Override
    public User findUser(String username) {

        synchronized (users) {
            return users.get(username);
        }

    }


    /**
     * Initialize access to this user database.
     *
     * @exception Exception if any exception is thrown during opening
     */
    @Override
    public void open() throws Exception {

        synchronized (groups) {
            synchronized (users) {

                // Erase any previous groups and users
                users.clear();
                groups.clear();
                roles.clear();

                String pathName = getPathname();
                InputStream is = null;

                try {
                    is = ConfigFileLoader.getInputStream(pathName);

                    // Construct a digester to read the XML input file
                    Digester digester = new Digester();
                    try {
                        digester.setFeature(
                                "http://apache.org/xml/features/allow-java-encodings",
                                true);
                    } catch (Exception e) {
                        log.warn(sm.getString("memoryUserDatabase.xmlFeatureEncoding"), e);
                    }
                    digester.addFactoryCreate
                            ("tomcat-users/group",
                                    new MemoryGroupCreationFactory(this), true);
                    digester.addFactoryCreate
                            ("tomcat-users/role",
                                    new MemoryRoleCreationFactory(this), true);
                    digester.addFactoryCreate
                            ("tomcat-users/user",
                                    new MemoryUserCreationFactory(this), true);

                    // Parse the XML input to load this database
                    digester.parse(is);
                } catch (IOException ioe) {
                    log.error(sm.getString("memoryUserDatabase.fileNotFound", pathName));
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException ioe) {
                            // Ignore
                        }
                    }
                }
            }
        }

    }


    /**
     * Remove the specified {@link Group} from this user database.
     *
     * @param group The group to be removed
     */
    @Override
    public void removeGroup(Group group) {

        synchronized (groups) {
            Iterator<User> users = getUsers();
            while (users.hasNext()) {
                User user = users.next();
                user.removeGroup(group);
            }
            groups.remove(group.getGroupname());
        }

    }


    /**
     * Remove the specified {@link Role} from this user database.
     *
     * @param role The role to be removed
     */
    @Override
    public void removeRole(Role role) {

        synchronized (roles) {
            Iterator<Group> groups = getGroups();
            while (groups.hasNext()) {
                Group group = groups.next();
                group.removeRole(role);
            }
            Iterator<User> users = getUsers();
            while (users.hasNext()) {
                User user = users.next();
                user.removeRole(role);
            }
            roles.remove(role.getRolename());
        }

    }


    /**
     * Remove the specified {@link User} from this user database.
     *
     * @param user The user to be removed
     */
    @Override
    public void removeUser(User user) {

        synchronized (users) {
            users.remove(user.getUsername());
        }

    }


    /**
     * Check for permissions to save this user database
     * to persistent storage location
     *
     */
    public boolean isWriteable() {

        File file = new File(pathname);
        if (!file.isAbsolute()) {
            file = new File(System.getProperty(Globals.CATALINA_BASE_PROP),
                            pathname);
        }
        File dir = file.getParentFile();
        return dir.exists() && dir.isDirectory() && dir.canWrite();

    }


    /**
     * Save any updated information to the persistent storage location for
     * this user database.
     *
     * @exception Exception if any exception is thrown during saving
     */
    @Override
    public void save() throws Exception {

        if (getReadonly()) {
            log.error(sm.getString("memoryUserDatabase.readOnly"));
            return;
        }

        if (!isWriteable()) {
            log.warn(sm.getString("memoryUserDatabase.notPersistable"));
            return;
        }

        // Write out contents to a temporary file
        File fileNew = new File(pathnameNew);
        if (!fileNew.isAbsolute()) {
            fileNew =
                new File(System.getProperty(Globals.CATALINA_BASE_PROP), pathnameNew);
        }
        PrintWriter writer = null;
        try {

            // Configure our PrintWriter
            FileOutputStream fos = new FileOutputStream(fileNew);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
            writer = new PrintWriter(osw);

            // Print the file prolog
            writer.println("<?xml version='1.0' encoding='utf-8'?>");
            writer.println("<tomcat-users>");

            // Print entries for each defined role, group, and user
            Iterator<?> values = null;
            values = getRoles();
            while (values.hasNext()) {
                writer.print("  ");
                writer.println(values.next());
            }
            values = getGroups();
            while (values.hasNext()) {
                writer.print("  ");
                writer.println(values.next());
            }
            values = getUsers();
            while (values.hasNext()) {
                writer.print("  ");
                writer.println(((MemoryUser) values.next()).toXml());
            }

            // Print the file epilog
            writer.println("</tomcat-users>");

            // Check for errors that occurred while printing
            if (writer.checkError()) {
                writer.close();
                fileNew.delete();
                throw new IOException
                    (sm.getString("memoryUserDatabase.writeException",
                                  fileNew.getAbsolutePath()));
            }
            writer.close();
        } catch (IOException e) {
            if (writer != null) {
                writer.close();
            }
            fileNew.delete();
            throw e;
        }

        // Perform the required renames to permanently save this file
        File fileOld = new File(pathnameOld);
        if (!fileOld.isAbsolute()) {
            fileOld =
                new File(System.getProperty(Globals.CATALINA_BASE_PROP), pathnameOld);
        }
        fileOld.delete();
        File fileOrig = new File(pathname);
        if (!fileOrig.isAbsolute()) {
            fileOrig =
                new File(System.getProperty(Globals.CATALINA_BASE_PROP), pathname);
        }
        if (fileOrig.exists()) {
            fileOld.delete();
            if (!fileOrig.renameTo(fileOld)) {
                throw new IOException
                    (sm.getString("memoryUserDatabase.renameOld",
                                  fileOld.getAbsolutePath()));
            }
        }
        if (!fileNew.renameTo(fileOrig)) {
            if (fileOld.exists()) {
                fileOld.renameTo(fileOrig);
            }
            throw new IOException
                (sm.getString("memoryUserDatabase.renameNew",
                              fileOrig.getAbsolutePath()));
        }
        fileOld.delete();

    }


    /**
     * Return a String representation of this UserDatabase.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("MemoryUserDatabase[id=");
        sb.append(this.id);
        sb.append(",pathname=");
        sb.append(pathname);
        sb.append(",groupCount=");
        sb.append(this.groups.size());
        sb.append(",roleCount=");
        sb.append(this.roles.size());
        sb.append(",userCount=");
        sb.append(this.users.size());
        sb.append("]");
        return (sb.toString());

    }


    // -------------------------------------------------------- Package Methods


    /**
     * Return the <code>StringManager</code> for use in looking up messages.
     */
    StringManager getStringManager() {

        return (sm);

    }


}



/**
 * Digester object creation factory for group instances.
 */
class MemoryGroupCreationFactory extends AbstractObjectCreationFactory {

    public MemoryGroupCreationFactory(MemoryUserDatabase database) {
        this.database = database;
    }

    @Override
    public Object createObject(Attributes attributes) {
        String groupname = attributes.getValue("groupname");
        if (groupname == null) {
            groupname = attributes.getValue("name");
        }
        String description = attributes.getValue("description");
        String roles = attributes.getValue("roles");
        Group group = database.createGroup(groupname, description);
        if (roles != null) {
            while (roles.length() > 0) {
                String rolename = null;
                int comma = roles.indexOf(',');
                if (comma >= 0) {
                    rolename = roles.substring(0, comma).trim();
                    roles = roles.substring(comma + 1);
                } else {
                    rolename = roles.trim();
                    roles = "";
                }
                if (rolename.length() > 0) {
                    Role role = database.findRole(rolename);
                    if (role == null) {
                        role = database.createRole(rolename, null);
                    }
                    group.addRole(role);
                }
            }
        }
        return (group);
    }

    private MemoryUserDatabase database = null;
}


/**
 * Digester object creation factory for role instances.
 */
class MemoryRoleCreationFactory extends AbstractObjectCreationFactory {

    public MemoryRoleCreationFactory(MemoryUserDatabase database) {
        this.database = database;
    }

    @Override
    public Object createObject(Attributes attributes) {
        String rolename = attributes.getValue("rolename");
        if (rolename == null) {
            rolename = attributes.getValue("name");
        }
        String description = attributes.getValue("description");
        Role role = database.createRole(rolename, description);
        return (role);
    }

    private MemoryUserDatabase database = null;
}


/**
 * Digester object creation factory for user instances.
 */
class MemoryUserCreationFactory extends AbstractObjectCreationFactory {

    public MemoryUserCreationFactory(MemoryUserDatabase database) {
        this.database = database;
    }

    @Override
    public Object createObject(Attributes attributes) {
        String username = attributes.getValue("username");
        if (username == null) {
            username = attributes.getValue("name");
        }
        String password = attributes.getValue("password");
        String fullName = attributes.getValue("fullName");
        if (fullName == null) {
            fullName = attributes.getValue("fullname");
        }
        String groups = attributes.getValue("groups");
        String roles = attributes.getValue("roles");
        User user = database.createUser(username, password, fullName);
        if (groups != null) {
            while (groups.length() > 0) {
                String groupname = null;
                int comma = groups.indexOf(',');
                if (comma >= 0) {
                    groupname = groups.substring(0, comma).trim();
                    groups = groups.substring(comma + 1);
                } else {
                    groupname = groups.trim();
                    groups = "";
                }
                if (groupname.length() > 0) {
                    Group group = database.findGroup(groupname);
                    if (group == null) {
                        group = database.createGroup(groupname, null);
                    }
                    user.addGroup(group);
                }
            }
        }
        if (roles != null) {
            while (roles.length() > 0) {
                String rolename = null;
                int comma = roles.indexOf(',');
                if (comma >= 0) {
                    rolename = roles.substring(0, comma).trim();
                    roles = roles.substring(comma + 1);
                } else {
                    rolename = roles.trim();
                    roles = "";
                }
                if (rolename.length() > 0) {
                    Role role = database.findRole(rolename);
                    if (role == null) {
                        role = database.createRole(rolename, null);
                    }
                    user.addRole(role);
                }
            }
        }
        return (user);
    }

    private MemoryUserDatabase database = null;
}
