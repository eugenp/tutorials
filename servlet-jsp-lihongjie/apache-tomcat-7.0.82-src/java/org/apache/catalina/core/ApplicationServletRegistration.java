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
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Wrapper;
import org.apache.catalina.util.ParameterMap;
import org.apache.tomcat.util.res.StringManager;

public class ApplicationServletRegistration
        implements ServletRegistration.Dynamic {

    /**
     * The string manager for this package.
     */
    private static final StringManager sm =
      StringManager.getManager(Constants.Package);
    
    private Wrapper wrapper;
    private Context context;
    
    public ApplicationServletRegistration(Wrapper wrapper,
            Context context) {
        this.wrapper = wrapper;
        this.context = context;
        
    }

    @Override
    public String getClassName() {
        return wrapper.getServletClass();
   }

    @Override
    public String getInitParameter(String name) {
        return wrapper.findInitParameter(name);
    }

    @Override
    public Map<String, String> getInitParameters() {
        ParameterMap<String,String> result = new ParameterMap<String,String>();
        
        String[] parameterNames = wrapper.findInitParameters();
        
        for (String parameterName : parameterNames) {
            result.put(parameterName, wrapper.findInitParameter(parameterName));
        }

        result.setLocked(true);
        return result;
    }

    @Override
    public String getName() {
        return wrapper.getName();
    }

    @Override
    public boolean setInitParameter(String name, String value) {
        if (name == null || value == null) {
            throw new IllegalArgumentException(
                    sm.getString("applicationFilterRegistration.nullInitParam",
                            name, value));
        }
        if (getInitParameter(name) != null) {
            return false;
        }
        
        wrapper.addInitParameter(name, value);

        return true;
    }

    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
        
        Set<String> conflicts = new HashSet<String>();
        
        for (Map.Entry<String, String> entry : initParameters.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException(sm.getString(
                        "applicationFilterRegistration.nullInitParams",
                                entry.getKey(), entry.getValue()));
            }
            if (getInitParameter(entry.getKey()) != null) {
                conflicts.add(entry.getKey());
            }
        }

        // Have to add in a separate loop since spec requires no updates at all
        // if there is an issue
        if (conflicts.isEmpty()) {
            for (Map.Entry<String, String> entry : initParameters.entrySet()) {
                setInitParameter(entry.getKey(), entry.getValue());
            }
        }

        return conflicts;
    }

    @Override
    public void setAsyncSupported(boolean asyncSupported) {
        wrapper.setAsyncSupported(asyncSupported);
    }

    @Override
    public void setLoadOnStartup(int loadOnStartup) {
        wrapper.setLoadOnStartup(loadOnStartup);
    }

    @Override
    public void setMultipartConfig(MultipartConfigElement multipartConfig) {
        wrapper.setMultipartConfigElement(multipartConfig);
    }

    @Override
    public void setRunAsRole(String roleName) {
        wrapper.setRunAs(roleName);
    }

    @Override
    public Set<String> setServletSecurity(ServletSecurityElement constraint) {
        if (constraint == null) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationServletRegistration.setServletSecurity.iae",
                    getName(), context.getName()));
        }
        
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(sm.getString(
                    "applicationServletRegistration.setServletSecurity.ise",
                    getName(), context.getName()));
        }

        return context.addServletSecurity(this, constraint);
    }


    @Override
    public Set<String> addMapping(String... urlPatterns) {
        if (urlPatterns == null) {
            return Collections.emptySet();
        }
        
        Set<String> conflicts = new HashSet<String>();
        
        for (String urlPattern : urlPatterns) {
            String wrapperName = context.findServletMapping(urlPattern);
            if (wrapperName != null) {
                Wrapper wrapper = (Wrapper) context.findChild(wrapperName);
                if (wrapper.isOverridable()) {
                    // Some Wrappers (from global and host web.xml) may be
                    // overridden rather than generating a conflict
                    context.removeServletMapping(urlPattern);
                } else {
                    conflicts.add(urlPattern);
                }
            }
        }

        if (!conflicts.isEmpty()) {
            return conflicts;
        }
        
        for (String urlPattern : urlPatterns) {
            context.addServletMapping(urlPattern, wrapper.getName());
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<String> getMappings() {

        Set<String> result = new HashSet<String>();
        String servletName = wrapper.getName();
        
        String[] urlPatterns = context.findServletMappings();
        for (String urlPattern : urlPatterns) {
            String name = context.findServletMapping(urlPattern);
            if (name.equals(servletName)) {
                result.add(urlPattern);
            }
        }
        return result;
    }

    @Override
    public String getRunAsRole() {
        return wrapper.getRunAs();
    }
    
}
