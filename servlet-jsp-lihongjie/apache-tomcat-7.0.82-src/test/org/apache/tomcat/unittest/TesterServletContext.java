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
package org.apache.tomcat.unittest;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.apache.catalina.core.ApplicationFilterRegistration;
import org.apache.catalina.deploy.FilterDef;

public class TesterServletContext implements ServletContext {

    /**
     * {@inheritDoc}
     * <p>
     * This test implementation is hard coded to return an empty String.
     */
    @Override
    public String getContextPath() {
        return "";
    }

    @Override
    public ServletContext getContext(String uripath) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public int getMajorVersion() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public int getMinorVersion() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getMimeType(String file) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Set<String> getResourcePaths(String path) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String name) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Servlet getServlet(String name) throws ServletException {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Enumeration<Servlet> getServlets() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Enumeration<String> getServletNames() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public void log(String msg) {
        // NOOP
    }

    @Override
    public void log(Exception exception, String msg) {
        // NOOP
    }

    @Override
    public void log(String message, Throwable throwable) {
        // NOOP
    }

    @Override
    public String getRealPath(String path) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getServerInfo() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getInitParameter(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Object getAttribute(String name) {
        // Used by websockets
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void setAttribute(String name, Object object) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void removeAttribute(String name) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getServletContextName() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public int getEffectiveMajorVersion() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public int getEffectiveMinorVersion() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean setInitParameter(String name, String value) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Dynamic addServlet(String servletName, String className) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Dynamic addServlet(String servletName, Servlet servlet) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Dynamic addServlet(String servletName,
            Class<? extends Servlet> servletClass) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> c)
            throws ServletException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public javax.servlet.FilterRegistration.Dynamic addFilter(
            String filterName, String className) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public javax.servlet.FilterRegistration.Dynamic addFilter(
            String filterName, Filter filter) {
        return new ApplicationFilterRegistration(
                new FilterDef(), new TesterContext());
    }

    @Override
    public javax.servlet.FilterRegistration.Dynamic addFilter(
            String filterName, Class<? extends Filter> filterClass) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> c)
            throws ServletException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        throw new RuntimeException("Not implemented");
    }

    private SessionCookieConfig sessionCookieConfig = new TesterSessionCookieConfig();
    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return sessionCookieConfig;
    }

    private final Set<SessionTrackingMode> sessionTrackingModes =
            new HashSet<SessionTrackingMode>();
    @Override
    public void setSessionTrackingModes(
            Set<SessionTrackingMode> sessionTrackingModes) {
        this.sessionTrackingModes.clear();
        this.sessionTrackingModes.addAll(sessionTrackingModes);
    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return sessionTrackingModes;
    }

    @Override
    public void addListener(String className) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <T extends EventListener> void addListener(T t) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <T extends EventListener> T createListener(Class<T> c)
            throws ServletException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public ClassLoader getClassLoader() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void declareRoles(String... roleNames) {
        throw new RuntimeException("Not implemented");
    }
}
