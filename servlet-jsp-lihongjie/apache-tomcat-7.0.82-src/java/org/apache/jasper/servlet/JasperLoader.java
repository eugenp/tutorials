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

package org.apache.jasper.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;

import org.apache.jasper.Constants;

/**
 * Class loader for loading servlet class files (corresponding to JSP files) 
 * and tag handler class files (corresponding to tag files).
 *
 * @author Anil K. Vijendran
 * @author Harish Prabandham
 * @author Jean-Francois Arcand
 */
public class JasperLoader extends URLClassLoader {

    private PermissionCollection permissionCollection;
    private ClassLoader parent;
    private SecurityManager securityManager;

    public JasperLoader(URL[] urls, ClassLoader parent,
                        PermissionCollection permissionCollection) {
        super(urls, parent);
        this.permissionCollection = permissionCollection;
        this.parent = parent;
        this.securityManager = System.getSecurityManager();
    }

    /**
     * Load the class with the specified name.  This method searches for
     * classes in the same manner as <code>loadClass(String, boolean)</code>
     * with <code>false</code> as the second argument.
     *
     * @param name Name of the class to be loaded
     *
     * @exception ClassNotFoundException if the class was not found
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        return (loadClass(name, false));
    }

    /**
     * Load the class with the specified name, searching using the following
     * algorithm until it finds and returns the class.  If the class cannot
     * be found, returns <code>ClassNotFoundException</code>.
     * <ul>
     * <li>Call <code>findLoadedClass(String)</code> to check if the
     *     class has already been loaded.  If it has, the same
     *     <code>Class</code> object is returned.</li>
     * <li>If the <code>delegate</code> property is set to <code>true</code>,
     *     call the <code>loadClass()</code> method of the parent class
     *     loader, if any.</li>            
     * <li>Call <code>findClass()</code> to find this class in our locally
     *     defined repositories.</li>      
     * <li>Call the <code>loadClass()</code> method of our parent
     *     class loader, if any.</li>      
     * </ul>
     * If the class was found using the above steps, and the
     * <code>resolve</code> flag is <code>true</code>, this method will then
     * call <code>resolveClass(Class)</code> on the resulting Class object.
     *                                     
     * @param name Name of the class to be loaded
     * @param resolve If <code>true</code> then resolve the class
     *                                     
     * @exception ClassNotFoundException if the class was not found
     */                                    
    @Override
    public synchronized Class<?> loadClass(final String name, boolean resolve)
        throws ClassNotFoundException {

        Class<?> clazz = null;                
                                           
        // (0) Check our previously loaded class cache
        clazz = findLoadedClass(name);     
        if (clazz != null) {               
            if (resolve)                   
                resolveClass(clazz);       
            return (clazz);        
        }                          
                          
        // (.5) Permission to access this class when using a SecurityManager
        if (securityManager != null) {     
            int dot = name.lastIndexOf('.');
            if (dot >= 0) {                
                try {        
                    // Do not call the security manager since by default, we grant that package.
                    if (!"org.apache.jasper.runtime".equalsIgnoreCase(name.substring(0,dot))){
                        securityManager.checkPackageAccess(name.substring(0,dot));
                    }
                } catch (SecurityException se) {
                    String error = "Security Violation, attempt to use " +
                        "Restricted Class: " + name;
                    se.printStackTrace();
                    throw new ClassNotFoundException(error);
                }                          
            }                              
        }

        if( !name.startsWith(Constants.JSP_PACKAGE_NAME + '.') ) {
            // Class is not in org.apache.jsp, therefore, have our
            // parent load it
            clazz = parent.loadClass(name);            
            if( resolve )
                resolveClass(clazz);
            return clazz;
        }

        return findClass(name);
    }

    
    /**
     * Delegate to parent
     * 
     * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
     */
    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream is = parent.getResourceAsStream(name);
        if (is == null) {
            URL url = findResource(name);
            if (url != null) {
                try {
                    is = url.openStream();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
        return is;
    }
    
    
    /**
     * Get the Permissions for a CodeSource.
     *
     * Since this ClassLoader is only used for a JSP page in
     * a web application context, we just return our preset
     * PermissionCollection for the web app context.
     *
     * @param codeSource Code source where the code was loaded from
     * @return PermissionCollection for CodeSource
     */
    @Override
    public final PermissionCollection getPermissions(CodeSource codeSource) {
        return permissionCollection;
    }
}
