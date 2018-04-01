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


package org.apache.catalina.deploy;


import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.tomcat.util.res.StringManager;


/**
 * Representation of a servlet definition for a web application, as represented
 * in a <code>&lt;servlet&gt;</code> element in the deployment descriptor.
 */

public class ServletDef implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final StringManager sm =
        StringManager.getManager(Constants.Package);

    // ------------------------------------------------------------- Properties


    /**
     * The description of this servlet.
     */
    private String description = null;

    public String getDescription() {
        return (this.description);
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * The display name of this servlet.
     */
    private String displayName = null;

    public String getDisplayName() {
        return (this.displayName);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    /**
     * The small icon associated with this servlet.
     */
    private String smallIcon = null;

    public String getSmallIcon() {
        return (this.smallIcon);
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }
    
    /**
     * The large icon associated with this servlet.
     */
    private String largeIcon = null;

    public String getLargeIcon() {
        return (this.largeIcon);
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }


    /**
     * The name of this servlet, which must be unique among the servlets
     * defined for a particular web application.
     */
    private String servletName = null;

    public String getServletName() {
        return (this.servletName);
    }

    public void setServletName(String servletName) {
        if (servletName == null || servletName.equals("")) {
            throw new IllegalArgumentException(
                    sm.getString("servletDef.invalidServletName", servletName));
        }
        this.servletName = servletName;
    }


    /**
     * The fully qualified name of the Java class that implements this servlet.
     */
    private String servletClass = null;

    public String getServletClass() {
        return (this.servletClass);
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }


    /**
     * The name of the JSP file to which this servlet definition applies
     */
    private String jspFile = null;

    public String getJspFile() {
        return (this.jspFile);
    }

    public void setJspFile(String jspFile) {
        this.jspFile = jspFile;
    }


    /**
     * The set of initialization parameters for this servlet, keyed by
     * parameter name.
     */
    private Map<String, String> parameters = new HashMap<String, String>();

    public Map<String, String> getParameterMap() {

        return (this.parameters);

    }

    /**
     * Add an initialization parameter to the set of parameters associated
     * with this servlet.
     *
     * @param name The initialisation parameter name
     * @param value The initialisation parameter value
     */
    public void addInitParameter(String name, String value) {

        if (parameters.containsKey(name)) {
            // The spec does not define this but the TCK expects the first
            // definition to take precedence
            return;
        }
        parameters.put(name, value);

    }

    /**
     * The load-on-startup order for this servlet
     */
    private Integer loadOnStartup = null;

    public Integer getLoadOnStartup() {
        return (this.loadOnStartup);
    }

    public void setLoadOnStartup(String loadOnStartup) {
        this.loadOnStartup = Integer.valueOf(loadOnStartup);
    }


    /**
     * The run-as configuration for this servlet
     */
    private String runAs = null;

    public String getRunAs() {
        return (this.runAs);
    }

    public void setRunAs(String runAs) {
        this.runAs = runAs;
    }


    /**
     * The set of security role references for this servlet
     */
    private Set<SecurityRoleRef> securityRoleRefs =
        new HashSet<SecurityRoleRef>();

    public Set<SecurityRoleRef> getSecurityRoleRefs() {
        return (this.securityRoleRefs);
    }

    /**
     * Add a security-role-ref to the set of security-role-refs associated
     * with this servlet.
     */
    public void addSecurityRoleRef(SecurityRoleRef securityRoleRef) {
        securityRoleRefs.add(securityRoleRef);
    }

    /**
     * Add a security-role-ref to the set of security-role-refs associated
     * with this servlet.
     * @deprecated
     */
    @Deprecated
    public void addSecurityRoleRef(String roleName, String roleLink) {
        SecurityRoleRef srr = new SecurityRoleRef();
        srr.setName(roleName);
        srr.setLink(roleLink);
        securityRoleRefs.add(srr);
    }

    
    /**
     * The multipart configuration, if any, for this servlet
     */
    private MultipartDef multipartDef = null;
    
    public MultipartDef getMultipartDef() {
        return this.multipartDef;
    }
    
    public void setMultipartDef(MultipartDef multipartDef) {
        this.multipartDef = multipartDef;
    }
    
    
    /**
     * Does this servlet support async.
     */
    private Boolean asyncSupported = null;
    
    public Boolean getAsyncSupported() {
        return this.asyncSupported;
    }
    
    public void setAsyncSupported(String asyncSupported) {
        this.asyncSupported = Boolean.valueOf(asyncSupported);
    }

    
    /**
     * Is this servlet enabled.
     */
    private Boolean enabled = null;
    
    public Boolean getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(String enabled) {
        this.enabled = Boolean.valueOf(enabled);
    }

    
    /**
     * Can this ServletDef be overridden by an SCI?
     */
    private boolean overridable = false;
    
    public boolean isOverridable() {
        return overridable;
    }

    public void setOverridable(boolean overridable) {
        this.overridable = overridable;
    }

}
