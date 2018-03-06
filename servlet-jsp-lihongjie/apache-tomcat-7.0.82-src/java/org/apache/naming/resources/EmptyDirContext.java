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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * A {@link DirContext} implementation that is not backed by a file system
 * and behaves as if it has no resources available. This is primarily used in
 * embedded mode when the web application is configured entirely
 * programmatically and does not use any static resources from the file system.
 * EmptyDirContext is implemented as a read only context.
 */
public class EmptyDirContext implements DirContext {

    /**
     * Static field to avoid useless object creation
     */
    private static final Attributes emptyAttributes = new BasicAttributes();

    private static final NameNotFoundException nameNotFoundException = new ImmutableNameNotFoundException();

    private static final Name emptyName = new CompositeName();

    private static final Hashtable<?,?> emptyEnv = new Hashtable<Object,Object>();

    private static final String emptyString = "";

    /**
     * Non-static field to avoid useless object creation
     */
    @SuppressWarnings("rawtypes")
    private final NamingEnumeration emptyEnum = new EmptyNamingEnumImpl();

    private final NameParser nameParser = new NameParserImpl();

    @Override
    public Attributes getAttributes(Name name) throws NamingException {
        return emptyAttributes;
    }

    @Override
    public Attributes getAttributes(String name) throws NamingException {
        return emptyAttributes;
    }

    @Override
    public Attributes getAttributes(Name name, String[] attrIds) throws NamingException {
        return emptyAttributes;
    }

    @Override
    public Attributes getAttributes(String name, String[] attrIds) throws NamingException {
        return emptyAttributes;
    }

    @Override
    public void modifyAttributes(Name name, int mod_op, Attributes attrs) throws NamingException {
        // no op
    }

    @Override
    public void modifyAttributes(String name, int mod_op, Attributes attrs) throws NamingException {
        // no op
    }

    @Override
    public void modifyAttributes(Name name, ModificationItem[] mods) throws NamingException {
        // no op
    }

    @Override
    public void modifyAttributes(String name, ModificationItem[] mods) throws NamingException {
        // no op
    }

    @Override
    public void bind(Name name, Object obj, Attributes attrs) throws NamingException {
        // no op
    }

    @Override
    public void bind(String name, Object obj, Attributes attrs) throws NamingException {
        // no op
    }

    @Override
    public void rebind(Name name, Object obj, Attributes attrs) throws NamingException {
        // no op
    }

    @Override
    public void rebind(String name, Object obj, Attributes attrs) throws NamingException {
        // no op
    }

    @Override
    public DirContext createSubcontext(Name name, Attributes attrs) throws NamingException {
        return this;
    }

    @Override
    public DirContext createSubcontext(String name, Attributes attrs) throws NamingException {
        return this;
    }

    @Override
    public DirContext getSchema(Name name) throws NamingException {
        return this;
    }

    @Override
    public DirContext getSchema(String name) throws NamingException {
        return this;
    }

    @Override
    public DirContext getSchemaClassDefinition(Name name) throws NamingException {
        return this;
    }

    @Override
    public DirContext getSchemaClassDefinition(String name) throws NamingException {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<SearchResult> search(Name name, Attributes matchingAttributes,
            String[] attributesToReturn) throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<SearchResult> search(String name, Attributes matchingAttributes,
            String[] attributesToReturn) throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<SearchResult> search(Name name, Attributes matchingAttributes)
            throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<SearchResult> search(String name, Attributes matchingAttributes)
            throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<SearchResult> search(Name name, String filter, SearchControls cons)
            throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<SearchResult> search(String name, String filter, SearchControls cons)
            throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<SearchResult> search(Name name, String filterExpr,
            Object[] filterArgs, SearchControls cons) throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<SearchResult> search(String name, String filterExpr,
            Object[] filterArgs, SearchControls cons) throws NamingException {
        return emptyEnum;
    }

    @Override
    public Object lookup(Name name) throws NamingException {
        throw nameNotFoundException;
    }

    @Override
    public Object lookup(String name) throws NamingException {
        throw nameNotFoundException;
    }

    @Override
    public void bind(Name name, Object obj) throws NamingException {
        // no op
    }

    @Override
    public void bind(String name, Object obj) throws NamingException {
        // no op
    }

    @Override
    public void rebind(Name name, Object obj) throws NamingException {
        // no op
    }

    @Override
    public void rebind(String name, Object obj) throws NamingException {
        // no op
    }

    @Override
    public void unbind(Name name) throws NamingException {
        // no op
    }

    @Override
    public void unbind(String name) throws NamingException {
        // no op
    }

    @Override
    public void rename(Name oldName, Name newName) throws NamingException {
        // no op
    }

    @Override
    public void rename(String oldName, String newName) throws NamingException {
        // no op
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return emptyEnum;
    }

    @Override
    @SuppressWarnings("unchecked")
    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        return emptyEnum;
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {
        // no op
    }

    @Override
    public void destroySubcontext(String name) throws NamingException {
        // no op
    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        return this;
    }

    @Override
    public Context createSubcontext(String name) throws NamingException {
        return this;
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        throw nameNotFoundException;
    }

    @Override
    public Object lookupLink(String name) throws NamingException {
        throw nameNotFoundException;
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return nameParser;
    }

    @Override
    public NameParser getNameParser(String name) throws NamingException {
        return nameParser;
    }

    @Override
    public Name composeName(Name name, Name prefix) throws NamingException {
        return emptyName;
    }

    @Override
    public String composeName(String name, String prefix) throws NamingException {
        return emptyString;
    }

    @Override
    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        return null;
    }

    @Override
    public Object removeFromEnvironment(String propName) throws NamingException {
        return null;
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return emptyEnv;
    }

    @Override
    public void close() throws NamingException {
        // NO OP
    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return emptyString;
    }

    static class EmptyNamingEnumImpl<T> implements NamingEnumeration<T> {

        Enumeration<T> elements = new Vector<T>().elements();

        @Override
        public T next() throws NamingException {
            return nextElement();
        }

        @Override
        public boolean hasMore() throws NamingException {
            return hasMoreElements();
        }

        @Override
        public void close() throws NamingException {
            elements = null;
        }

        @Override
        public boolean hasMoreElements() {
            return elements.hasMoreElements();
        }

        @Override
        public T nextElement() {
            return elements.nextElement();
        }
    }

    static class NameParserImpl implements NameParser {

        @Override
        public Name parse(String name) throws NamingException {
            return emptyName;
        }
    }

}