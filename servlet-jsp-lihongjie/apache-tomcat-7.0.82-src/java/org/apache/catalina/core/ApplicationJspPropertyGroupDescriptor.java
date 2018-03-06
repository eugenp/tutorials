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
package org.apache.catalina.core;

import java.util.Collection;

import javax.servlet.descriptor.JspPropertyGroupDescriptor;

import org.apache.catalina.deploy.JspPropertyGroup;


public class ApplicationJspPropertyGroupDescriptor
        implements JspPropertyGroupDescriptor{

    /**
     * @deprecated  Will be made private in 8.0.x
     */
    @Deprecated
    JspPropertyGroup jspPropertyGroup;

    
    public ApplicationJspPropertyGroupDescriptor(
            JspPropertyGroup jspPropertyGroup) {
        this.jspPropertyGroup = jspPropertyGroup;
    }

    
    @Override
    public String getBuffer() {
        return jspPropertyGroup.getBuffer();
    }

    
    @Override
    public String getDefaultContentType() {
        return jspPropertyGroup.getDefaultContentType();
    }

    
    @Override
    public String getDeferredSyntaxAllowedAsLiteral() {
        String result = null;
        
        if (jspPropertyGroup.getDeferredSyntax() != null) {
            result = jspPropertyGroup.getDeferredSyntax().toString();
        }
        
        return result;
    }

    
    @Override
    public String getElIgnored() {
        String result = null;
        
        if (jspPropertyGroup.getElIgnored() != null) {
            result = jspPropertyGroup.getElIgnored().toString();
        }
        
        return result;
    }

    
    @Override
    public String getErrorOnUndeclaredNamespace() {
        String result = null;
        
        if (jspPropertyGroup.getErrorOnUndeclaredNamespace() != null) {
            result =
                jspPropertyGroup.getErrorOnUndeclaredNamespace().toString();
        }
        
        return result;
    }

    
    @Override
    public Collection<String> getIncludeCodas() {
        return jspPropertyGroup.getIncludeCodas();
    }

    
    @Override
    public Collection<String> getIncludePreludes() {
        return jspPropertyGroup.getIncludePreludes();
    }

    
    @Override
    public String getIsXml() {
        String result = null;
        
        if (jspPropertyGroup.getIsXml() != null) {
            result = jspPropertyGroup.getIsXml().toString();
        }
        
        return result;
    }

    
    @Override
    public String getPageEncoding() {
        return jspPropertyGroup.getPageEncoding();
    }

    
    @Override
    public String getScriptingInvalid() {
        String result = null;
        
        if (jspPropertyGroup.getScriptingInvalid() != null) {
            result = jspPropertyGroup.getScriptingInvalid().toString();
        }
        
        return result;
    }

    
    @Override
    public String getTrimDirectiveWhitespaces() {
        String result = null;
        
        if (jspPropertyGroup.getTrimWhitespace() != null) {
            result = jspPropertyGroup.getTrimWhitespace().toString();
        }
        
        return result;
    }

    
    @Override
    public Collection<String> getUrlPatterns() {
        return jspPropertyGroup.getUrlPatterns();
    }
}
