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


package org.apache.catalina.tribes.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * Custom subclass of <code>ObjectInputStream</code> that loads from the
 * class loader for this web application.  This allows classes defined only
 * with the web application to be found correctly.
 *
 * @author Craig R. McClanahan
 * @author Bip Thelin
 * @author Filip Hanik
 */
public final class ReplicationStream extends ObjectInputStream {

    
    /**
     * The class loader we will use to resolve classes.
     */
    private ClassLoader[] classLoaders = null;
    
    /**
     * Construct a new instance of CustomObjectInputStream
     *
     * @param stream The input stream we will read from
     * @param classLoaders The class loader array used to instantiate objects
     *
     * @exception IOException if an input/output error occurs
     */
    public ReplicationStream(InputStream stream,
                             ClassLoader[] classLoaders)
        throws IOException {

        super(stream);
        this.classLoaders = classLoaders;
    }

    /**
     * Load the local class equivalent of the specified stream class
     * description, by using the class loader assigned to this Context.
     *
     * @param classDesc Class description from the input stream
     *
     * @exception ClassNotFoundException if this class cannot be found
     * @exception IOException if an input/output error occurs
     */
    @Override
    public Class<?> resolveClass(ObjectStreamClass classDesc)
        throws ClassNotFoundException, IOException {
        String name = classDesc.getName();
        try {
            return resolveClass(name);
        } catch (ClassNotFoundException e) {
            return super.resolveClass(classDesc);
        }
    }
    
    public Class<?> resolveClass(String name)
        throws ClassNotFoundException, IOException {

        boolean tryRepFirst = name.startsWith("org.apache.catalina.tribes");
            try {
            if (tryRepFirst)
                return findReplicationClass(name);
            else
                return findExternalClass(name);
        } catch (Exception x) {
            if (tryRepFirst)
                return findExternalClass(name);
            else
                return findReplicationClass(name);
        }
    }
    
    /**
     * ObjectInputStream.resolveProxyClass has some funky way of using 
     * the incorrect class loader to resolve proxy classes, let's do it our way instead
     */
    @Override
    protected Class<?> resolveProxyClass(String[] interfaces)
            throws IOException, ClassNotFoundException {
        
        ClassLoader latestLoader;
        if (classLoaders != null && classLoaders.length > 0) {
            latestLoader = classLoaders[0];
        } else {
            latestLoader = null;
        }
        ClassLoader nonPublicLoader = null;
        boolean hasNonPublicInterface = false;

        // define proxy in class loader of non-public interface(s), if any
        Class<?>[] classObjs = new Class[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            Class<?> cl = this.resolveClass(interfaces[i]);
            if (latestLoader==null) latestLoader = cl.getClassLoader();
            if ((cl.getModifiers() & Modifier.PUBLIC) == 0) {
                if (hasNonPublicInterface) {
                    if (nonPublicLoader != cl.getClassLoader()) {
                        throw new IllegalAccessError(
                                "conflicting non-public interface class loaders");
                    }
                } else {
                    nonPublicLoader = cl.getClassLoader();
                    hasNonPublicInterface = true;
                }
            }
            classObjs[i] = cl;
        }
        try {
            return Proxy.getProxyClass(hasNonPublicInterface ? nonPublicLoader
                    : latestLoader, classObjs);
        } catch (IllegalArgumentException e) {
            throw new ClassNotFoundException(null, e);
        }
    }

    
    public Class<?> findReplicationClass(String name)
        throws ClassNotFoundException, IOException {
        Class<?> clazz = Class.forName(name, false, getClass().getClassLoader());
        return clazz;
    }

    public Class<?> findExternalClass(String name) throws ClassNotFoundException  {
        ClassNotFoundException cnfe = null;
        for (int i=0; i<classLoaders.length; i++ ) {
            try {
                Class<?> clazz = Class.forName(name, false, classLoaders[i]);
                return clazz;
            } catch ( ClassNotFoundException x ) {
                cnfe = x;
            } 
        }
        if ( cnfe != null ) throw cnfe;
        else throw new ClassNotFoundException(name);
    }
    
    @Override
    public void close() throws IOException  {
        this.classLoaders = null;
        super.close();
    }


}
