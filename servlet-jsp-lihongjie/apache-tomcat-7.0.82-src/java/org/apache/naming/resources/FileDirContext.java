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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.naming.NamingEntry;
import org.apache.tomcat.util.http.RequestUtil;

/**
 * Filesystem Directory Context implementation helper class.
 *
 * @author Remy Maucherat
 */
public class FileDirContext extends BaseDirContext {

    private static final org.apache.juli.logging.Log log=
        org.apache.juli.logging.LogFactory.getLog( FileDirContext.class );

    // -------------------------------------------------------------- Constants


    /**
     * The descriptive information string for this implementation.
     */
    protected static final int BUFFER_SIZE = 2048;


    // ----------------------------------------------------------- Constructors


    /**
     * Builds a file directory context using the given environment.
     */
    public FileDirContext() {
        super();
    }


    /**
     * Builds a file directory context using the given environment.
     *
     * @param env The environment with which to build the context
     */
    public FileDirContext(Hashtable<String,Object> env) {
        super(env);
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The document base directory.
     */
    protected File base = null;


    /**
     * Absolute normalized filename of the base.
     */
    protected String absoluteBase = null;

    private String canonicalBase = null;


    /**
     * Allow linking.
     */
    protected boolean allowLinking = false;


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
        if (docBase == null) {
            throw new IllegalArgumentException(sm.getString("resources.null"));
        }

        // Calculate a File object referencing this document base directory
        base = new File(docBase);
        try {
            base = base.getCanonicalFile();
        } catch (IOException e) {
            // Ignore
        }

        // Validate that the document base is an existing directory
        if (!base.exists() || !base.isDirectory() || !base.canRead()) {
            throw new IllegalArgumentException(sm.getString("fileResources.base", docBase));
        }

        this.absoluteBase = normalize(base.getAbsolutePath());

        // absoluteBase also needs to be normalized. Using the canonical path is
        // the simplest way of doing this.
        try {
            this.canonicalBase = base.getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        super.setDocBase(docBase);
    }


    /**
     * Set allow linking.
     *
     * @param allowLinking The new value for the attribute
     */
    public void setAllowLinking(boolean allowLinking) {
        this.allowLinking = allowLinking;
    }


    /**
     * Is linking allowed.
     *
     * @return {@code true} is linking is allowed, otherwise {@code false}
     */
    public boolean getAllowLinking() {
        return allowLinking;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Release any resources allocated for this directory context.
     */
    @Override
    public void release() {
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
        File file = new File(getDocBase(), path);
        return file.getAbsolutePath();
    }

    // -------------------------------------------------------- Context Methods


    /**
     * Retrieves the named object.
     *
     * @param name the name of the object to look up
     * @return the object bound to name
     */
    @Override
    protected Object doLookup(String name) {
        Object result = null;
        File file = file(name, true);

        if (file == null)
            return null;

        if (file.isDirectory()) {
            FileDirContext tempContext = new FileDirContext(env);
            tempContext.setDocBase(file.getPath());
            tempContext.setAllowLinking(getAllowLinking());
            result = tempContext;
        } else {
            result = new FileResource(file);
        }

        return result;

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
    public void unbind(String name)
        throws NamingException {

        File file = file(name, true);

        if (file == null)
            throw new NameNotFoundException(
                    sm.getString("resources.notFound", name));

        if (!file.delete())
            throw new NamingException
                (sm.getString("resources.unbindFailed", name));

    }


    /**
     * Binds a new name to the object bound to an old name, and unbinds the
     * old name. Both names are relative to this context. Any attributes
     * associated with the old name become associated with the new name.
     * Intermediate contexts of the old name are not changed.
     *
     * @param oldName the name of the existing binding; may not be empty
     * @param newName the name of the new binding; may not be empty
     * @exception NameAlreadyBoundException if newName is already bound
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void rename(String oldName, String newName) throws NamingException {

        File file = file(oldName, true);

        if (file == null) {
            throw new NameNotFoundException(sm.getString("resources.notFound", oldName));
        }

        File newFile = file(newName, false);
        if (newFile == null) {
            throw new NamingException(sm.getString("resources.renameFail", oldName, newName));
        }

        if (!file.renameTo(newFile)) {
            throw new NamingException(sm.getString("resources.renameFail", oldName, newName));
        }
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
    protected List<NamingEntry> doListBindings(String name)
        throws NamingException {

        File file = file(name, true);

        if (file == null)
            return null;

        return list(file);

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
    public void destroySubcontext(String name)
        throws NamingException {
        unbind(name);
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

        // Building attribute list
        File file = file(name, true);

        if (file == null)
            return null;

        return new FileResourceAttributes(file);

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
     * @exception NameAlreadyBoundException if name is already bound
     * @exception javax.naming.directory.InvalidAttributesException if some
     * "mandatory" attributes of the binding are not supplied
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public void bind(String name, Object obj, Attributes attrs) throws NamingException {

        // Note: No custom attributes allowed

        // bind() is meant to create a file so ensure that the path doesn't end
        // in '/'
        if (name.endsWith("/")) {
            throw new NamingException(sm.getString("resources.bindFailed", name));
        }

        File file = file(name, false);
        if (file == null) {
            throw new NamingException(sm.getString("resources.bindFailed", name));
        }
        if (file.exists())
            throw new NameAlreadyBoundException
                (sm.getString("resources.alreadyBound", name));

        rebind(name, obj, attrs);

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

        // Note: No custom attributes allowed
        // Check obj type

        File file = file(name, false);
        if (file == null) {
            throw new NamingException(sm.getString("resources.bindFailed", name));
        }

        InputStream is = null;
        if (obj instanceof Resource) {
            try {
                is = ((Resource) obj).streamContent();
            } catch (IOException e) {
                // Ignore
            }
        } else if (obj instanceof InputStream) {
            is = (InputStream) obj;
        } else if (obj instanceof DirContext) {
            if (file.exists()) {
                if (!file.delete())
                    throw new NamingException
                        (sm.getString("resources.bindFailed", name));
            }
            if (!file.mkdir())
                throw new NamingException
                    (sm.getString("resources.bindFailed", name));
        }
        if (is == null)
            throw new NamingException
                (sm.getString("resources.bindFailed", name));

        // Open os

        FileOutputStream os = null;
        byte buffer[] = new byte[BUFFER_SIZE];
        int len = -1;
        try {
            os = new FileOutputStream(file);
            while (true) {
                len = is.read(buffer);
                if (len == -1)
                    break;
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            NamingException ne = new NamingException
                    (sm.getString("resources.bindFailed", e));
            ne.initCause(e);
            throw ne;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            try {
                is.close();
            } catch (IOException e) {
            }
        }

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
     * @exception NameAlreadyBoundException if the name is already bound
     * @exception javax.naming.directory.InvalidAttributesException if attrs
     * does not contain all the mandatory attributes required for creation
     * @exception NamingException if a naming exception is encountered
     */
    @Override
    public DirContext createSubcontext(String name, Attributes attrs)
        throws NamingException {

        File file = file(name, false);
        if (file == null) {
            throw new NamingException(sm.getString("resources.bindFailed", name));
        }
        if (file.exists())
            throw new NameAlreadyBoundException(sm.getString("resources.alreadyBound", name));
        if (!file.mkdir())
            throw new NamingException(sm.getString("resources.bindFailed", name));
        return (DirContext) lookup(name);

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
        return null;
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
        return null;
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
        return null;
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
    public NamingEnumeration<SearchResult> search(String name,
            String filterExpr, Object[] filterArgs, SearchControls cons)
        throws NamingException {
        return null;
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Return a context-relative path, beginning with a "/", that represents
     * the canonical version of the specified path after ".." and "." elements
     * are resolved out.  If the specified path attempts to go outside the
     * boundaries of the current context (i.e. too many ".." path elements
     * are present), return <code>null</code> instead.
     *
     * @param path Path to be normalized
     */
    protected String normalize(String path) {

        return RequestUtil.normalize(path, File.separatorChar == '\\');

    }


    /**
     * Return a File object representing the specified normalized
     * context-relative path if it exists and is readable.  Otherwise,
     * return <code>null</code>.
     *
     * @param name Normalized context-relative path (with leading '/')
     */
    protected File file(String name) {
        return file(name, true);
    }


    /**
     * Return a File object representing the specified normalized
     * context-relative path if it exists and is readable.  Otherwise,
     * return <code>null</code>.
     *
     * @param name      Normalized context-relative path (with leading '/')
     * @param mustExist Must the specified resource exist?
     */
    protected File file(String name, boolean mustExist) {
        if (name.equals("/")) {
            name = "";
        }

        File file = new File(base, name);
        return validate(file, name, mustExist, absoluteBase, canonicalBase);
    }


    protected File validate(File file, String name, boolean mustExist, String absoluteBase,
            String canonicalBase) {

        // If the requested names ends in '/', the Java File API will return a
        // matching file if one exists. This isn't what we want as it is not
        // consistent with the Servlet spec rules for request mapping.
        if (name.endsWith("/") && file.isFile()) {
            return null;
        }

        // If the file/dir must exist but the identified file/dir can't be read
        // then signal that the resource was not found
        if (mustExist && !file.canRead()) {
            return null;
        }

        // If allow linking is enabled, files are not limited to being located
        // under the fileBase so all further checks are disabled.
        if (allowLinking) {
            return file;
        }

        // Additional Windows specific checks to handle known problems with
        // File.getCanonicalPath()
        if (JrePlatform.IS_WINDOWS && isInvalidWindowsFilename(name)) {
            return null;
        }

        // Check that this file is located under the web application root
        String canPath = null;
        try {
            canPath = file.getCanonicalPath();
        } catch (IOException e) {
            // Ignore
        }
        if (canPath == null || !canPath.startsWith(canonicalBase)) {
            return null;
        }

        // Ensure that the file is not outside the fileBase. This should not be
        // possible for standard requests (the request is normalized early in
        // the request processing) but might be possible for some access via the
        // Servlet API (RequestDispatcher etc.) therefore these checks are
        // retained as an additional safety measure. absoluteBase has been
        // normalized so absPath needs to be normalized as well.
        String absPath = normalize(file.getAbsolutePath());
        if ((absoluteBase.length() > absPath.length())) {
            return null;
        }

        // Remove the fileBase location from the start of the paths since that
        // was not part of the requested path and the remaining check only
        // applies to the request path
        absPath = absPath.substring(absoluteBase.length());
        canPath = canPath.substring(canonicalBase.length());

        // Case sensitivity check
        // The normalized requested path should be an exact match the equivalent
        // canonical path. If it is not, possible reasons include:
        // - case differences on case insensitive file systems
        // - Windows removing a trailing ' ' or '.' from the file name
        //
        // In all cases, a mis-match here results in the resource not being
        // found
        //
        // absPath is normalized so canPath needs to be normalized as well
        // Can't normalize canPath earlier as canonicalBase is not normalized
        if (canPath.length() > 0) {
            canPath = normalize(canPath);
        }
        if (!canPath.equals(absPath)) {
            return null;
        }

        return file;
    }


    private boolean isInvalidWindowsFilename(String name) {
        final int len = name.length();
        if (len == 0) {
            return false;
        }
        // This consistently ~10 times faster than the equivalent regular
        // expression irrespective of input length.
        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            if (c == '\"' || c == '<' || c == '>') {
                // These characters are disallowed in Windows file names and
                // there are known problems for file names with these characters
                // when using File#getCanonicalPath().
                // Note: There are additional characters that are disallowed in
                //       Windows file names but these are not known to cause
                //       problems when using File#getCanonicalPath().
                return true;
            }
        }
        // Windows does not allow file names to end in ' ' unless specific low
        // level APIs are used to create the files that bypass various checks.
        // File names that end in ' ' are known to cause problems when using
        // File#getCanonicalPath().
        if (name.charAt(len -1) == ' ') {
            return true;
        }
        return false;
    }


    /**
     * List the resources which are members of a collection.
     *
     * @param file Collection
     * @return Vector containing NamingEntry objects
     */
    protected List<NamingEntry> list(File file) {

        List<NamingEntry> entries = new ArrayList<NamingEntry>();
        if (!file.isDirectory())
            return entries;
        String[] names = file.list();
        if (names==null) {
            /* Some IO error occurred such as bad file permissions.
               Prevent a NPE with Arrays.sort(names) */
            log.warn(sm.getString("fileResources.listingNull",
                                  file.getAbsolutePath()));
            return entries;
        }

        Arrays.sort(names);             // Sort alphabetically
        NamingEntry entry = null;

        for (int i = 0; i < names.length; i++) {

            File currentFile = new File(file, names[i]);
            Object object = null;
            if (currentFile.isDirectory()) {
                FileDirContext tempContext = new FileDirContext(env);
                tempContext.setDocBase(currentFile.getPath());
                tempContext.setAllowLinking(getAllowLinking());
                object = tempContext;
            } else {
                object = new FileResource(currentFile);
            }
            entry = new NamingEntry(names[i], object, NamingEntry.ENTRY);
            entries.add(entry);

        }

        return entries;

    }


    // ----------------------------------------------- FileResource Inner Class


    /**
     * This specialized resource implementation avoids opening the InputStream
     * to the file right away (which would put a lock on the file).
     */
    protected static class FileResource extends Resource {


        // -------------------------------------------------------- Constructor


        public FileResource(File file) {
            this.file = file;
        }


        // --------------------------------------------------- Member Variables


        /**
         * Associated file object.
         */
        protected File file;


        // --------------------------------------------------- Resource Methods


        /**
         * Content accessor.
         *
         * @return InputStream
         */
        @Override
        public InputStream streamContent()
            throws IOException {
            if (binaryContent == null) {
                FileInputStream fis = new FileInputStream(file);
                inputStream = fis;
                return fis;
            }
            return super.streamContent();
        }


    }


    // ------------------------------------- FileResourceAttributes Inner Class


    /**
     * This specialized resource attribute implementation does some lazy
     * reading (to speed up simple checks, like checking the last modified
     * date).
     */
    protected static class FileResourceAttributes extends ResourceAttributes {

        private static final long serialVersionUID = 1L;

        // -------------------------------------------------------- Constructor

        public FileResourceAttributes(File file) {
            this.file = file;
            getCreation();
            getLastModified();
        }

        // --------------------------------------------------- Member Variables


        protected File file;


        protected boolean accessed = false;


        protected String canonicalPath = null;


        // ----------------------------------------- ResourceAttributes Methods


        /**
         * Is collection.
         */
        @Override
        public boolean isCollection() {
            if (!accessed) {
                collection = file.isDirectory();
                accessed = true;
            }
            return super.isCollection();
        }


        /**
         * Get content length.
         *
         * @return content length value
         */
        @Override
        public long getContentLength() {
            if (contentLength != -1L)
                return contentLength;
            contentLength = file.length();
            return contentLength;
        }


        /**
         * Get creation time.
         *
         * @return creation time value
         */
        @Override
        public long getCreation() {
            if (creation != -1L)
                return creation;
            creation = getLastModified();
            return creation;
        }


        /**
         * Get creation date.
         *
         * @return Creation date value
         */
        @Override
        public Date getCreationDate() {
            if (creation == -1L) {
                creation = getCreation();
            }
            return super.getCreationDate();
        }


        /**
         * Get last modified time.
         *
         * @return lastModified time value
         */
        @Override
        public long getLastModified() {
            if (lastModified != -1L)
                return lastModified;
            lastModified = file.lastModified();
            return lastModified;
        }


        /**
         * Get lastModified date.
         *
         * @return LastModified date value
         */
        @Override
        public Date getLastModifiedDate() {
            if (lastModified == -1L) {
                lastModified = getLastModified();
            }
            return super.getLastModifiedDate();
        }


        /**
         * Get name.
         *
         * @return Name value
         */
        @Override
        public String getName() {
            if (name == null)
                name = file.getName();
            return name;
        }


        /**
         * Get resource type.
         *
         * @return String resource type
         */
        @Override
        public String getResourceType() {
            if (!accessed) {
                collection = file.isDirectory();
                accessed = true;
            }
            return super.getResourceType();
        }


        /**
         * Get canonical path.
         *
         * @return String the file's canonical path
         */
        @Override
        public String getCanonicalPath() {
            if (canonicalPath == null) {
                try {
                    canonicalPath = file.getCanonicalPath();
                } catch (IOException e) {
                    // Ignore
                }
            }
            return canonicalPath;
        }
    }
}

