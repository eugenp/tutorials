/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.tomcat.util.http.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.apache.tomcat.util.buf.Ascii;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.res.StringManager;

/**
 * Mapper, which implements the servlet API mapping rules (which are derived
 * from the HTTP rules).
 *
 * @author Remy Maucherat
 */
public final class Mapper {


    private static final org.apache.juli.logging.Log log =
        org.apache.juli.logging.LogFactory.getLog(Mapper.class);

    static final StringManager sm =
        StringManager.getManager(Mapper.class.getPackage().getName());

    // ----------------------------------------------------- Instance Variables


    /**
     * Array containing the virtual hosts definitions.
     */
    Host[] hosts = new Host[0];


    /**
     * Default host name.
     */
    String defaultHostName = null;

    /**
     * ContextVersion associated with this Mapper, used for wrapper mapping.
     *
     * <p>
     * It is used only by Mapper in a Context. Is not used by Mapper in a
     * Connector.
     *
     * @see #setContext(String, String[], javax.naming.Context)
     */
    ContextVersion context = new ContextVersion();


    // --------------------------------------------------------- Public Methods


    /**
     * Set default host.
     *
     * @param defaultHostName Default host name
     */
    public void setDefaultHostName(String defaultHostName) {
        this.defaultHostName = defaultHostName;
    }

    /**
     * Add a new host to the mapper.
     *
     * @param name Virtual host name
     * @param aliases Alias names for the virtual host
     * @param host Host object
     */
    public synchronized void addHost(String name, String[] aliases,
                                     Object host) {
        Host[] newHosts = new Host[hosts.length + 1];
        Host newHost = new Host(name, host);
        if (insertMap(hosts, newHosts, newHost)) {
            hosts = newHosts;
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("mapper.addHost.success", name));
            }
        } else {
            Host duplicate = hosts[find(hosts, name)];
            if (duplicate.object == host) {
                // The host is already registered in the mapper.
                // E.g. it might have been added by addContextVersion()
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("mapper.addHost.sameHost", name));
                }
                newHost = duplicate;
            } else {
                log.error(sm.getString("mapper.duplicateHost", name,
                        duplicate.getRealHostName()));
                // Do not add aliases, as removeHost(hostName) won't be able to
                // remove them
                return;
            }
        }
        List<Host> newAliases = new ArrayList<Host>(aliases.length);
        for (String alias : aliases) {
            Host newAlias = new Host(alias, newHost);
            if (addHostAliasImpl(newAlias)) {
                newAliases.add(newAlias);
            }
        }
        newHost.addAliases(newAliases);
    }


    /**
     * Remove a host from the mapper.
     *
     * @param name Virtual host name
     */
    public synchronized void removeHost(String name) {
        // Find and remove the old host
        Host host = exactFind(hosts, name);
        if (host == null || host.isAlias()) {
            return;
        }
        Host[] newHosts = hosts.clone();
        // Remove real host and all its aliases
        int j = 0;
        for (int i = 0; i < newHosts.length; i++) {
            if (newHosts[i].getRealHost() != host) {
                newHosts[j++] = newHosts[i];
            }
        }
        hosts = Arrays.copyOf(newHosts, j);
    }

    /**
     * Add an alias to an existing host.
     * @param name  The name of the host
     * @param alias The alias to add
     */
    public synchronized void addHostAlias(String name, String alias) {
        Host realHost = exactFind(hosts, name);
        if (realHost == null) {
            // Should not be adding an alias for a host that doesn't exist but
            // just in case...
            return;
        }
        Host newAlias = new Host(alias, realHost);
        if (addHostAliasImpl(newAlias)) {
            realHost.addAlias(newAlias);
        }
    }

    private boolean addHostAliasImpl(Host newAlias) {
        Host[] newHosts = new Host[hosts.length + 1];
        if (insertMap(hosts, newHosts, newAlias)) {
            hosts = newHosts;
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("mapper.addHostAlias.success",
                        newAlias.name, newAlias.getRealHostName()));
            }
            return true;
        } else {
            Host duplicate = hosts[find(hosts, newAlias.name)];
            if (duplicate.getRealHost() == newAlias.getRealHost()) {
                // A duplicate Alias for the same Host.
                // A harmless redundancy. E.g.
                // <Host name="localhost"><Alias>localhost</Alias></Host>
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("mapper.addHostAlias.sameHost",
                            newAlias.name, newAlias.getRealHostName()));
                }
                return false;
            }
            log.error(sm.getString("mapper.duplicateHostAlias", newAlias.name,
                    newAlias.getRealHostName(), duplicate.getRealHostName()));
            return false;
        }
    }

    /**
     * Remove a host alias
     * @param alias The alias to remove
     */
    public synchronized void removeHostAlias(String alias) {
        // Find and remove the alias
        Host host = exactFind(hosts, alias);
        if (host == null || !host.isAlias()) {
            return;
        }
        Host[] newHosts = new Host[hosts.length - 1];
        if (removeMap(hosts, newHosts, alias)) {
            hosts = newHosts;
            host.getRealHost().removeAlias(host);
        }

    }

    /**
     * Replace {@link Host#contextList} field in <code>realHost</code> and
     * all its aliases with a new value.
     */
    private void updateContextList(Host realHost, ContextList newContextList) {
        realHost.contextList = newContextList;
        for (Host alias : realHost.getAliases()) {
            alias.contextList = newContextList;
        }
    }

    /**
     * Set context, used for wrapper mapping (request dispatcher).
     *
     * @param welcomeResources Welcome files defined for this context
     * @param resources Static resources of the context
     */
    public void setContext(String path, String[] welcomeResources,
                           javax.naming.Context resources) {
        context.path = path;
        context.welcomeResources = welcomeResources;
        context.resources = resources;
    }


    /**
     * Add a new Context to an existing Host.
     *
     * @param hostName Virtual host name this context belongs to
     * @param host Host object
     * @param path Context path
     * @param version Context version
     * @param context Context object
     * @param welcomeResources Welcome files defined for this context
     * @param resources Static resources of the context
     * @deprecated Use {@link #addContextVersion(String, Object, String, String, Object, String[],
     *             javax.naming.Context, Collection, boolean, boolean)}
     */
    @Deprecated
    public void addContextVersion(String hostName, Object host, String path,
            String version, Object context, String[] welcomeResources,
            javax.naming.Context resources) {
        addContextVersion(hostName, host, path, version, context,
                welcomeResources, resources, null);
    }

    
    /**
     * Add a new Context to an existing Host.
     *
     * @param hostName Virtual host name this context belongs to
     * @param host Host object
     * @param path Context path
     * @param version Context version
     * @param context Context object
     * @param welcomeResources Welcome files defined for this context
     * @param resources Static resources of the context
     * @param wrappers Information on wrapper mappings
     * @deprecated Use {@link #addContextVersion(String, Object, String, String, Object, String[],
     *             javax.naming.Context, Collection, boolean, boolean)}
     */
    @Deprecated
    public void addContextVersion(String hostName, Object host, String path,
            String version, Object context, String[] welcomeResources,
            javax.naming.Context resources, Collection<WrapperMappingInfo> wrappers) {
        addContextVersion(hostName, host, path, version, context, welcomeResources, resources,
                wrappers, false, false);
    }
    
    
    /**
     * Add a new Context to an existing Host.
     *
     * @param hostName Virtual host name this context belongs to
     * @param host Host object
     * @param path Context path
     * @param version Context version
     * @param context Context object
     * @param welcomeResources Welcome files defined for this context
     * @param resources Static resources of the context
     * @param wrappers Information on wrapper mappings
     * @param mapperContextRootRedirectEnabled Mapper does context root redirects
     * @param mapperDirectoryRedirectEnabled Mapper does directory redirects
     */
    public void addContextVersion(String hostName, Object host, String path,
            String version, Object context, String[] welcomeResources,
            javax.naming.Context resources, Collection<WrapperMappingInfo> wrappers,
            boolean mapperContextRootRedirectEnabled, boolean mapperDirectoryRedirectEnabled) {

        Host mappedHost = exactFind(hosts, hostName);
        if (mappedHost == null) {
            addHost(hostName, new String[0], host);
            mappedHost = exactFind(hosts, hostName);
            if (mappedHost == null) {
                log.error("No host found: " + hostName);
                return;
            }
        }
        if (mappedHost.isAlias()) {
            log.error("No host found: " + hostName);
            return;
        }
        int slashCount = slashCount(path);
        synchronized (mappedHost) {
            ContextVersion newContextVersion = new ContextVersion(version, context);
            newContextVersion.path = path;
            newContextVersion.slashCount = slashCount;
            newContextVersion.welcomeResources = welcomeResources;
            newContextVersion.resources = resources;
            newContextVersion.mapperContextRootRedirectEnabled = mapperContextRootRedirectEnabled;
            newContextVersion.mapperDirectoryRedirectEnabled = mapperDirectoryRedirectEnabled;
            
            if (wrappers != null) {
                addWrappers(newContextVersion, wrappers);
            }

            ContextList contextList = mappedHost.contextList;
            Context mappedContext = exactFind(contextList.contexts, path);
            if (mappedContext == null) {
                mappedContext = new Context(path, newContextVersion);
                ContextList newContextList = contextList.addContext(
                        mappedContext, slashCount);
                if (newContextList != null) {
                    updateContextList(mappedHost, newContextList);
                }
            } else {
                ContextVersion[] contextVersions = mappedContext.versions;
                ContextVersion[] newContextVersions =
                    new ContextVersion[contextVersions.length + 1];
                if (insertMap(contextVersions, newContextVersions, newContextVersion)) {
                    mappedContext.versions = newContextVersions;
                } else {
                    // Re-registration after Context.reload()
                    // Replace ContextVersion with the new one
                    int pos = find(contextVersions, version);
                    if (pos >= 0 && contextVersions[pos].name.equals(version)) {
                        contextVersions[pos] = newContextVersion;
                    }
                }
            }
        }

    }


    /**
     * Remove a context from an existing host.
     *
     * @param hostName Virtual host name this context belongs to
     * @param path Context path
     * @param version Context version
     */
    public void removeContextVersion(String hostName, String path,
            String version) {
        Host host = exactFind(hosts, hostName);
        if (host == null || host.isAlias()) {
            return;
        }
        synchronized (host) {
            ContextList contextList = host.contextList;
            Context context = exactFind(contextList.contexts, path);
            if (context == null) {
                return;
            }

            ContextVersion[] contextVersions = context.versions;
            ContextVersion[] newContextVersions =
                new ContextVersion[contextVersions.length - 1];
            if (removeMap(contextVersions, newContextVersions, version)) {
                if (newContextVersions.length == 0) {
                    // Remove the context
                    ContextList newContextList = contextList.removeContext(path);
                    if (newContextList != null) {
                        updateContextList(host, newContextList);
                    }
                } else {
                    context.versions = newContextVersions;
                }
            }
        }
    }


    /**
     * Mark a context as being reloaded. Reversion of this state is performed
     * by calling <code>addContextVersion(...)</code> when context starts up.
     *
     * @param ctxt      The actual context
     * @param hostName  Virtual host name this context belongs to
     * @param contextPath Context path
     * @param version   Context version
     */
    public void pauseContextVersion(Object ctxt, String hostName,
            String contextPath, String version) {

        ContextVersion contextVersion = findContextVersion(hostName,
                contextPath, version, true);
        if (contextVersion == null || !ctxt.equals(contextVersion.object)) {
            return;
        }
        contextVersion.markPaused();
    }


    private ContextVersion findContextVersion(String hostName,
            String contextPath, String version, boolean silent) {
        Host host = exactFind(hosts, hostName);
        if (host == null || host.isAlias()) {
            if (!silent) {
                log.error("No host found: " + hostName);
            }
            return null;
        }
        Context context = exactFind(host.contextList.contexts, contextPath);
        if (context == null) {
            if (!silent) {
                log.error("No context found: " + contextPath);
            }
            return null;
        }
        ContextVersion contextVersion = exactFind(context.versions, version);
        if (contextVersion == null) {
            if (!silent) {
                log.error("No context version found: " + contextPath + " "
                        + version);
            }
            return null;
        }
        return contextVersion;
    }


    public void addWrapper(String hostName, String contextPath, String version,
                           String path, Object wrapper, boolean jspWildCard,
                           boolean resourceOnly) {
        ContextVersion contextVersion = findContextVersion(hostName,
                contextPath, version, false);
        if (contextVersion == null) {
            return;
        }
        addWrapper(contextVersion, path, wrapper, jspWildCard, resourceOnly);
    }


    public void addWrapper(String path, Object wrapper, boolean jspWildCard,
            boolean resourceOnly) {
        addWrapper(context, path, wrapper, jspWildCard, resourceOnly);
    }

    public void addWrappers(String hostName, String contextPath,
            String version, Collection<WrapperMappingInfo> wrappers) {
        ContextVersion contextVersion = findContextVersion(hostName,
                contextPath, version, false);
        if (contextVersion == null) {
            return;
        }
        addWrappers(contextVersion, wrappers);
    }

    /**
     * Adds wrappers to the given context.
     *
     * @param contextVersion The context to which to add the wrappers
     * @param wrappers Information on wrapper mappings
     */
    private void addWrappers(ContextVersion contextVersion,
            Collection<WrapperMappingInfo> wrappers) {
        for (WrapperMappingInfo wrapper : wrappers) {
            addWrapper(contextVersion, wrapper.getMapping(),
                    wrapper.getWrapper(), wrapper.isJspWildCard(),
                    wrapper.isResourceOnly());
        }
    }

    /**
     * Adds a wrapper to the given context.
     *
     * @param context The context to which to add the wrapper
     * @param path Wrapper mapping
     * @param wrapper The Wrapper object
     * @param jspWildCard true if the wrapper corresponds to the JspServlet
     *   and the mapping path contains a wildcard; false otherwise
     * @param resourceOnly true if this wrapper always expects a physical
     *                     resource to be present (such as a JSP)
     */
    protected void addWrapper(ContextVersion context, String path,
            Object wrapper, boolean jspWildCard, boolean resourceOnly) {

        synchronized (context) {
            if (path.endsWith("/*")) {
                // Wildcard wrapper
                String name = path.substring(0, path.length() - 2);
                Wrapper newWrapper = new Wrapper(name, wrapper, jspWildCard,
                        resourceOnly);
                Wrapper[] oldWrappers = context.wildcardWrappers;
                Wrapper[] newWrappers =
                    new Wrapper[oldWrappers.length + 1];
                if (insertMap(oldWrappers, newWrappers, newWrapper)) {
                    context.wildcardWrappers = newWrappers;
                    int slashCount = slashCount(newWrapper.name);
                    if (slashCount > context.nesting) {
                        context.nesting = slashCount;
                    }
                }
            } else if (path.startsWith("*.")) {
                // Extension wrapper
                String name = path.substring(2);
                Wrapper newWrapper = new Wrapper(name, wrapper, jspWildCard,
                        resourceOnly);
                Wrapper[] oldWrappers = context.extensionWrappers;
                Wrapper[] newWrappers =
                    new Wrapper[oldWrappers.length + 1];
                if (insertMap(oldWrappers, newWrappers, newWrapper)) {
                    context.extensionWrappers = newWrappers;
                }
            } else if (path.equals("/")) {
                // Default wrapper
                Wrapper newWrapper = new Wrapper("", wrapper, jspWildCard,
                        resourceOnly);
                context.defaultWrapper = newWrapper;
            } else {
                // Exact wrapper
                final String name;
                if (path.length() == 0) {
                    // Special case for the Context Root mapping which is
                    // treated as an exact match
                    name = "/";
                } else {
                    name = path;
                }
                Wrapper newWrapper = new Wrapper(name, wrapper, jspWildCard,
                        resourceOnly);
                Wrapper[] oldWrappers = context.exactWrappers;
                Wrapper[] newWrappers =
                    new Wrapper[oldWrappers.length + 1];
                if (insertMap(oldWrappers, newWrappers, newWrapper)) {
                    context.exactWrappers = newWrappers;
                }
            }
        }
    }


    /**
     * Remove a wrapper from the context associated with this wrapper.
     *
     * @param path Wrapper mapping
     */
    public void removeWrapper(String path) {
        removeWrapper(context, path);
    }


    /**
     * Remove a wrapper from an existing context.
     *
     * @param hostName Virtual host name this wrapper belongs to
     * @param contextPath Context path this wrapper belongs to
     * @param path Wrapper mapping
     */
    public void removeWrapper(String hostName, String contextPath,
            String version, String path) {
        ContextVersion contextVersion = findContextVersion(hostName,
                contextPath, version, true);
        if (contextVersion == null || contextVersion.isPaused()) {
            return;
        }
        removeWrapper(contextVersion, path);
    }

    protected void removeWrapper(ContextVersion context, String path) {

        if (log.isDebugEnabled()) {
            log.debug(sm.getString("mapper.removeWrapper", context.name, path));
        }

        synchronized (context) {
            if (path.endsWith("/*")) {
                // Wildcard wrapper
                String name = path.substring(0, path.length() - 2);
                Wrapper[] oldWrappers = context.wildcardWrappers;
                if (oldWrappers.length == 0) {
                    return;
                }
                Wrapper[] newWrappers =
                    new Wrapper[oldWrappers.length - 1];
                if (removeMap(oldWrappers, newWrappers, name)) {
                    // Recalculate nesting
                    context.nesting = 0;
                    for (int i = 0; i < newWrappers.length; i++) {
                        int slashCount = slashCount(newWrappers[i].name);
                        if (slashCount > context.nesting) {
                            context.nesting = slashCount;
                        }
                    }
                    context.wildcardWrappers = newWrappers;
                }
            } else if (path.startsWith("*.")) {
                // Extension wrapper
                String name = path.substring(2);
                Wrapper[] oldWrappers = context.extensionWrappers;
                if (oldWrappers.length == 0) {
                    return;
                }
                Wrapper[] newWrappers =
                    new Wrapper[oldWrappers.length - 1];
                if (removeMap(oldWrappers, newWrappers, name)) {
                    context.extensionWrappers = newWrappers;
                }
            } else if (path.equals("/")) {
                // Default wrapper
                context.defaultWrapper = null;
            } else {
                // Exact wrapper
                String name;
                if (path.length() == 0) {
                    // Special case for the Context Root mapping which is
                    // treated as an exact match
                    name = "/";
                } else {
                    name = path;
                }
                Wrapper[] oldWrappers = context.exactWrappers;
                if (oldWrappers.length == 0) {
                    return;
                }
                Wrapper[] newWrappers =
                    new Wrapper[oldWrappers.length - 1];
                if (removeMap(oldWrappers, newWrappers, name)) {
                    context.exactWrappers = newWrappers;
                }
            }
        }
    }


    /**
     * Add a welcome file to the given context.
     *
     * @param hostName
     * @param contextPath
     * @param welcomeFile
     */
    public void addWelcomeFile(String hostName, String contextPath,
            String version, String welcomeFile) {
        ContextVersion contextVersion = findContextVersion(hostName,
                contextPath, version, false);
        if (contextVersion == null) {
            return;
        }
        int len = contextVersion.welcomeResources.length + 1;
        String[] newWelcomeResources = new String[len];
        System.arraycopy(contextVersion.welcomeResources, 0,
                newWelcomeResources, 0, len - 1);
        newWelcomeResources[len - 1] = welcomeFile;
        contextVersion.welcomeResources = newWelcomeResources;
    }

    /**
     * Remove a welcome file from the given context.
     *
     * @param hostName
     * @param contextPath
     * @param welcomeFile
     */
    public void removeWelcomeFile(String hostName, String contextPath,
            String version, String welcomeFile) {
        ContextVersion contextVersion = findContextVersion(hostName,
                contextPath, version, false);
        if (contextVersion == null || contextVersion.isPaused()) {
            return;
        }
        int match = -1;
        for (int i = 0; i < contextVersion.welcomeResources.length; i++) {
            if (welcomeFile.equals(contextVersion.welcomeResources[i])) {
                match = i;
                break;
            }
        }
        if (match > -1) {
            int len = contextVersion.welcomeResources.length - 1;
            String[] newWelcomeResources = new String[len];
            System.arraycopy(contextVersion.welcomeResources, 0,
                    newWelcomeResources, 0, match);
            if (match < len) {
                System.arraycopy(contextVersion.welcomeResources, match + 1,
                        newWelcomeResources, match, len - match);
            }
            contextVersion.welcomeResources = newWelcomeResources;
        }
    }

    /**
     * Clear the welcome files for the given context.
     *
     * @param hostName
     * @param contextPath
     */
    public void clearWelcomeFiles(String hostName, String contextPath,
            String version) {
        ContextVersion contextVersion = findContextVersion(hostName,
                contextPath, version, false);
        if (contextVersion == null) {
            return;
        }
        contextVersion.welcomeResources = new String[0];
    }


    /**
     * Map the specified host name and URI, mutating the given mapping data.
     *
     * @param host Virtual host name
     * @param uri URI
     * @param mappingData This structure will contain the result of the mapping
     *                    operation
     */
    public void map(MessageBytes host, MessageBytes uri, String version,
                    MappingData mappingData)
        throws Exception {

        if (host.isNull()) {
            host.getCharChunk().append(defaultHostName);
        }
        host.toChars();
        uri.toChars();
        internalMap(host.getCharChunk(), uri.getCharChunk(), version,
                mappingData);

    }


    /**
     * Map the specified URI relative to the context,
     * mutating the given mapping data.
     *
     * @param uri URI
     * @param mappingData This structure will contain the result of the mapping
     *                    operation
     */
    public void map(MessageBytes uri, MappingData mappingData)
        throws Exception {

        uri.toChars();
        CharChunk uricc = uri.getCharChunk();
        uricc.setLimit(-1);
        internalMapWrapper(context, uricc, mappingData);

    }


    // -------------------------------------------------------- Private Methods


    /**
     * Map the specified URI.
     */
    private final void internalMap(CharChunk host, CharChunk uri,
            String version, MappingData mappingData) throws Exception {

        if (mappingData.host != null) {
            // The legacy code (dating down at least to Tomcat 4.1) just
            // skipped all mapping work in this case. That behaviour has a risk
            // of returning an inconsistent result.
            // I do not see a valid use case for it.
            throw new AssertionError();
        }

        uri.setLimit(-1);

        // Virtual host mapping
        Host[] hosts = this.hosts;
        Host mappedHost = exactFindIgnoreCase(hosts, host);
        if (mappedHost == null) {
            if (defaultHostName == null) {
                return;
            }
            mappedHost = exactFind(hosts, defaultHostName);
            if (mappedHost == null) {
                return;
            }
        }
        mappingData.host = mappedHost.object;

        // Context mapping
        ContextList contextList = mappedHost.contextList;
        Context[] contexts = contextList.contexts;
        int nesting = contextList.nesting;

        int pos = find(contexts, uri);
        if (pos == -1) {
            return;
        }

        int lastSlash = -1;
        int uriEnd = uri.getEnd();
        int length = -1;
        boolean found = false;
        Context context = null;
        while (pos >= 0) {
            context = contexts[pos];
            if (uri.startsWith(context.name)) {
                length = context.name.length();
                if (uri.getLength() == length) {
                    found = true;
                    break;
                } else if (uri.startsWithIgnoreCase("/", length)) {
                    found = true;
                    break;
                }
            }
            if (lastSlash == -1) {
                lastSlash = nthSlash(uri, nesting + 1);
            } else {
                lastSlash = lastSlash(uri);
            }
            uri.setEnd(lastSlash);
            pos = find(contexts, uri);
        }
        uri.setEnd(uriEnd);

        if (!found) {
            if (contexts[0].name.equals("")) {
                context = contexts[0];
            } else {
                context = null;
            }
        }
        if (context == null) {
            return;
        }

        mappingData.contextPath.setString(context.name);

        ContextVersion contextVersion = null;
        ContextVersion[] contextVersions = context.versions;
        final int versionCount = contextVersions.length;
        if (versionCount > 1) {
            Object[] contextObjects = new Object[contextVersions.length];
            for (int i = 0; i < contextObjects.length; i++) {
                contextObjects[i] = contextVersions[i].object;
            }
            mappingData.contexts = contextObjects;
            if (version != null) {
                contextVersion = exactFind(contextVersions, version);
            }
        }
        if (contextVersion == null) {
            // Return the latest version
            // The versions array is known to contain at least one element
            contextVersion = contextVersions[versionCount - 1];
        }

        mappingData.context = contextVersion.object;
        mappingData.contextSlashCount = contextVersion.slashCount;

        // Wrapper mapping
        if (!contextVersion.isPaused()) {
            internalMapWrapper(contextVersion, uri, mappingData);
        }

    }


    /**
     * Wrapper mapping.
     */
    private final void internalMapWrapper(ContextVersion contextVersion,
                                          CharChunk path,
                                          MappingData mappingData)
        throws Exception {

        int pathOffset = path.getOffset();
        int pathEnd = path.getEnd();
        boolean noServletPath = false;

        int length = contextVersion.path.length();
        if (length == (pathEnd - pathOffset)) {
            noServletPath = true;
        }
        int servletPath = pathOffset + length;
        path.setOffset(servletPath);

        // Rule 1 -- Exact Match
        Wrapper[] exactWrappers = contextVersion.exactWrappers;
        internalMapExactWrapper(exactWrappers, path, mappingData);

        // Rule 2 -- Prefix Match
        boolean checkJspWelcomeFiles = false;
        Wrapper[] wildcardWrappers = contextVersion.wildcardWrappers;
        if (mappingData.wrapper == null) {
            internalMapWildcardWrapper(wildcardWrappers, contextVersion.nesting,
                                       path, mappingData);
            if (mappingData.wrapper != null && mappingData.jspWildCard) {
                char[] buf = path.getBuffer();
                if (buf[pathEnd - 1] == '/') {
                    /*
                     * Path ending in '/' was mapped to JSP servlet based on
                     * wildcard match (e.g., as specified in url-pattern of a
                     * jsp-property-group.
                     * Force the context's welcome files, which are interpreted
                     * as JSP files (since they match the url-pattern), to be
                     * considered. See Bugzilla 27664.
                     */
                    mappingData.wrapper = null;
                    checkJspWelcomeFiles = true;
                } else {
                    // See Bugzilla 27704
                    mappingData.wrapperPath.setChars(buf, path.getStart(),
                                                     path.getLength());
                    mappingData.pathInfo.recycle();
                }
            }
        }

        if(mappingData.wrapper == null && noServletPath &&
                contextVersion.mapperContextRootRedirectEnabled) {
            // The path is empty, redirect to "/"
            path.append('/');
            pathEnd = path.getEnd();
            mappingData.redirectPath.setChars
                (path.getBuffer(), pathOffset, pathEnd - pathOffset);
            path.setEnd(pathEnd - 1);
            return;
        }

        // Rule 3 -- Extension Match
        Wrapper[] extensionWrappers = contextVersion.extensionWrappers;
        if (mappingData.wrapper == null && !checkJspWelcomeFiles) {
            internalMapExtensionWrapper(extensionWrappers, path, mappingData,
                    true);
        }

        // Rule 4 -- Welcome resources processing for servlets
        if (mappingData.wrapper == null) {
            boolean checkWelcomeFiles = checkJspWelcomeFiles;
            if (!checkWelcomeFiles) {
                char[] buf = path.getBuffer();
                checkWelcomeFiles = (buf[pathEnd - 1] == '/');
            }
            if (checkWelcomeFiles) {
                for (int i = 0; (i < contextVersion.welcomeResources.length)
                         && (mappingData.wrapper == null); i++) {
                    path.setOffset(pathOffset);
                    path.setEnd(pathEnd);
                    path.append(contextVersion.welcomeResources[i], 0,
                            contextVersion.welcomeResources[i].length());
                    path.setOffset(servletPath);

                    // Rule 4a -- Welcome resources processing for exact macth
                    internalMapExactWrapper(exactWrappers, path, mappingData);

                    // Rule 4b -- Welcome resources processing for prefix match
                    if (mappingData.wrapper == null) {
                        internalMapWildcardWrapper
                            (wildcardWrappers, contextVersion.nesting,
                             path, mappingData);
                    }

                    // Rule 4c -- Welcome resources processing
                    //            for physical folder
                    if (mappingData.wrapper == null
                        && contextVersion.resources != null) {
                        Object file = null;
                        String pathStr = path.toString();
                        try {
                            file = contextVersion.resources.lookup(pathStr);
                        } catch(NamingException nex) {
                            // Swallow not found, since this is normal
                        }
                        if (file != null && !(file instanceof DirContext) ) {
                            internalMapExtensionWrapper(extensionWrappers, path,
                                                        mappingData, true);
                            if (mappingData.wrapper == null
                                && contextVersion.defaultWrapper != null) {
                                mappingData.wrapper =
                                    contextVersion.defaultWrapper.object;
                                mappingData.requestPath.setChars
                                    (path.getBuffer(), path.getStart(),
                                     path.getLength());
                                mappingData.wrapperPath.setChars
                                    (path.getBuffer(), path.getStart(),
                                     path.getLength());
                                mappingData.requestPath.setString(pathStr);
                                mappingData.wrapperPath.setString(pathStr);
                            }
                        }
                    }
                }

                path.setOffset(servletPath);
                path.setEnd(pathEnd);
            }

        }

        /* welcome file processing - take 2
         * Now that we have looked for welcome files with a physical
         * backing, now look for an extension mapping listed
         * but may not have a physical backing to it. This is for
         * the case of index.jsf, index.do, etc.
         * A watered down version of rule 4
         */
        if (mappingData.wrapper == null) {
            boolean checkWelcomeFiles = checkJspWelcomeFiles;
            if (!checkWelcomeFiles) {
                char[] buf = path.getBuffer();
                checkWelcomeFiles = (buf[pathEnd - 1] == '/');
            }
            if (checkWelcomeFiles) {
                for (int i = 0; (i < contextVersion.welcomeResources.length)
                         && (mappingData.wrapper == null); i++) {
                    path.setOffset(pathOffset);
                    path.setEnd(pathEnd);
                    path.append(contextVersion.welcomeResources[i], 0,
                                contextVersion.welcomeResources[i].length());
                    path.setOffset(servletPath);
                    internalMapExtensionWrapper(extensionWrappers, path,
                                                mappingData, false);
                }

                path.setOffset(servletPath);
                path.setEnd(pathEnd);
            }
        }


        // Rule 7 -- Default servlet
        if (mappingData.wrapper == null && !checkJspWelcomeFiles) {
            if (contextVersion.defaultWrapper != null) {
                mappingData.wrapper = contextVersion.defaultWrapper.object;
                mappingData.requestPath.setChars
                    (path.getBuffer(), path.getStart(), path.getLength());
                mappingData.wrapperPath.setChars
                    (path.getBuffer(), path.getStart(), path.getLength());
            }
            // Redirection to a folder
            char[] buf = path.getBuffer();
            if (contextVersion.resources != null && buf[pathEnd -1 ] != '/') {
                Object file = null;
                String pathStr = path.toString();
                try {
                    if (pathStr.length() == 0) {
                        file = contextVersion.resources.lookup("/");
                    } else {
                        file = contextVersion.resources.lookup(pathStr);
                    }
                } catch(NamingException nex) {
                    // Swallow, since someone else handles the 404
                }
                if (file != null && file instanceof DirContext &&
                        contextVersion.mapperDirectoryRedirectEnabled) {
                    // Note: this mutates the path: do not do any processing
                    // after this (since we set the redirectPath, there
                    // shouldn't be any)
                    path.setOffset(pathOffset);
                    path.append('/');
                    mappingData.redirectPath.setChars
                        (path.getBuffer(), path.getStart(), path.getLength());
                } else {
                    mappingData.requestPath.setString(pathStr);
                    mappingData.wrapperPath.setString(pathStr);
                }
            }
        }

        path.setOffset(pathOffset);
        path.setEnd(pathEnd);
    }


    /**
     * Exact mapping.
     */
    private final void internalMapExactWrapper
        (Wrapper[] wrappers, CharChunk path, MappingData mappingData) {
        Wrapper wrapper = exactFind(wrappers, path);
        if (wrapper != null) {
            mappingData.requestPath.setString(wrapper.name);
            mappingData.wrapper = wrapper.object;
            if (path.equals("/")) {
                // Special handling for Context Root mapped servlet
                mappingData.pathInfo.setString("/");
                mappingData.wrapperPath.setString("");
                // This seems wrong but it is what the spec says...
                mappingData.contextPath.setString("");
            } else {
                mappingData.wrapperPath.setString(wrapper.name);
            }
        }
    }


    /**
     * Wildcard mapping.
     */
    private final void internalMapWildcardWrapper
        (Wrapper[] wrappers, int nesting, CharChunk path,
         MappingData mappingData) {

        int pathEnd = path.getEnd();

        int lastSlash = -1;
        int length = -1;
        int pos = find(wrappers, path);
        if (pos != -1) {
            boolean found = false;
            while (pos >= 0) {
                if (path.startsWith(wrappers[pos].name)) {
                    length = wrappers[pos].name.length();
                    if (path.getLength() == length) {
                        found = true;
                        break;
                    } else if (path.startsWithIgnoreCase("/", length)) {
                        found = true;
                        break;
                    }
                }
                if (lastSlash == -1) {
                    lastSlash = nthSlash(path, nesting + 1);
                } else {
                    lastSlash = lastSlash(path);
                }
                path.setEnd(lastSlash);
                pos = find(wrappers, path);
            }
            path.setEnd(pathEnd);
            if (found) {
                mappingData.wrapperPath.setString(wrappers[pos].name);
                if (path.getLength() > length) {
                    mappingData.pathInfo.setChars
                        (path.getBuffer(),
                         path.getOffset() + length,
                         path.getLength() - length);
                }
                mappingData.requestPath.setChars
                    (path.getBuffer(), path.getOffset(), path.getLength());
                mappingData.wrapper = wrappers[pos].object;
                mappingData.jspWildCard = wrappers[pos].jspWildCard;
            }
        }
    }


    /**
     * Extension mappings.
     *
     * @param wrappers          Set of wrappers to check for matches
     * @param path              Path to map
     * @param mappingData       Mapping data for result
     * @param resourceExpected  Is this mapping expecting to find a resource
     */
    private final void internalMapExtensionWrapper(Wrapper[] wrappers,
            CharChunk path, MappingData mappingData, boolean resourceExpected) {
        char[] buf = path.getBuffer();
        int pathEnd = path.getEnd();
        int servletPath = path.getOffset();
        int slash = -1;
        for (int i = pathEnd - 1; i >= servletPath; i--) {
            if (buf[i] == '/') {
                slash = i;
                break;
            }
        }
        if (slash >= 0) {
            int period = -1;
            for (int i = pathEnd - 1; i > slash; i--) {
                if (buf[i] == '.') {
                    period = i;
                    break;
                }
            }
            if (period >= 0) {
                path.setOffset(period + 1);
                path.setEnd(pathEnd);
                Wrapper wrapper = exactFind(wrappers, path);
                if (wrapper != null
                        && (resourceExpected || !wrapper.resourceOnly)) {
                    mappingData.wrapperPath.setChars(buf, servletPath, pathEnd
                            - servletPath);
                    mappingData.requestPath.setChars(buf, servletPath, pathEnd
                            - servletPath);
                    mappingData.wrapper = wrapper.object;
                }
                path.setOffset(servletPath);
                path.setEnd(pathEnd);
            }
        }
    }


    /**
     * Find a map element given its name in a sorted array of map elements.
     * This will return the index for the closest inferior or equal item in the
     * given array.
     */
    private static final int find(MapElement[] map, CharChunk name) {
        return find(map, name, name.getStart(), name.getEnd());
    }


    /**
     * Find a map element given its name in a sorted array of map elements.
     * This will return the index for the closest inferior or equal item in the
     * given array.
     */
    private static final int find(MapElement[] map, CharChunk name,
                                  int start, int end) {

        int a = 0;
        int b = map.length - 1;

        // Special cases: -1 and 0
        if (b == -1) {
            return -1;
        }

        if (compare(name, start, end, map[0].name) < 0 ) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }

        int i = 0;
        while (true) {
            i = (b + a) / 2;
            int result = compare(name, start, end, map[i].name);
            if (result == 1) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
            if ((b - a) == 1) {
                int result2 = compare(name, start, end, map[b].name);
                if (result2 < 0) {
                    return a;
                } else {
                    return b;
                }
            }
        }

    }

    /**
     * Find a map element given its name in a sorted array of map elements.
     * This will return the index for the closest inferior or equal item in the
     * given array.
     */
    private static final int findIgnoreCase(MapElement[] map, CharChunk name) {
        return findIgnoreCase(map, name, name.getStart(), name.getEnd());
    }


    /**
     * Find a map element given its name in a sorted array of map elements.
     * This will return the index for the closest inferior or equal item in the
     * given array.
     */
    private static final int findIgnoreCase(MapElement[] map, CharChunk name,
                                  int start, int end) {

        int a = 0;
        int b = map.length - 1;

        // Special cases: -1 and 0
        if (b == -1) {
            return -1;
        }
        if (compareIgnoreCase(name, start, end, map[0].name) < 0 ) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }

        int i = 0;
        while (true) {
            i = (b + a) / 2;
            int result = compareIgnoreCase(name, start, end, map[i].name);
            if (result == 1) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
            if ((b - a) == 1) {
                int result2 = compareIgnoreCase(name, start, end, map[b].name);
                if (result2 < 0) {
                    return a;
                } else {
                    return b;
                }
            }
        }

    }


    /**
     * Find a map element given its name in a sorted array of map elements.
     * This will return the index for the closest inferior or equal item in the
     * given array.
     * @see #exactFind(MapElement[], String)
     */
    private static final int find(MapElement[] map, String name) {

        int a = 0;
        int b = map.length - 1;

        // Special cases: -1 and 0
        if (b == -1) {
            return -1;
        }

        if (name.compareTo(map[0].name) < 0) {
            return -1;
        }
        if (b == 0) {
            return 0;
        }

        int i = 0;
        while (true) {
            i = (b + a) / 2;
            int result = name.compareTo(map[i].name);
            if (result > 0) {
                a = i;
            } else if (result == 0) {
                return i;
            } else {
                b = i;
            }
            if ((b - a) == 1) {
                int result2 = name.compareTo(map[b].name);
                if (result2 < 0) {
                    return a;
                } else {
                    return b;
                }
            }
        }

    }


    /**
     * Find a map element given its name in a sorted array of map elements. This
     * will return the element that you were searching for. Otherwise it will
     * return <code>null</code>.
     * @see #find(MapElement[], String)
     */
    private static final <E extends MapElement> E exactFind(E[] map,
            String name) {
        int pos = find(map, name);
        if (pos >= 0) {
            E result = map[pos];
            if (name.equals(result.name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Find a map element given its name in a sorted array of map elements. This
     * will return the element that you were searching for. Otherwise it will
     * return <code>null</code>.
     */
    private static final <E extends MapElement> E exactFind(E[] map,
            CharChunk name) {
        int pos = find(map, name);
        if (pos >= 0) {
            E result = map[pos];
            if (name.equals(result.name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Find a map element given its name in a sorted array of map elements. This
     * will return the element that you were searching for. Otherwise it will
     * return <code>null</code>.
     * @see #findIgnoreCase(MapElement[], CharChunk)
     */
    private static final <E extends MapElement> E exactFindIgnoreCase(E[] map,
            CharChunk name) {
        int pos = findIgnoreCase(map, name);
        if (pos >= 0) {
            E result = map[pos];
            if (name.equalsIgnoreCase(result.name)) {
                return result;
            }
        }
        return null;
    }


    /**
     * Compare given char chunk with String.
     * Return -1, 0 or +1 if inferior, equal, or superior to the String.
     */
    private static final int compare(CharChunk name, int start, int end,
                                     String compareTo) {
        int result = 0;
        char[] c = name.getBuffer();
        int len = compareTo.length();
        if ((end - start) < len) {
            len = end - start;
        }
        for (int i = 0; (i < len) && (result == 0); i++) {
            if (c[i + start] > compareTo.charAt(i)) {
                result = 1;
            } else if (c[i + start] < compareTo.charAt(i)) {
                result = -1;
            }
        }
        if (result == 0) {
            if (compareTo.length() > (end - start)) {
                result = -1;
            } else if (compareTo.length() < (end - start)) {
                result = 1;
            }
        }
        return result;
    }


    /**
     * Compare given char chunk with String ignoring case.
     * Return -1, 0 or +1 if inferior, equal, or superior to the String.
     */
    private static final int compareIgnoreCase(CharChunk name, int start, int end,
                                     String compareTo) {
        int result = 0;
        char[] c = name.getBuffer();
        int len = compareTo.length();
        if ((end - start) < len) {
            len = end - start;
        }
        for (int i = 0; (i < len) && (result == 0); i++) {
            if (Ascii.toLower(c[i + start]) > Ascii.toLower(compareTo.charAt(i))) {
                result = 1;
            } else if (Ascii.toLower(c[i + start]) < Ascii.toLower(compareTo.charAt(i))) {
                result = -1;
            }
        }
        if (result == 0) {
            if (compareTo.length() > (end - start)) {
                result = -1;
            } else if (compareTo.length() < (end - start)) {
                result = 1;
            }
        }
        return result;
    }


    /**
     * Find the position of the last slash in the given char chunk.
     */
    private static final int lastSlash(CharChunk name) {

        char[] c = name.getBuffer();
        int end = name.getEnd();
        int start = name.getStart();
        int pos = end;

        while (pos > start) {
            if (c[--pos] == '/') {
                break;
            }
        }

        return (pos);

    }


    /**
     * Find the position of the nth slash, in the given char chunk.
     */
    private static final int nthSlash(CharChunk name, int n) {

        char[] c = name.getBuffer();
        int end = name.getEnd();
        int start = name.getStart();
        int pos = start;
        int count = 0;

        while (pos < end) {
            if ((c[pos++] == '/') && ((++count) == n)) {
                pos--;
                break;
            }
        }

        return (pos);

    }


    /**
     * Return the slash count in a given string.
     */
    private static final int slashCount(String name) {
        int pos = -1;
        int count = 0;
        while ((pos = name.indexOf('/', pos + 1)) != -1) {
            count++;
        }
        return count;
    }


    /**
     * Insert into the right place in a sorted MapElement array, and prevent
     * duplicates.
     */
    private static final boolean insertMap
        (MapElement[] oldMap, MapElement[] newMap, MapElement newElement) {
        int pos = find(oldMap, newElement.name);
        if ((pos != -1) && (newElement.name.equals(oldMap[pos].name))) {
            return false;
        }
        System.arraycopy(oldMap, 0, newMap, 0, pos + 1);
        newMap[pos + 1] = newElement;
        System.arraycopy
            (oldMap, pos + 1, newMap, pos + 2, oldMap.length - pos - 1);
        return true;
    }


    /**
     * Insert into the right place in a sorted MapElement array.
     */
    private static final boolean removeMap
        (MapElement[] oldMap, MapElement[] newMap, String name) {
        int pos = find(oldMap, name);
        if ((pos != -1) && (name.equals(oldMap[pos].name))) {
            System.arraycopy(oldMap, 0, newMap, 0, pos);
            System.arraycopy(oldMap, pos + 1, newMap, pos,
                             oldMap.length - pos - 1);
            return true;
        }
        return false;
    }


    // ------------------------------------------------- MapElement Inner Class


    protected abstract static class MapElement {

        public final String name;
        public final Object object;

        public MapElement(String name, Object object) {
            this.name = name;
            this.object = object;
        }
    }


    // ------------------------------------------------------- Host Inner Class


    protected static final class Host extends MapElement {

        public volatile ContextList contextList;

        /**
         * Link to the "real" Host, shared by all aliases.
         */
        private final Host realHost;

        /**
         * Links to all registered aliases, for easy enumeration. This field
         * is available only in the "real" Host. In an alias this field
         * is <code>null</code>.
         */
        private final List<Host> aliases;

        /**
         * Creates an object for primary Host
         */
        public Host(String name, Object host) {
            super(name, host);
            this.realHost = this;
            this.contextList = new ContextList();
            this.aliases = new CopyOnWriteArrayList<Host>();
        }

        /**
         * Creates an object for an Alias
         */
        public Host(String alias, Host realHost) {
            super(alias, realHost.object);
            this.realHost = realHost;
            this.contextList = realHost.contextList;
            this.aliases = null;
        }

        public boolean isAlias() {
            return realHost != this;
        }

        public Host getRealHost() {
            return realHost;
        }

        public String getRealHostName() {
            return realHost.name;
        }

        public Collection<Host> getAliases() {
            return aliases;
        }

        public void addAlias(Host alias) {
            aliases.add(alias);
        }

        public void addAliases(Collection<? extends Host> c) {
            aliases.addAll(c);
        }

        public void removeAlias(Host alias) {
            aliases.remove(alias);
        }
    }


    // ------------------------------------------------ ContextList Inner Class


    protected static final class ContextList {

        public final Context[] contexts;
        public final int nesting;

        public ContextList() {
            this(new Context[0], 0);
        }

        private ContextList(Context[] contexts, int nesting) {
            this.contexts = contexts;
            this.nesting = nesting;
        }

        public ContextList addContext(Context mappedContext, int slashCount) {
            Context[] newContexts = new Context[contexts.length + 1];
            if (insertMap(contexts, newContexts, mappedContext)) {
                return new ContextList(newContexts, Math.max(nesting,
                        slashCount));
            }
            return null;
        }

        public ContextList removeContext(String path) {
            Context[] newContexts = new Context[contexts.length - 1];
            if (removeMap(contexts, newContexts, path)) {
                int newNesting = 0;
                for (Context context : newContexts) {
                    newNesting = Math.max(newNesting, slashCount(context.name));
                }
                return new ContextList(newContexts, newNesting);
            }
            return null;
        }
    }


    // ---------------------------------------------------- Context Inner Class


    protected static final class Context extends MapElement {
        public volatile ContextVersion[] versions;

        public Context(String name, ContextVersion firstVersion) {
            super(name, null);
            versions = new ContextVersion[] { firstVersion };
        }
    }


    protected static final class ContextVersion extends MapElement {
        public String path = null;
        public int slashCount;
        public String[] welcomeResources = new String[0];
        public javax.naming.Context resources = null;
        public Wrapper defaultWrapper = null;
        public Wrapper[] exactWrappers = new Wrapper[0];
        public Wrapper[] wildcardWrappers = new Wrapper[0];
        public Wrapper[] extensionWrappers = new Wrapper[0];
        public int nesting = 0;
        public boolean mapperContextRootRedirectEnabled = false;
        public boolean mapperDirectoryRedirectEnabled = false;
        private volatile boolean paused;

        public ContextVersion() {
            super(null, null);
        }

        public ContextVersion(String version, Object context) {
            super(version, context);
        }

        public boolean isPaused() {
            return paused;
        }

        public void markPaused() {
            paused = true;
        }
    }


    // ---------------------------------------------------- Wrapper Inner Class


    protected static class Wrapper extends MapElement {

        public final boolean jspWildCard;
        public final boolean resourceOnly;

        public Wrapper(String name, /* Wrapper */Object wrapper,
                boolean jspWildCard, boolean resourceOnly) {
            super(name, wrapper);
            this.jspWildCard = jspWildCard;
            this.resourceOnly = resourceOnly;
        }
    }
}
