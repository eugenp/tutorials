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


package org.apache.naming.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.naming.CompositeName;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.naming.NamingEntry;

/**
 * WAR Directory Context implementation.
 *
 * @author Remy Maucherat
 */
public class WARDirContext extends BaseDirContext {

    private static final org.apache.juli.logging.Log log=
        org.apache.juli.logging.LogFactory.getLog( WARDirContext.class );
    
    // ----------------------------------------------------------- Constructors


    /**
     * Builds a WAR directory context using the given environment.
     */
    public WARDirContext() {
        super();
    }


    /**
     * Builds a WAR directory context using the given environment.
     */
    public WARDirContext(Hashtable<String,Object> env) {
        super(env);
    }


    /**
     * Constructor used for returning fake sub-contexts or for accessing
     * META-INF/resources locations in bundled JAR files.
     */
    protected WARDirContext(ZipFile base, Entry entries) {
        this.base = base;
        this.entries = entries;
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The WAR file.
     */
    protected ZipFile base = null;


    /**
     * WAR entries.
     */
    protected Entry entries = null;


    // ------------------------------------------------------------- Properties


    /**
     * Set the document root.
     * 
     * @param docBase The new document root
     * 
     * @exception IllegalArgumentException if the specified value is not
     *  supported by this implementation
     * @exception IllegalArgumentException if this would create a
     *  malformed URL
     */
    @Override
    public void setDocBase(String docBase) {

        // Validate the format of the proposed document root
        if (docBase == null)
            throw new IllegalArgumentException
                (sm.getString("resources.null"));
        if (!(docBase.endsWith(".war")))
            throw new IllegalArgumentException
                (sm.getString("warResources.notWar"));

        // Calculate a File object referencing this document base directory
        File base = new File(docBase);

        // Validate that the document base is an existing directory
        if (!base.exists() || !base.canRead() || base.isDirectory())
            throw new IllegalArgumentException
                (sm.getString("warResources.invalidWar", docBase));
        try {
            this.base = new ZipFile(base);
        } catch (Exception e) {
            throw new IllegalArgumentException
                (sm.getString("warResources.invalidWar", e.getMessage()));
        }
        super.setDocBase(docBase);

        loadEntries();

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Release any resources allocated for this directory context.
     */
    @Override
    public void release() {

        entries = null;
        if (base != null) {
            try {
                base.close();
            } catch (IOException e) {
                log.warn
                    ("Exception closing WAR File " + base.getName(), e);
            }
        }
        base = null;
        super.release();

    }

    /**
     * Return the real path for a given virtual path, if possible; otherwise
     * return <code>null</code>.
     *
     * @param path The path to the desired resource
     */
    @Override
    protected String doGetRealPath(String path) {
        return null;
    }


    // -------------------------------------------------------- Context Methods


    /**
     * Retrieves the named object.
     * 
     * @param strName the name of the object to look up
     * @return the object bound to name
     */
    @Override
    protected Object doLookup(String strName) {

        Name name;
        try {
            name = getEscapedJndiName(strName);
        } catch (InvalidNameException e) {
            log.info(sm.getString("resources.invalidName", strName), e);
            return null;
        }

        if (name.isEmpty())
            return this;
        Entry entry = treeLookup(name);
        if (entry == null)
            return null;
            
        ZipEntry zipEntry = entry.getEntry();
        if (zipEntry.isDirectory())
            return new WARDirContext(base, entry);
        else
            return new WARResource(entry.getEntry());
    }


    /**
     * JNDI treats ' and " as reserved characters therefore they need to be
     * escaped as part of converting file names to JNDI names. Note that while
     * ' can be used in Windows and Unix file names, " is only valid on Unix.
     * This method assumes that the string is currently unquoted.
     * 
     * @return  A valid JNDI name
     * @throws InvalidNameException 
     */
    private Name getEscapedJndiName(String name) throws InvalidNameException {
        return new CompositeName(name.replace("'", "\\'").replace("\"", ""));
    }

    /**
     * {@inheritDoc}
     * 
     * This method is not supported in this implementation
     * 
     * @throws OperationNotSupportedException for this implementation
     */
    @Override
    public void unbind(String name) throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Binds a new name to the object bound to an old name, and unbinds the 
     * old name. Both names are relative to this context. Any attributes 
     * associated with the old name become associated with the new name. 
     * Intermediate contexts of the old name are not changed.
     * 
     * @param oldName the name of the existing binding; may not be empty
     * @param newName the name of the new binding; may not be empty
     * @exception javax.naming.NameAlreadyBoundException if newName is already
     * bound
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void rename(String oldName, String newName)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Enumerates the names bound in the named context, along with the 
     * objects bound to them. The contents of any subcontexts are not 
     * included.
     * <p>
     * If a binding is added to or removed from this context, its effect on 
     * an enumeration previously returned is undefined.
     * 
     * @param strName the name of the context to list
     * @return an enumeration of the bindings in this context. 
     * Each element of the enumeration is of type Binding.
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    protected List<NamingEntry> doListBindings(String strName)
        throws NamingException {
        
        Name name = getEscapedJndiName(strName);

        if (name.isEmpty())
            return list(entries);

        Entry entry = treeLookup(name);
        if (entry == null)
            return null;
        
        return list(entry);
    }


    /**
     * {@inheritDoc}
     * 
     * This method is not supported in this implementation
     * 
     * @throws OperationNotSupportedException for this implementation
     */
    @Override
    public void destroySubcontext(String name) throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Retrieves the named object, following links except for the terminal 
     * atomic component of the name. If the object bound to name is not a 
     * link, returns the object itself.
     * 
     * @param name the name of the object to look up
     * @return the object bound to name, not following the terminal link 
     * (if any).
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Object lookupLink(String name)
        throws NamingException {
        // Note : Links are not supported
        return lookup(name);
    }


    /**
     * Retrieves the full name of this context within its own namespace.
     * <p>
     * Many naming services have a notion of a "full name" for objects in 
     * their respective namespaces. For example, an LDAP entry has a 
     * distinguished name, and a DNS record has a fully qualified name. This 
     * method allows the client application to retrieve this name. The string 
     * returned by this method is not a JNDI composite name and should not be 
     * passed directly to context methods. In naming systems for which the 
     * notion of full name does not make sense, 
     * OperationNotSupportedException is thrown.
     * 
     * @return this context's name in its own namespace; never null
     * @exception OperationNotSupportedException if the naming system does 
     * not have the notion of a full name
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public String getNameInNamespace()
        throws NamingException {
        return docBase;
    }


    // ----------------------------------------------------- DirContext Methods


    /**
     * Retrieves selected attributes associated with a named object. 
     * See the class description regarding attribute models, attribute type 
     * names, and operational attributes.
     * 
     * @return the requested attributes; never null
     * @param name the name of the object from which to retrieve attributes
     * @param attrIds the identifiers of the attributes to retrieve. null 
     * indicates that all attributes should be retrieved; an empty array 
     * indicates that none should be retrieved
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    protected Attributes doGetAttributes(String name, String[] attrIds)
        throws NamingException {
        return getAttributes(getEscapedJndiName(name), attrIds);
    }


    /**
     * Retrieves all of the attributes associated with a named object. 
     * 
     * @return the set of attributes associated with name. 
     * Returns an empty attribute set if name has no attributes; never null.
     * @param name the name of the object from which to retrieve attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Attributes getAttributes(Name name, String[] attrIds)
        throws NamingException {
        
        Entry entry = null;
        if (name.isEmpty())
            entry = entries;
        else
            entry = treeLookup(name);
        if (entry == null)
            return null;
        
        ZipEntry zipEntry = entry.getEntry();

        ResourceAttributes attrs = new ResourceAttributes();
        attrs.setCreationDate(new Date(zipEntry.getTime()));
        attrs.setName(entry.getName());
        if (!zipEntry.isDirectory())
            attrs.setResourceType("");
        else
            attrs.setCollection(true);
        attrs.setContentLength(zipEntry.getSize());
        attrs.setLastModified(zipEntry.getTime());
        
        return attrs;
        
    }


    /**
     * Modifies the attributes associated with a named object. The order of 
     * the modifications is not specified. Where possible, the modifications 
     * are performed atomically.
     * 
     * @param name the name of the object whose attributes will be updated
     * @param mod_op the modification operation, one of: ADD_ATTRIBUTE, 
     * REPLACE_ATTRIBUTE, REMOVE_ATTRIBUTE
     * @param attrs the attributes to be used for the modification; may not 
     * be null
     * @exception javax.naming.directory.AttributeModificationException if the
     * modification cannot be completed successfully
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void modifyAttributes(String name, int mod_op, Attributes attrs)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Modifies the attributes associated with a named object using an an 
     * ordered list of modifications. The modifications are performed in the 
     * order specified. Each modification specifies a modification operation 
     * code and an attribute on which to operate. Where possible, the 
     * modifications are performed atomically.
     * 
     * @param name the name of the object whose attributes will be updated
     * @param mods an ordered sequence of modifications to be performed; may 
     * not be null
     * @exception javax.naming.directory.AttributeModificationException if the
     * modification cannot be completed successfully
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void modifyAttributes(String name, ModificationItem[] mods)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Binds a name to an object, along with associated attributes. If attrs 
     * is null, the resulting binding will have the attributes associated 
     * with obj if obj is a DirContext, and no attributes otherwise. If attrs 
     * is non-null, the resulting binding will have attrs as its attributes; 
     * any attributes associated with obj are ignored.
     * 
     * @param name the name to bind; may not be empty
     * @param obj the object to bind; possibly null
     * @param attrs the attributes to associate with the binding
     * @exception javax.naming.NameAlreadyBoundException if name is already
     * bound
     * @exception javax.naming.directory.InvalidAttributesException if some
     * "mandatory" attributes of the binding are not supplied
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void bind(String name, Object obj, Attributes attrs)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Binds a name to an object, along with associated attributes, 
     * overwriting any existing binding. If attrs is null and obj is a 
     * DirContext, the attributes from obj are used. If attrs is null and obj 
     * is not a DirContext, any existing attributes associated with the object
     * already bound in the directory remain unchanged. If attrs is non-null, 
     * any existing attributes associated with the object already bound in 
     * the directory are removed and attrs is associated with the named 
     * object. If obj is a DirContext and attrs is non-null, the attributes 
     * of obj are ignored.
     * 
     * @param name the name to bind; may not be empty
     * @param obj the object to bind; possibly null
     * @param attrs the attributes to associate with the binding
     * @exception javax.naming.directory.InvalidAttributesException if some
     * "mandatory" attributes of the binding are not supplied
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void rebind(String name, Object obj, Attributes attrs)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Creates and binds a new context, along with associated attributes. 
     * This method creates a new subcontext with the given name, binds it in 
     * the target context (that named by all but terminal atomic component of 
     * the name), and associates the supplied attributes with the newly 
     * created object. All intermediate and target contexts must already 
     * exist. If attrs is null, this method is equivalent to 
     * Context.createSubcontext().
     * 
     * @param name the name of the context to create; may not be empty
     * @param attrs the attributes to associate with the newly created context
     * @return the newly created context
     * @exception javax.naming.NameAlreadyBoundException if name is already
     * bound
     * @exception javax.naming.directory.InvalidAttributesException if attrs
     * does not contain all the mandatory attributes required for creation
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public DirContext createSubcontext(String name, Attributes attrs)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Retrieves the schema associated with the named object. The schema 
     * describes rules regarding the structure of the namespace and the 
     * attributes stored within it. The schema specifies what types of 
     * objects can be added to the directory and where they can be added; 
     * what mandatory and optional attributes an object can have. The range 
     * of support for schemas is directory-specific.
     * 
     * @param name the name of the object whose schema is to be retrieved
     * @return the schema associated with the context; never null
     * @exception OperationNotSupportedException if schema not supported
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public DirContext getSchema(String name)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Retrieves a context containing the schema objects of the named 
     * object's class definitions.
     * 
     * @param name the name of the object whose object class definition is to 
     * be retrieved
     * @return the DirContext containing the named object's class 
     * definitions; never null
     * @exception OperationNotSupportedException if schema not supported
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public DirContext getSchemaClassDefinition(String name)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Searches in a single context for objects that contain a specified set 
     * of attributes, and retrieves selected attributes. The search is 
     * performed using the default SearchControls settings.
     * 
     * @param name the name of the context to search
     * @param matchingAttributes the attributes to search for. If empty or 
     * null, all objects in the target context are returned.
     * @param attributesToReturn the attributes to return. null indicates 
     * that all attributes are to be returned; an empty array indicates that 
     * none are to be returned.
     * @return a non-null enumeration of SearchResult objects. Each 
     * SearchResult contains the attributes identified by attributesToReturn 
     * and the name of the corresponding object, named relative to the 
     * context named by name.
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NamingEnumeration<SearchResult> search(String name,
            Attributes matchingAttributes, String[] attributesToReturn)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Searches in a single context for objects that contain a specified set 
     * of attributes. This method returns all the attributes of such objects. 
     * It is equivalent to supplying null as the atributesToReturn parameter 
     * to the method search(Name, Attributes, String[]).
     * 
     * @param name the name of the context to search
     * @param matchingAttributes the attributes to search for. If empty or 
     * null, all objects in the target context are returned.
     * @return a non-null enumeration of SearchResult objects. Each 
     * SearchResult contains the attributes identified by attributesToReturn 
     * and the name of the corresponding object, named relative to the 
     * context named by name.
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NamingEnumeration<SearchResult> search(String name,
            Attributes matchingAttributes) throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Searches in the named context or object for entries that satisfy the 
     * given search filter. Performs the search as specified by the search 
     * controls.
     * 
     * @param name the name of the context or object to search
     * @param filter the filter expression to use for the search; may not be 
     * null
     * @param cons the search controls that control the search. If null, 
     * the default search controls are used (equivalent to 
     * (new SearchControls())).
     * @return an enumeration of SearchResults of the objects that satisfy 
     * the filter; never null
     * @exception javax.naming.directory.InvalidSearchFilterException if the
     * search filter specified is not supported or understood by the underlying
     * directory
     * @exception javax.naming.directory.InvalidSearchControlsException if the
     * search controls contain invalid settings
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NamingEnumeration<SearchResult> search(String name, String filter, 
                                    SearchControls cons)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    /**
     * Searches in the named context or object for entries that satisfy the 
     * given search filter. Performs the search as specified by the search 
     * controls.
     * 
     * @param name the name of the context or object to search
     * @param filterExpr the filter expression to use for the search. 
     * The expression may contain variables of the form "{i}" where i is a 
     * nonnegative integer. May not be null.
     * @param filterArgs the array of arguments to substitute for the 
     * variables in filterExpr. The value of filterArgs[i] will replace each 
     * occurrence of "{i}". If null, equivalent to an empty array.
     * @param cons the search controls that control the search. If null, the 
     * default search controls are used (equivalent to (new SearchControls())).
     * @return an enumeration of SearchResults of the objects that satisfy the 
     * filter; never null
     * @exception ArrayIndexOutOfBoundsException if filterExpr contains {i} 
     * expressions where i is outside the bounds of the array filterArgs
     * @exception javax.naming.directory.InvalidSearchControlsException if cons
     * contains invalid settings
     * @exception javax.naming.directory.InvalidSearchFilterException if
     * filterExpr with filterArgs represents an invalid search filter
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NamingEnumeration<SearchResult> search(String name, String filterExpr, 
                                    Object[] filterArgs, SearchControls cons)
        throws NamingException {
        throw new OperationNotSupportedException();
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Normalize the name of an entry read from the Zip.
     */
    protected String normalize(ZipEntry entry) {

        String result = "/" + entry.getName();
        if (entry.isDirectory()) {
            result = result.substring(0, result.length() - 1);
        }
        return result;

    }


    /**
     * Constructs a tree of the entries contained in a WAR file.
     */
    protected void loadEntries() {

        try {

            Enumeration<? extends ZipEntry> entryList = base.entries();
            entries = new Entry("/", new ZipEntry("/"));
            
            while (entryList.hasMoreElements()) {
                
                ZipEntry entry = entryList.nextElement();
                String name = normalize(entry);
                int pos = name.lastIndexOf('/');
                // Check that parent entries exist and, if not, create them.
                // This fixes a bug for war files that don't record separate
                // zip entries for the directories.
                int currentPos = -1;
                int lastPos = 0;
                while ((currentPos = name.indexOf('/', lastPos)) != -1) {
                    Name parentName = getEscapedJndiName(name.substring(0, lastPos));
                    Name childName = getEscapedJndiName(name.substring(0, currentPos));
                    String entryName = name.substring(lastPos, currentPos);
                    // Parent should have been created in last cycle through
                    // this loop
                    Entry parent = treeLookup(parentName);
                    Entry child = treeLookup(childName);
                    if (child == null) {
                        // Create a new entry for missing entry and strip off
                        // the leading '/' character and appended on by the
                        // normalize method and add '/' character to end to
                        // signify that it is a directory entry
                        String zipName = name.substring(1, currentPos) + "/";
                        child = new Entry(entryName, new ZipEntry(zipName));
                        if (parent != null)
                            parent.addChild(child);
                    }
                    // Increment lastPos
                    lastPos = currentPos + 1;
                }
                String entryName = name.substring(pos + 1, name.length());
                Name compositeName = getEscapedJndiName(name.substring(0, pos));
                Entry parent = treeLookup(compositeName);
                Entry child = new Entry(entryName, entry);
                if (parent != null)
                    parent.addChild(child);
                
            }

        } catch (Exception e) {
            // Ignore
        }

    }


    /**
     * Entry tree lookup.
     */
    protected Entry treeLookup(Name name) {
        if (name.isEmpty() || entries == null)
            return entries;
        Entry currentEntry = entries;
        for (int i = 0; i < name.size(); i++) {
            if (name.get(i).length() == 0)
                continue;
            currentEntry = currentEntry.getChild(name.get(i));
            if (currentEntry == null)
                return null;
        }
        return currentEntry;
    }


    /**
     * List children as objects.
     */
    protected ArrayList<NamingEntry> list(Entry entry) {
        
        ArrayList<NamingEntry> entries = new ArrayList<NamingEntry>();
        Entry[] children = entry.getChildren();
        NamingEntry namingEntry = null;
        
        for (int i = 0; i < children.length; i++) {
            ZipEntry current = children[i].getEntry();
            Object object = null;
            if (current.isDirectory()) {
                object = new WARDirContext(base, children[i]);
            } else {
                object = new WARResource(current);
            }
            namingEntry = new NamingEntry
                (children[i].getName(), object, NamingEntry.ENTRY);
            entries.add(namingEntry);
        }
        
        return entries;
        
    }


    // ---------------------------------------------------- Entries Inner Class


    /**
     * Entries structure.
     */
    protected static class Entry implements Comparable<Object> {


        // -------------------------------------------------------- Constructor
        
        
        public Entry(String name, ZipEntry entry) {
            this.name = name;
            this.entry = entry;
        }
        
        
        // --------------------------------------------------- Member Variables
        
        
        protected String name = null;
        
        
        protected ZipEntry entry = null;
        
        
        protected Entry children[] = new Entry[0];
        
        
        protected volatile boolean childrenSorted = false;


        // ----------------------------------------------------- Public Methods
        
        
        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Entry))
                return (+1);
            return name.compareTo(((Entry) o).getName());
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Entry))
                return false;
            return name.equals(((Entry) o).getName());
        }
        
        @Override
        public int hashCode() {
            return name.hashCode();
        }

        public ZipEntry getEntry() {
            return entry;
        }
        
        
        public String getName() {
            return name;
        }
        
        
        public void addChild(Entry entry) {
            Entry[] newChildren = new Entry[children.length + 1];
            for (int i = 0; i < children.length; i++)
                newChildren[i] = children[i];
            newChildren[children.length] = entry;
            children = newChildren;
            childrenSorted = false;
        }


        public Entry[] getChildren() {
            if (!childrenSorted) {
                synchronized (children) {
                    if (!childrenSorted) {
                        Arrays.sort(children);
                        childrenSorted = true;
                    }
                }
            }
            return children;
        }


        public Entry getChild(String name) {
            for (int i = 0; i < children.length; i++) {
                if (children[i].name.equals(name)) {
                    return children[i];
                }
            }
            return null;
        }


    }


    // ------------------------------------------------ WARResource Inner Class


    /**
     * This specialized resource implementation avoids opening the IputStream
     * to the WAR right away.
     */
    protected class WARResource extends Resource {
        
        
        // -------------------------------------------------------- Constructor
        
        
        public WARResource(ZipEntry entry) {
            this.entry = entry;
        }
        
        
        // --------------------------------------------------- Member Variables
        
        
        protected ZipEntry entry;
        
        
        // ----------------------------------------------------- Public Methods
        
        
        /**
         * Content accessor.
         * 
         * @return InputStream
         */
        @Override
        public InputStream streamContent()
            throws IOException {
            try {
                if (binaryContent == null) {
                    InputStream is = base.getInputStream(entry);
                    inputStream = is;
                    return is;
                }
            } catch (ZipException e) {
                throw new IOException(e.getMessage(), e);
            }
            return super.streamContent();
        }
        
        
    }


}

