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
package org.apache.tomcat.util.modeler.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.management.ObjectName;

import org.apache.tomcat.util.modeler.Registry;

/** Source for descriptor data. More sources can be added.
 *
 */
public abstract class ModelerSource {
    protected Object source;
    @Deprecated
    protected String location;

    /** Load data, returns a list of items. 
     * 
     * @param registry
     * @param location
     * @param type
     * @param source Introspected object or some other source
     * @throws Exception
     *
     * @deprecated  Location parameter is unused. Will be removed in Tomcat
     *              8.0.x
     */
    @Deprecated
    public List<ObjectName> loadDescriptors( Registry registry, String location,
            String type, Object source) throws Exception {
        return loadDescriptors(registry, type, source);
    }
    
    /** Callback from the BaseMBean to notify that an attribute has changed.
     * Can be used to implement persistence.
     * 
     * @param oname
     * @param name
     * @param value
     * @deprecated Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public void updateField( ObjectName oname, String name,
                             Object value ) {
        // nothing by default 
    }

    /**
     *
     * @deprecated Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    public void store() {
        // nothing
    }

    /**
     * @deprecated Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    protected InputStream getInputStream() throws IOException {
        if( source instanceof URL ) {
            URL url=(URL)source;
            location=url.toString();
            return url.openStream();
        } else if( source instanceof File ) {
            location=((File)source).getAbsolutePath();
            return new FileInputStream((File)source);            
        } else if( source instanceof String) {
            location=(String)source;
            return new FileInputStream((String)source);            
        } else if( source instanceof InputStream ) {
            return (InputStream)source;
        } 
        return null;
    }


    public abstract List<ObjectName> loadDescriptors(Registry registry,
            String type, Object source) throws Exception;
}
