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
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.naming.NameParserImpl;
import org.apache.naming.NamingContextBindingsEnumeration;
import org.apache.naming.NamingContextEnumeration;
import org.apache.naming.NamingEntry;
import org.apache.naming.StringManager;

/**
 * Directory Context implementation helper class.
 *
 * @author Remy Maucherat
 */
public abstract class BaseDirContext implements DirContext {

    private static final org.apache.juli.logging.Log log=
        org.apache.juli.logging.LogFactory.getLog( BaseDirContext.class );

    // ----------------------------------------------------------- Constructors


    /**
     * Builds a base directory context.
     */
    public BaseDirContext() {
        this.env = new Hashtable<String,Object>();
    }


    /**
     * Builds a base directory context using the given environment.
     */
    public BaseDirContext(Hashtable<String,Object> env) {
        this.env = env;
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The document base path.
     */
    protected String docBase = null;


    /**
     * Environment.
     */
    protected Hashtable<String,Object> env;


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(Constants.Package);


    /**
     * Name parser for this context.
     */
    protected final NameParser nameParser = new NameParserImpl();


    /**
     * Cached.
     */
    protected boolean cached = true;


    /**
     * Cache TTL.
     */
    protected int cacheTTL = 5000; // 5s


    /**
     * Max size of cache for resources.
     */
    protected int cacheMaxSize = 10240; // 10 MB


    /**
     * Max size of resources that will be content cached.
     */
    protected int cacheObjectMaxSize = 512; // 512 K


    /**
     * Aliases allow content to be included from other locations.
     */
    protected Map<String,BaseDirContext> aliases =
        new HashMap<String,BaseDirContext>();


    /**
     * Alternate / backup DirContexts for static resources. These will be
     * searched in the order they are added if the requested resource cannot be
     * found in the primary DirContext. 
     */
    protected List<DirContext> altDirContexts = new ArrayList<DirContext>();
    
    
    // ------------------------------------------------------------- Properties


    /**
     * Add a resources JAR. The contents of /META-INF/resources/ will be used if
     * a requested resource can not be found in the main context.
     */
    public void addResourcesJar(URL url) {
        try {
            JarURLConnection conn = (JarURLConnection) url.openConnection();
            JarFile jarFile = conn.getJarFile();   
            ZipEntry entry = jarFile.getEntry("/");
            WARDirContext warDirContext = new WARDirContext(jarFile,
                    new WARDirContext.Entry("/", entry));
            warDirContext.loadEntries();
            altDirContexts.add(warDirContext);
        } catch (IOException ioe) {
            log.warn(sm.getString("resources.addResourcesJarFail", url), ioe);
        }
    }
    

    /**
     * Add an alternative DirContext (must contain META-INF/resources) directly.
     */
    public void addAltDirContext(DirContext altDirContext) {
        altDirContexts.add(altDirContext);
    }


    /**
     * Add an alias.
     */
    public void addAlias(String path, BaseDirContext dirContext) {
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException(
                    sm.getString("resources.invalidAliasPath", path));
        }
        aliases.put(path, dirContext);
    }

    
    /**
     * Remove an alias.
     */
    public void removeAlias(String path) {
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException(
                    sm.getString("resources.invalidAliasPath", path));
        }
        aliases.remove(path);
    }
    
    
    /**
     * Get the current alias configuration in String form. If no aliases are
     * configured, an empty string will be returned.
     */
    public String getAliases() {
        StringBuilder result = new StringBuilder();
        Iterator<Entry<String,BaseDirContext>> iter =
            aliases.entrySet().iterator();
        boolean first = true;
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                result.append(',');
            }
            Entry<String,BaseDirContext> entry = iter.next();
            result.append(entry.getKey());
            result.append('=');
            result.append(entry.getValue().getDocBase());
        }
        return result.toString();
    }

    
    /**
     * Set the current alias configuration from a String. The String should be
     * of the form "/aliasPath1=docBase1,/aliasPath2=docBase2" where aliasPathN
     * must include a leading '/' and docBaseN must be an absolute path to
     * either a .war file or a directory. Any call to this method will replace
     * the current set of aliases.
     */
    public void setAliases(String theAliases) {
        // Overwrite whatever is currently set
        aliases.clear();
        
        if (theAliases == null || theAliases.length() == 0)
            return;
        
        String[] kvps = theAliases.split(",");
        for (String kvp : kvps) {
            // Skip blanks introduced by regexp split and/or poor input
            kvp = kvp.trim();
            if(0 == kvp.length())
                continue;

            String[] kv = kvp.split("=");
            if (kv.length != 2)
                throw new IllegalArgumentException(
                        sm.getString("resources.invalidAliasMapping", kvp));

            // Trim whitespace from key and value
            kv[0] = kv[0].trim();
            kv[1] = kv[1].trim();

            if(kv[0].length() == 0 || kv[1].length() == 0)
                throw new IllegalArgumentException(
                        sm.getString("resources.invalidAliasMapping", kvp));

            if (kv[0].equals("/")) {
                throw new IllegalArgumentException(
                        sm.getString("resources.invalidAliasNotAllowed", kv[0]));
            }
            File aliasLoc = new File(kv[1]);
            if (!aliasLoc.exists()) {
                throw new IllegalArgumentException(
                        sm.getString("resources.invalidAliasNotExist", kv[1]));
            }
            BaseDirContext context;
            if (kv[1].endsWith(".war") && !(aliasLoc.isDirectory())) {
                context = new WARDirContext();
            } else if (aliasLoc.isDirectory()) {
                context = new FileDirContext();
            } else {
                throw new IllegalArgumentException(
                        sm.getString("resources.invalidAliasFile", kv[1]));
            }
            context.setDocBase(kv[1]);
            addAlias(kv[0], context);
        }
    }

    
    /**
     * Return the document root for this component.
     */
    public String getDocBase() {
        return (this.docBase);
    }


    /**
     * Set the document root for this component.
     *
     * @param docBase The new document root
     *
     * @exception IllegalArgumentException if the specified value is not
     *  supported by this implementation
     * @exception IllegalArgumentException if this would create a
     *  malformed URL
     */
    public void setDocBase(String docBase) {

        // Validate the format of the proposed document root
        if (docBase == null)
            throw new IllegalArgumentException
                (sm.getString("resources.null"));

        // Change the document root property
        this.docBase = docBase;

    }


    /**
     * Set cached.
     */
    public void setCached(boolean cached) {
        this.cached = cached;
    }


    /**
     * Is cached ?
     */
    public boolean isCached() {
        return cached;
    }


    /**
     * Set cache TTL.
     */
    public void setCacheTTL(int cacheTTL) {
        this.cacheTTL = cacheTTL;
    }


    /**
     * Get cache TTL.
     */
    public int getCacheTTL() {
        return cacheTTL;
    }


    /**
     * Return the maximum size of the cache in KB.
     */
    public int getCacheMaxSize() {
        return cacheMaxSize;
    }


    /**
     * Set the maximum size of the cache in KB.
     */
    public void setCacheMaxSize(int cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }


    /**
     * Return the maximum size of objects to be cached in KB.
     */
    public int getCacheObjectMaxSize() {
        return cacheObjectMaxSize;
    }


    /**
     * Set the maximum size of objects to be placed the cache in KB.
     */
    public void setCacheObjectMaxSize(int cacheObjectMaxSize) {
        this.cacheObjectMaxSize = cacheObjectMaxSize;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Allocate resources for this directory context.
     */
    public void allocate() {
        // No action taken by the default implementation
    }


    /**
     * Release any resources allocated for this directory context.
     */
    public void release() {
        for(BaseDirContext bcontext: this.aliases.values()) {
            bcontext.release();
        }
        this.aliases.clear();
        for(DirContext dcontext: this.altDirContexts) {
            if(dcontext instanceof BaseDirContext) {
                ((BaseDirContext)dcontext).release();
            }
        }
        this.altDirContexts.clear();        
    }

    
    /**
     * Return the real path for a given virtual path, if possible; otherwise
     * return <code>null</code>.
     *
     * @param name The path to the desired resource
     */
    public String getRealPath(String name) {
        if (!aliases.isEmpty()) {
            AliasResult result = findAlias(name);
            if (result.dirContext != null) {
                return result.dirContext.doGetRealPath(result.aliasName);
            }
        }
        
        // Next do a standard getRealPath()
        String path = doGetRealPath(name);

        if (path != null)
            return path;

        String resourceName = "/META-INF/resources" + name;
        // Check the alternate locations
        for (DirContext altDirContext : altDirContexts) {
            if (altDirContext instanceof BaseDirContext){
                path = ((BaseDirContext) altDirContext).getRealPath(resourceName);
                if (path != null)
                    return path;
            }
        }
        
        // Really not found
        return null; 
    }

    // -------------------------------------------------------- Context Methods


    /**
     * Retrieves the named object. If name is empty, returns a new instance 
     * of this context (which represents the same naming context as this 
     * context, but its environment may be modified independently and it may 
     * be accessed concurrently).
     * 
     * @param name the name of the object to look up
     * @return the object bound to name
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public final Object lookup(Name name)
        throws NamingException {
        return lookup(name.toString());
    }


    /**
     * Retrieves the named object.
     * 
     * @param name
     *            the name of the object to look up
     * @return the object bound to name
     * @exception NamingException
     *                if a naming exception is encountered
     */
    @Override
    public final Object lookup(String name) throws NamingException {
        // First check for aliases
        Object obj = doLookupWithoutNNFE(name);
        if (obj != null) {
            return obj;
        }

        // Really not found
        throw new NameNotFoundException(
                sm.getString("resources.notFound", name));
    }

    private Object doLookupWithoutNNFE(String name) throws NamingException {
        if (!aliases.isEmpty()) {
            AliasResult result = findAlias(name);
            if (result.dirContext != null) {
                return result.dirContext.lookup(result.aliasName);
            }
        }

        // Next do a standard lookup
        Object obj = doLookup(name);

        if (obj != null) {
            return obj;
        }

        // Class files may not be loaded from the alternate locations so don't
        // waste cycles looking.
        if (name.endsWith(".class")) {
            return null;
        }
        
        // Check the alternate locations (Resource JARs)
        String resourceName = "/META-INF/resources" + name;
        for (DirContext altDirContext : altDirContexts) {
            if (altDirContext instanceof BaseDirContext) {
                obj = ((BaseDirContext) altDirContext)
                        .doLookupWithoutNNFE(resourceName);
            } else {
                try {
                    obj = altDirContext.lookup(resourceName);
                } catch (NamingException ex) {
                    // ignore
                }
            }
            if (obj != null) {
                return obj;
            }
        }

        // Return null instead
        return null;
    }

    /**
     * Binds a name to an object. All intermediate contexts and the target 
     * context (that named by all but terminal atomic component of the name) 
     * must already exist.
     * 
     * @param name the name to bind; may not be empty
     * @param obj the object to bind; possibly null
     * @exception javax.naming.NameAlreadyBoundException if name is already
     * bound
     * @exception javax.naming.directory.InvalidAttributesException if object
     * did not supply all mandatory attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void bind(Name name, Object obj)
        throws NamingException {
        bind(name.toString(), obj);
    }


    /**
     * Binds a name to an object.
     * 
     * @param name the name to bind; may not be empty
     * @param obj the object to bind; possibly null
     * @exception javax.naming.NameAlreadyBoundException if name is already
     * bound
     * @exception javax.naming.directory.InvalidAttributesException if object
     * did not supply all mandatory attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void bind(String name, Object obj)
        throws NamingException {
        bind(name, obj, null);
    }


    /**
     * Binds a name to an object, overwriting any existing binding. All 
     * intermediate contexts and the target context (that named by all but 
     * terminal atomic component of the name) must already exist.
     * <p>
     * If the object is a DirContext, any existing attributes associated with 
     * the name are replaced with those of the object. Otherwise, any 
     * existing attributes associated with the name remain unchanged.
     * 
     * @param name the name to bind; may not be empty
     * @param obj the object to bind; possibly null
     * @exception javax.naming.directory.InvalidAttributesException if object
     * did not supply all mandatory attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void rebind(Name name, Object obj)
        throws NamingException {
        rebind(name.toString(), obj);
    }


    /**
     * Binds a name to an object, overwriting any existing binding.
     * 
     * @param name the name to bind; may not be empty
     * @param obj the object to bind; possibly null
     * @exception javax.naming.directory.InvalidAttributesException if object
     * did not supply all mandatory attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void rebind(String name, Object obj)
        throws NamingException {
        rebind(name, obj, null);
    }


    /**
     * Unbinds the named object. Removes the terminal atomic name in name 
     * from the target context--that named by all but the terminal atomic 
     * part of name.
     * <p>
     * This method is idempotent. It succeeds even if the terminal atomic 
     * name is not bound in the target context, but throws 
     * NameNotFoundException if any of the intermediate contexts do not exist. 
     * 
     * @param name the name to bind; may not be empty
     * @exception NameNotFoundException if an intermediate context does not 
     * exist
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void unbind(Name name)
        throws NamingException {
        unbind(name.toString());
    }


    /**
     * Unbinds the named object.
     * 
     * @param name the name to bind; may not be empty
     * @exception NameNotFoundException if an intermediate context does not 
     * exist
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract void unbind(String name)
        throws NamingException;


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
    public void rename(Name oldName, Name newName)
        throws NamingException {
        rename(oldName.toString(), newName.toString());
    }


    /**
     * Binds a new name to the object bound to an old name, and unbinds the 
     * old name.
     * 
     * @param oldName the name of the existing binding; may not be empty
     * @param newName the name of the new binding; may not be empty
     * @exception javax.naming.NameAlreadyBoundException if newName is already
     * bound
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract void rename(String oldName, String newName)
        throws NamingException;


    /**
     * Enumerates the names bound in the named context, along with the class 
     * names of objects bound to them. The contents of any subcontexts are 
     * not included.
     * <p>
     * If a binding is added to or removed from this context, its effect on 
     * an enumeration previously returned is undefined.
     * 
     * @param name the name of the context to list
     * @return an enumeration of the names and class names of the bindings in 
     * this context. Each element of the enumeration is of type NameClassPair.
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NamingEnumeration<NameClassPair> list(Name name)
        throws NamingException {
        return list(name.toString());
    }


    /**
     * Enumerates the names bound in the named context, along with the class 
     * names of objects bound to them.
     * 
     * @param name the name of the context to list
     * @return an enumeration of the names and class names of the bindings in 
     * this context. Each element of the enumeration is of type NameClassPair.
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NamingEnumeration<NameClassPair> list(String name)
        throws NamingException {

        if (!aliases.isEmpty()) {
            AliasResult result = findAlias(name);
            if (result.dirContext != null) {
                return result.dirContext.list(result.aliasName);
            }
        }

        // Next do a standard lookup
        List<NamingEntry> bindings = doListBindings(name);

        // Check the alternate locations
        List<NamingEntry> altBindings = null;

        String resourceName = "/META-INF/resources" + name;
        for (DirContext altDirContext : altDirContexts) {
            if (altDirContext instanceof BaseDirContext) {
                altBindings = ((BaseDirContext) altDirContext).doListBindings(resourceName);
            }
            if (altBindings != null) {
                if (bindings == null) {
                    bindings = altBindings;
                } else {
                    bindings.addAll(altBindings);
                }
            }
        }

        if (bindings != null) {
            return new NamingContextEnumeration(bindings.iterator());
        }

        // Really not found
        throw new NameNotFoundException(
                sm.getString("resources.notFound", name));
    }


    /**
     * Enumerates the names bound in the named context, along with the 
     * objects bound to them. The contents of any subcontexts are not 
     * included.
     * <p>
     * If a binding is added to or removed from this context, its effect on 
     * an enumeration previously returned is undefined.
     * 
     * @param name the name of the context to list
     * @return an enumeration of the bindings in this context. 
     * Each element of the enumeration is of type Binding.
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public final NamingEnumeration<Binding> listBindings(Name name)
        throws NamingException {
        return listBindings(name.toString());
    }


    /**
     * Enumerates the names bound in the named context, along with the 
     * objects bound to them.
     * 
     * @param name the name of the context to list
     * @return an enumeration of the bindings in this context. 
     * Each element of the enumeration is of type Binding.
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public final NamingEnumeration<Binding> listBindings(String name)
        throws NamingException {
        if (!aliases.isEmpty()) {
            AliasResult result = findAlias(name);
            if (result.dirContext != null) {
                return result.dirContext.listBindings(result.aliasName);
            }
        }
        
        // Next do a standard lookup
        List<NamingEntry> bindings = doListBindings(name);

        // Check the alternate locations
        List<NamingEntry> altBindings = null;

        String resourceName = "/META-INF/resources" + name;
        for (DirContext altDirContext : altDirContexts) {
            if (altDirContext instanceof BaseDirContext) {
                altBindings = ((BaseDirContext) altDirContext).doListBindings(resourceName);
            }
            if (altBindings != null) {
                if (bindings == null) {
                    bindings = altBindings;
                } else {
                    bindings.addAll(altBindings);
                }
            }
        }

        if (bindings != null) {
            return new NamingContextBindingsEnumeration(bindings.iterator(),
                    this);
        }

        // Really not found
        throw new NameNotFoundException(
                sm.getString("resources.notFound", name));
    }


    /**
     * Destroys the named context and removes it from the namespace. Any 
     * attributes associated with the name are also removed. Intermediate 
     * contexts are not destroyed.
     * <p>
     * This method is idempotent. It succeeds even if the terminal atomic 
     * name is not bound in the target context, but throws 
     * NameNotFoundException if any of the intermediate contexts do not exist. 
     *
     * In a federated naming system, a context from one naming system may be 
     * bound to a name in another. One can subsequently look up and perform 
     * operations on the foreign context using a composite name. However, an 
     * attempt destroy the context using this composite name will fail with 
     * NotContextException, because the foreign context is not a "subcontext" 
     * of the context in which it is bound. Instead, use unbind() to remove 
     * the binding of the foreign context. Destroying the foreign context 
     * requires that the destroySubcontext() be performed on a context from 
     * the foreign context's "native" naming system.
     * 
     * @param name the name of the context to be destroyed; may not be empty
     * @exception NameNotFoundException if an intermediate context does not 
     * exist
     * @exception javax.naming.NotContextException if the name is bound but does
     * not name a context, or does not name a context of the appropriate type
     */
    @Override
    public void destroySubcontext(Name name)
        throws NamingException {
        destroySubcontext(name.toString());
    }


    /**
     * Destroys the named context and removes it from the namespace.
     * 
     * @param name the name of the context to be destroyed; may not be empty
     * @exception NameNotFoundException if an intermediate context does not 
     * exist
     * @exception javax.naming.NotContextException if the name is bound but does
     * not name a context, or does not name a context of the appropriate type
     */
    @Override
    public abstract void destroySubcontext(String name)
        throws NamingException;


    /**
     * Creates and binds a new context. Creates a new context with the given 
     * name and binds it in the target context (that named by all but 
     * terminal atomic component of the name). All intermediate contexts and 
     * the target context must already exist.
     * 
     * @param name the name of the context to create; may not be empty
     * @return the newly created context
     * @exception javax.naming.NameAlreadyBoundException if name is already
     * bound
     * @exception javax.naming.directory.InvalidAttributesException if creation
     * of the sub-context requires specification of mandatory attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Context createSubcontext(Name name)
        throws NamingException {
        return createSubcontext(name.toString());
    }


    /**
     * Creates and binds a new context.
     * 
     * @param name the name of the context to create; may not be empty
     * @return the newly created context
     * @exception javax.naming.NameAlreadyBoundException if name is already
     * bound
     * @exception javax.naming.directory.InvalidAttributesException if creation
     * of the sub-context requires specification of mandatory attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Context createSubcontext(String name)
        throws NamingException {
        return createSubcontext(name, null);
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
    public Object lookupLink(Name name)
        throws NamingException {
        return lookupLink(name.toString());
    }


    /**
     * Retrieves the named object, following links except for the terminal 
     * atomic component of the name.
     * 
     * @param name the name of the object to look up
     * @return the object bound to name, not following the terminal link 
     * (if any).
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract Object lookupLink(String name)
        throws NamingException;


    /**
     * Retrieves the parser associated with the named context. In a 
     * federation of namespaces, different naming systems will parse names 
     * differently. This method allows an application to get a parser for 
     * parsing names into their atomic components using the naming convention 
     * of a particular naming system. Within any single naming system, 
     * NameParser objects returned by this method must be equal (using the 
     * equals() test).
     * 
     * @param name the name of the context from which to get the parser
     * @return a name parser that can parse compound names into their atomic 
     * components
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NameParser getNameParser(Name name)
        throws NamingException {
        return new NameParserImpl();
    }


    /**
     * Retrieves the parser associated with the named context.
     * 
     * @param name the name of the context from which to get the parser
     * @return a name parser that can parse compound names into their atomic 
     * components
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NameParser getNameParser(String name)
        throws NamingException {
        return new NameParserImpl();
    }


    /**
     * Composes the name of this context with a name relative to this context.
     * <p>
     * Given a name (name) relative to this context, and the name (prefix) 
     * of this context relative to one of its ancestors, this method returns 
     * the composition of the two names using the syntax appropriate for the 
     * naming system(s) involved. That is, if name names an object relative 
     * to this context, the result is the name of the same object, but 
     * relative to the ancestor context. None of the names may be null.
     * 
     * @param name a name relative to this context
     * @param prefix the name of this context relative to one of its ancestors
     * @return the composition of prefix and name
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Name composeName(Name name, Name prefix)
        throws NamingException {
        Name clone = (Name) prefix.clone();
        return clone.addAll(name);
    }


    /**
     * Composes the name of this context with a name relative to this context.
     * 
     * @param name a name relative to this context
     * @param prefix the name of this context relative to one of its ancestors
     * @return the composition of prefix and name
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public String composeName(String name, String prefix)
        throws NamingException {
        return prefix + "/" + name;
    }


    /**
     * Adds a new environment property to the environment of this context. If 
     * the property already exists, its value is overwritten.
     * 
     * @param propName the name of the environment property to add; may not 
     * be null
     * @param propVal the value of the property to add; may not be null
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Object addToEnvironment(String propName, Object propVal)
        throws NamingException {
        return env.put(propName, propVal);
    }


    /**
     * Removes an environment property from the environment of this context. 
     * 
     * @param propName the name of the environment property to remove; 
     * may not be null
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Object removeFromEnvironment(String propName)
        throws NamingException {
        return env.remove(propName);
    }


    /**
     * Retrieves the environment in effect for this context. See class 
     * description for more details on environment properties. 
     * The caller should not make any changes to the object returned: their 
     * effect on the context is undefined. The environment of this context 
     * may be changed using addToEnvironment() and removeFromEnvironment().
     * 
     * @return the environment of this context; never null
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Hashtable<String,Object> getEnvironment()
        throws NamingException {
        return env;
    }


    /**
     * Closes this context. This method releases this context's resources 
     * immediately, instead of waiting for them to be released automatically 
     * by the garbage collector.
     * This method is idempotent: invoking it on a context that has already 
     * been closed has no effect. Invoking any other method on a closed 
     * context is not allowed, and results in undefined behaviour.
     * 
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void close()
        throws NamingException {
        env.clear();
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
     * @exception javax.naming.OperationNotSupportedException if the naming
     * system does not have the notion of a full name
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract String getNameInNamespace()
        throws NamingException;


    // ----------------------------------------------------- DirContext Methods


    /**
     * Retrieves all of the attributes associated with a named object. 
     * 
     * @return the set of attributes associated with name. 
     * Returns an empty attribute set if name has no attributes; never null.
     * @param name the name of the object from which to retrieve attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Attributes getAttributes(Name name)
        throws NamingException {
        return getAttributes(name.toString());
    }


    /**
     * Retrieves all of the attributes associated with a named object.
     * 
     * @return the set of attributes associated with name
     * @param name the name of the object from which to retrieve attributes
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public Attributes getAttributes(String name)
        throws NamingException {
        return getAttributes(name, null);
    }


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
    public Attributes getAttributes(Name name, String[] attrIds)
        throws NamingException {
        return getAttributes(name.toString(), attrIds);
    }
    
    
    /**
     * Retrieves selected attributes associated with a named object.
     * 
     * @return the requested attributes; never null
     * @param name the name of the object from which to retrieve attributes
     * @param attrIds the identifiers of the attributes to retrieve. null 
     * indicates that all attributes should be retrieved; an empty array 
     * indicates that none should be retrieved
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public final Attributes getAttributes(String name, String[] attrIds)
        throws NamingException {
        
        // First check for aliases
        if (!aliases.isEmpty()) {
            AliasResult result = findAlias(name);
            if (result.dirContext != null) {
                return result.dirContext.getAttributes(
                        result.aliasName, attrIds);
            }
        }
        
        // Next do a standard lookup
        Attributes attrs = doGetAttributes(name, attrIds);

        if (attrs != null)
            return attrs;

        String resourceName = "/META-INF/resources" + name;
        // Check the alternate locations
        for (DirContext altDirContext : altDirContexts) {
            if (altDirContext instanceof BaseDirContext)
                attrs = ((BaseDirContext) altDirContext).doGetAttributes(resourceName, attrIds);
            else {
                try {
                    attrs = altDirContext.getAttributes(name, attrIds);
                } catch (NamingException ne) {
                    // Ignore
                }
            }
            if (attrs != null)
                return attrs;
        }
        
        // Really not found
        throw new NameNotFoundException(
                sm.getString("resources.notFound", name));
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
    public void modifyAttributes(Name name, int mod_op, Attributes attrs)
        throws NamingException {
        modifyAttributes(name.toString(), mod_op, attrs);
    }


    /**
     * Modifies the attributes associated with a named object.
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
    public abstract void modifyAttributes
        (String name, int mod_op, Attributes attrs)
        throws NamingException;


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
    public void modifyAttributes(Name name, ModificationItem[] mods)
        throws NamingException {
        modifyAttributes(name.toString(), mods);
    }


    /**
     * Modifies the attributes associated with a named object using an an 
     * ordered list of modifications.
     * 
     * @param name the name of the object whose attributes will be updated
     * @param mods an ordered sequence of modifications to be performed; may 
     * not be null
     * @exception javax.naming.directory.AttributeModificationException if the
     * modification cannot be completed successfully
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract void modifyAttributes(String name, ModificationItem[] mods)
        throws NamingException;


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
    public void bind(Name name, Object obj, Attributes attrs)
        throws NamingException {
        bind(name.toString(), obj, attrs);
    }


    /**
     * Binds a name to an object, along with associated attributes.
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
    public abstract void bind(String name, Object obj, Attributes attrs)
        throws NamingException;


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
    public void rebind(Name name, Object obj, Attributes attrs)
        throws NamingException {
        rebind(name.toString(), obj, attrs);
    }


    /**
     * Binds a name to an object, along with associated attributes, 
     * overwriting any existing binding.
     * 
     * @param name the name to bind; may not be empty
     * @param obj the object to bind; possibly null
     * @param attrs the attributes to associate with the binding
     * @exception javax.naming.directory.InvalidAttributesException if some
     * "mandatory" attributes of the binding are not supplied
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract void rebind(String name, Object obj, Attributes attrs)
        throws NamingException;


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
    public DirContext createSubcontext(Name name, Attributes attrs)
        throws NamingException {
        return createSubcontext(name.toString(), attrs);
    }


    /**
     * Creates and binds a new context, along with associated attributes.
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
    public abstract DirContext createSubcontext(String name, Attributes attrs)
        throws NamingException;


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
     * @exception javax.naming.OperationNotSupportedException if schema not
     * supported
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public DirContext getSchema(Name name)
        throws NamingException {
        return getSchema(name.toString());
    }


    /**
     * Retrieves the schema associated with the named object.
     * 
     * @param name the name of the object whose schema is to be retrieved
     * @return the schema associated with the context; never null
     * @exception javax.naming.OperationNotSupportedException if schema not
     * supported
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract DirContext getSchema(String name)
        throws NamingException;


    /**
     * Retrieves a context containing the schema objects of the named 
     * object's class definitions.
     * 
     * @param name the name of the object whose object class definition is to 
     * be retrieved
     * @return the DirContext containing the named object's class 
     * definitions; never null
     * @exception javax.naming.OperationNotSupportedException if schema not
     * supported
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public DirContext getSchemaClassDefinition(Name name)
        throws NamingException {
        return getSchemaClassDefinition(name.toString());
    }


    /**
     * Retrieves a context containing the schema objects of the named 
     * object's class definitions.
     * 
     * @param name the name of the object whose object class definition is to 
     * be retrieved
     * @return the DirContext containing the named object's class 
     * definitions; never null
     * @exception javax.naming.OperationNotSupportedException if schema not
     * supported
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract DirContext getSchemaClassDefinition(String name)
        throws NamingException;


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
    public NamingEnumeration<SearchResult> search(Name name,
            Attributes matchingAttributes, String[] attributesToReturn)
        throws NamingException {
        return search(name.toString(), matchingAttributes, attributesToReturn);
    }


    /**
     * Searches in a single context for objects that contain a specified set 
     * of attributes, and retrieves selected attributes.
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
    public abstract NamingEnumeration<SearchResult> search
        (String name, Attributes matchingAttributes,
         String[] attributesToReturn)
        throws NamingException;


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
    public NamingEnumeration<SearchResult> search(Name name,
            Attributes matchingAttributes) throws NamingException {
        return search(name.toString(), matchingAttributes);
    }


    /**
     * Searches in a single context for objects that contain a specified set 
     * of attributes.
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
    public abstract NamingEnumeration<SearchResult> search
        (String name, Attributes matchingAttributes)
        throws NamingException;


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
    public NamingEnumeration<SearchResult> search
        (Name name, String filter, SearchControls cons)
        throws NamingException {
        return search(name.toString(), filter, cons);
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
    public abstract NamingEnumeration<SearchResult> search(String name,
            String filter, SearchControls cons)
        throws NamingException;


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
     * @return an enumeration of SearchResults of the objects that satisy the 
     * filter; never null
     * @exception ArrayIndexOutOfBoundsException if filterExpr contains {i} 
     * expressions where i is outside the bounds of the array filterArgs
     * @exception javax.naming.directory.InvalidSearchControlsException if the
     * search controls contain invalid settings
     * @exception javax.naming.directory.InvalidSearchFilterException if
     * filterExpr with filterArgs represents an invalid search filter
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public NamingEnumeration<SearchResult> search(Name name, String filterExpr, 
                                    Object[] filterArgs, SearchControls cons)
        throws NamingException {
        return search(name.toString(), filterExpr, filterArgs, cons);
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
     * @exception javax.naming.directory.InvalidSearchControlsException if the
     * search controls contain invalid settings
     * @exception javax.naming.directory.InvalidSearchFilterException if
     * filterExpr with filterArgs represents an invalid search filter
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public abstract NamingEnumeration<SearchResult> search
        (String name, String filterExpr, 
         Object[] filterArgs, SearchControls cons)
        throws NamingException;


    // ------------------------------------------------------ Protected Methods

    protected abstract Attributes doGetAttributes(String name, String[] attrIds)
        throws NamingException;

    protected abstract Object doLookup(String name);

    protected abstract List<NamingEntry> doListBindings(String name)
        throws NamingException;

    protected abstract String doGetRealPath(String name);

    // -------------------------------------------------------- Private Methods
    private AliasResult findAlias(String name) {
        AliasResult result = new AliasResult();
        
        // The JNDI resources code mixes names (that don't have leading '/') and
        // paths (which do have leading '/'). This code is a little messy but
        // the alternative is reviewing the entire o.a.n.resources package for
        // the correct use of name vs path and that is very likely to break
        // clients (including Tomcat internals and user applications).
        String searchName;
        boolean slashAppended;
        if (name.length() == 0) {
            searchName = "/";
            slashAppended = true;
        } else if (name.charAt(0) == '/') {
            searchName = name;
            slashAppended = false;
        } else {
            searchName = "/" + name;
            slashAppended = true;
        }
        
        result.dirContext = aliases.get(searchName);
        while (result.dirContext == null) {
            int slash = searchName.lastIndexOf('/');
            if (slash < 0)
                break;
            searchName = searchName.substring(0, slash);
            result.dirContext = aliases.get(searchName);
        }
        
        if (result.dirContext != null) {
            if (slashAppended) {
                result.aliasName = name.substring(searchName.length() - 1);
            } else {
                result.aliasName = name.substring(searchName.length());
            }
        }
        
        return result;
    }

    private static class AliasResult {
        BaseDirContext dirContext;
        String aliasName;
    }
}
