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
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.util.ParameterMap;
import org.apache.tomcat.util.res.StringManager;

public class ApplicationFilterRegistration
        implements FilterRegistration.Dynamic {

    /**
     * The string manager for this package.
     */
    private static final StringManager sm =
      StringManager.getManager(Constants.Package);
    
    private FilterDef filterDef;
    private Context context;
    
    public ApplicationFilterRegistration(FilterDef filterDef,
            Context context) {
        this.filterDef = filterDef;
        this.context = context;
        
    }

    @Override
    public void addMappingForServletNames(
            EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
            String... servletNames) {

        FilterMap filterMap = new FilterMap();
        
        filterMap.setFilterName(filterDef.getFilterName());
        
        if (dispatcherTypes != null) {
            for (DispatcherType dispatcherType : dispatcherTypes) {
                filterMap.setDispatcher(dispatcherType.name());
            }
        }
        
        if (servletNames != null) {
            for (String servletName : servletNames) {
                filterMap.addServletName(servletName);
            }
        
            if (isMatchAfter) {
                context.addFilterMap(filterMap);
            } else {
                context.addFilterMapBefore(filterMap);
            }
        }
        // else error?
    }

    @Override
    public void addMappingForUrlPatterns(
            EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
            String... urlPatterns) {

        FilterMap filterMap = new FilterMap();

        filterMap.setFilterName(filterDef.getFilterName());

        if (dispatcherTypes != null) {
            for (DispatcherType dispatcherType : dispatcherTypes) {
                filterMap.setDispatcher(dispatcherType.name());
            }
        }
        
        if (urlPatterns != null) {
            for (String urlPattern : urlPatterns) {
                filterMap.addURLPattern(urlPattern);
            }
        
            if (isMatchAfter) {
                context.addFilterMap(filterMap);
            } else {
                context.addFilterMapBefore(filterMap);
            }
        }
        // else error?
        
    }

    @Override
    public Collection<String> getServletNameMappings() {
        Collection<String> result = new HashSet<String>();
        
        FilterMap[] filterMaps = context.findFilterMaps();
        
        for (FilterMap filterMap : filterMaps) {
            if (filterMap.getFilterName().equals(filterDef.getFilterName())) {
                for (String servletName : filterMap.getServletNames()) {
                    result.add(servletName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> getUrlPatternMappings() {
        Collection<String> result = new HashSet<String>();
        
        FilterMap[] filterMaps = context.findFilterMaps();
        
        for (FilterMap filterMap : filterMaps) {
            if (filterMap.getFilterName().equals(filterDef.getFilterName())) {
                for (String urlPattern : filterMap.getURLPatterns()) {
                    result.add(urlPattern);
                }
            }
        }
        return result;
    }

    @Override
    public String getClassName() {
        return filterDef.getFilterClass();
   }

    @Override
    public String getInitParameter(String name) {
        return filterDef.getParameterMap().get(name);
    }

    @Override
    public Map<String, String> getInitParameters() {
        ParameterMap<String,String> result = new ParameterMap<String,String>();
        result.putAll(filterDef.getParameterMap());
        result.setLocked(true);
        return result;
    }

    @Override
    public String getName() {
        return filterDef.getFilterName();
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
        
        filterDef.addInitParameter(name, value);

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
        for (Map.Entry<String, String> entry : initParameters.entrySet()) {
            setInitParameter(entry.getKey(), entry.getValue());
        }
        
        return conflicts;
    }

    @Override
    public void setAsyncSupported(boolean asyncSupported) {
        filterDef.setAsyncSupported(Boolean.valueOf(asyncSupported).toString());
    }
    
}
