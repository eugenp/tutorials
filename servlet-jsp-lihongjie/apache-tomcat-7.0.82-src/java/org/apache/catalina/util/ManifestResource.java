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
package org.apache.catalina.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 *  Representation of a Manifest file and its available extensions and
 *  required extensions
 *  
 * @author Greg Murray
 * @author Justyna Horwat
 */
public class ManifestResource {
    
    // ------------------------------------------------------------- Properties

    // These are the resource types for determining effect error messages
    public static final int SYSTEM = 1;
    public static final int WAR = 2;
    public static final int APPLICATION = 3;
    
    private ArrayList<Extension> availableExtensions = null;
    private ArrayList<Extension> requiredExtensions = null;
    
    private String resourceName = null;
    private int resourceType = -1;
        
    public ManifestResource(String resourceName, Manifest manifest, 
                            int resourceType) {
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        processManifest(manifest);
    }
    
    /**
     * Gets the name of the resource
     *
     * @return The name of the resource
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Gets the list of available extensions
     *
     * @return List of available extensions
     */
    public ArrayList<Extension> getAvailableExtensions() {
        return availableExtensions;
    }
    
    /**
     * Gets the list of required extensions
     *
     * @return List of required extensions
     */
    public ArrayList<Extension> getRequiredExtensions() {
        return requiredExtensions;   
    }
    
    // --------------------------------------------------------- Public Methods

    /**
     * Gets the number of available extensions
     *
     * @return The number of available extensions
     */
    public int getAvailableExtensionCount() {
        return (availableExtensions != null) ? availableExtensions.size() : 0;
    }
    
    /**
     * Gets the number of required extensions
     *
     * @return The number of required extensions
     */
    public int getRequiredExtensionCount() {
        return (requiredExtensions != null) ? requiredExtensions.size() : 0;
    }
    
    /**
     * Convenience method to check if this <code>ManifestResource</code>
     * has an requires extensions.
     *
     * @return true if required extensions are present
     */
    public boolean requiresExtensions() {
        return (requiredExtensions != null) ? true : false;
    }
    
    /**
     * Returns <code>true</code> if all required extension dependencies
     * have been meet for this <code>ManifestResource</code> object.
     *
     * @return boolean true if all extension dependencies have been satisfied
     */
    public boolean isFulfilled() {
        if (requiredExtensions == null) {
            return true;
        }
        Iterator<Extension> it = requiredExtensions.iterator();
        while (it.hasNext()) {
            Extension ext = it.next();
            if (!ext.isFulfilled()) return false;            
        }
        return true;
    }
    
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("ManifestResource[");
        sb.append(resourceName);

        sb.append(", isFulfilled=");
        sb.append(isFulfilled() +"");
        sb.append(", requiredExtensionCount =");
        sb.append(getRequiredExtensionCount());
        sb.append(", availableExtensionCount=");
        sb.append(getAvailableExtensionCount());
        switch (resourceType) {
            case SYSTEM : sb.append(", resourceType=SYSTEM"); break;
            case WAR : sb.append(", resourceType=WAR"); break;
            case APPLICATION : sb.append(", resourceType=APPLICATION"); break;
        }
        sb.append("]");
        return (sb.toString());
    }


    // -------------------------------------------------------- Private Methods

    private void processManifest(Manifest manifest) {
        availableExtensions = getAvailableExtensions(manifest);
        requiredExtensions = getRequiredExtensions(manifest);
    }
    
    /**
     * Return the set of <code>Extension</code> objects representing optional
     * packages that are required by the application associated with the
     * specified <code>Manifest</code>.
     *
     * @param manifest Manifest to be parsed
     *
     * @return List of required extensions, or null if the application
     * does not require any extensions
     */
    private ArrayList<Extension> getRequiredExtensions(Manifest manifest) {

        Attributes attributes = manifest.getMainAttributes();
        String names = attributes.getValue("Extension-List");
        if (names == null)
            return null;

        ArrayList<Extension> extensionList = new ArrayList<Extension>();
        names += " ";

        while (true) {

            int space = names.indexOf(' ');
            if (space < 0)
                break;
            String name = names.substring(0, space).trim();
            names = names.substring(space + 1);

            String value =
                attributes.getValue(name + "-Extension-Name");
            if (value == null)
                continue;
            Extension extension = new Extension();
            extension.setExtensionName(value);
            extension.setImplementationURL
                (attributes.getValue(name + "-Implementation-URL"));
            extension.setImplementationVendorId
                (attributes.getValue(name + "-Implementation-Vendor-Id"));
            String version = attributes.getValue(name + "-Implementation-Version");
            extension.setImplementationVersion(version);
            extension.setSpecificationVersion
                (attributes.getValue(name + "-Specification-Version"));
            extensionList.add(extension);
        }
        return extensionList;
    }
    
    /**
     * Return the set of <code>Extension</code> objects representing optional
     * packages that are bundled with the application associated with the
     * specified <code>Manifest</code>.
     *
     * @param manifest Manifest to be parsed
     *
     * @return List of available extensions, or null if the web application
     * does not bundle any extensions
     */
    private ArrayList<Extension> getAvailableExtensions(Manifest manifest) {

        Attributes attributes = manifest.getMainAttributes();
        String name = attributes.getValue("Extension-Name");
        if (name == null)
            return null;

        ArrayList<Extension> extensionList = new ArrayList<Extension>();

        Extension extension = new Extension();
        extension.setExtensionName(name);
        extension.setImplementationURL(
            attributes.getValue("Implementation-URL"));
        extension.setImplementationVendor(
            attributes.getValue("Implementation-Vendor"));
        extension.setImplementationVendorId(
            attributes.getValue("Implementation-Vendor-Id"));
        extension.setImplementationVersion(
            attributes.getValue("Implementation-Version"));
        extension.setSpecificationVersion(
            attributes.getValue("Specification-Version"));

        extensionList.add(extension);

        return extensionList;
    }
    
}
