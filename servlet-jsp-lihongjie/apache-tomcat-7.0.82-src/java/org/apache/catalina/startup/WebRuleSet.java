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


package org.apache.catalina.startup;


import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextHandler;
import org.apache.catalina.deploy.ContextService;
import org.apache.catalina.deploy.ResourceBase;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.deploy.ServletDef;
import org.apache.catalina.deploy.WebXml;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.digester.CallMethodRule;
import org.apache.tomcat.util.digester.CallParamRule;
import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.digester.Rule;
import org.apache.tomcat.util.digester.RuleSetBase;
import org.apache.tomcat.util.digester.SetNextRule;
import org.apache.tomcat.util.res.StringManager;
import org.xml.sax.Attributes;


/**
 * <p><strong>RuleSet</strong> for processing the contents of a web application
 * deployment descriptor (<code>/WEB-INF/web.xml</code>) resource.</p>
 *
 * @author Craig R. McClanahan
 */
public class WebRuleSet extends RuleSetBase {

    /**
     * The string resources for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);

    // ----------------------------------------------------- Instance Variables


    /**
     * The matching pattern prefix to use for recognizing our elements.
     */
    protected String prefix = null;

    /**
     * The full pattern matching prefix, including the webapp or web-fragment
     * component, to use for matching elements
     */
    protected String fullPrefix = null;

    /**
     * Flag that indicates if this ruleset is for a web-fragment.xml file or for
     * a web.xml file.
     */
    protected boolean fragment = false;

    /**
     * The <code>SetSessionConfig</code> rule used to parse the web.xml
     */
    protected SetSessionConfig sessionConfig = new SetSessionConfig();
    
    
    /**
     * The <code>SetLoginConfig</code> rule used to parse the web.xml
     */
    protected SetLoginConfig loginConfig = new SetLoginConfig();

    
    /**
     * The <code>SetJspConfig</code> rule used to parse the web.xml
     */    
    protected SetJspConfig jspConfig = new SetJspConfig();


    /**
     * The <code>NameRule</code> rule used to parse the web.xml
     */
    protected NameRule name = new NameRule();


    /**
     * The <code>AbsoluteOrderingRule</code> rule used to parse the web.xml
     */
    protected AbsoluteOrderingRule absoluteOrdering;


    /**
     * The <code>RelativeOrderingRule</code> rule used to parse the web.xml
     */
    protected RelativeOrderingRule relativeOrdering;



    // ------------------------------------------------------------ Constructor


    /**
     * Construct an instance of this <code>RuleSet</code> with the default
     * matching pattern prefix and default fragment setting.
     */
    public WebRuleSet() {

        this("", false);

    }


    /**
     * Construct an instance of this <code>RuleSet</code> with the default
     * matching pattern prefix.
     */
    public WebRuleSet(boolean fragment) {

        this("", fragment);

    }


    /**
     * Construct an instance of this <code>RuleSet</code> with the specified
     * matching pattern prefix.
     *
     * @param prefix Prefix for matching pattern rules (including the
     *  trailing slash character)
     */
    public WebRuleSet(String prefix, boolean fragment) {

        super();
        this.namespaceURI = null;
        this.prefix = prefix;
        this.fragment = fragment;

        if(fragment) {
            fullPrefix = prefix + "web-fragment";
        } else {
            fullPrefix = prefix + "web-app";
        }

        absoluteOrdering = new AbsoluteOrderingRule(fragment);
        relativeOrdering = new RelativeOrderingRule(fragment);
    }

    // --------------------------------------------------------- Public Methods


    /**
     * <p>Add the set of Rule instances defined in this RuleSet to the
     * specified <code>Digester</code> instance, associating them with
     * our namespace URI (if any).  This method should only be called
     * by a Digester instance.</p>
     *
     * @param digester Digester instance to which the new Rule instances
     *  should be added.
     */
    @Override
    public void addRuleInstances(Digester digester) {
        digester.addRule(fullPrefix,
                         new SetPublicIdRule("setPublicId"));
        digester.addRule(fullPrefix,
                         new IgnoreAnnotationsRule());
        digester.addRule(fullPrefix,
                new VersionRule());

        // Required for both fragments and non-fragments
        digester.addRule(fullPrefix + "/absolute-ordering", absoluteOrdering);
        digester.addRule(fullPrefix + "/ordering", relativeOrdering);

        if (fragment) {
            // web-fragment.xml
            digester.addRule(fullPrefix + "/name", name);
            digester.addCallMethod(fullPrefix + "/ordering/after/name",
                                   "addAfterOrdering", 0);
            digester.addCallMethod(fullPrefix + "/ordering/after/others",
                                   "addAfterOrderingOthers");
            digester.addCallMethod(fullPrefix + "/ordering/before/name",
                                   "addBeforeOrdering", 0);
            digester.addCallMethod(fullPrefix + "/ordering/before/others",
                                   "addBeforeOrderingOthers");
        } else {
            // web.xml
            digester.addCallMethod(fullPrefix + "/absolute-ordering/name",
                                   "addAbsoluteOrdering", 0);
            digester.addCallMethod(fullPrefix + "/absolute-ordering/others",
                                   "addAbsoluteOrderingOthers");
        }

        digester.addCallMethod(fullPrefix + "/context-param",
                               "addContextParam", 2);
        digester.addCallParam(fullPrefix + "/context-param/param-name", 0);
        digester.addCallParam(fullPrefix + "/context-param/param-value", 1);

        digester.addCallMethod(fullPrefix + "/display-name",
                               "setDisplayName", 0);

        digester.addRule(fullPrefix + "/distributable",
                         new SetDistributableRule());

        configureNamingRules(digester);

        digester.addObjectCreate(fullPrefix + "/error-page",
                                 "org.apache.catalina.deploy.ErrorPage");
        digester.addSetNext(fullPrefix + "/error-page",
                            "addErrorPage",
                            "org.apache.catalina.deploy.ErrorPage");

        digester.addCallMethod(fullPrefix + "/error-page/error-code",
                               "setErrorCode", 0);
        digester.addCallMethod(fullPrefix + "/error-page/exception-type",
                               "setExceptionType", 0);
        digester.addCallMethod(fullPrefix + "/error-page/location",
                               "setLocation", 0);

        digester.addObjectCreate(fullPrefix + "/filter",
                                 "org.apache.catalina.deploy.FilterDef");
        digester.addSetNext(fullPrefix + "/filter",
                            "addFilter",
                            "org.apache.catalina.deploy.FilterDef");

        digester.addCallMethod(fullPrefix + "/filter/description",
                               "setDescription", 0);
        digester.addCallMethod(fullPrefix + "/filter/display-name",
                               "setDisplayName", 0);
        digester.addCallMethod(fullPrefix + "/filter/filter-class",
                               "setFilterClass", 0);
        digester.addCallMethod(fullPrefix + "/filter/filter-name",
                               "setFilterName", 0);
        digester.addCallMethod(fullPrefix + "/filter/icon/large-icon",
                               "setLargeIcon", 0);
        digester.addCallMethod(fullPrefix + "/filter/icon/small-icon",
                               "setSmallIcon", 0);
        digester.addCallMethod(fullPrefix + "/filter/async-supported",
                "setAsyncSupported", 0);

        digester.addCallMethod(fullPrefix + "/filter/init-param",
                               "addInitParameter", 2);
        digester.addCallParam(fullPrefix + "/filter/init-param/param-name",
                              0);
        digester.addCallParam(fullPrefix + "/filter/init-param/param-value",
                              1);

        digester.addObjectCreate(fullPrefix + "/filter-mapping",
                                 "org.apache.catalina.deploy.FilterMap");
        digester.addSetNext(fullPrefix + "/filter-mapping",
                                 "addFilterMapping",
                                 "org.apache.catalina.deploy.FilterMap");

        digester.addCallMethod(fullPrefix + "/filter-mapping/filter-name",
                               "setFilterName", 0);
        digester.addCallMethod(fullPrefix + "/filter-mapping/servlet-name",
                               "addServletName", 0);
        digester.addCallMethod(fullPrefix + "/filter-mapping/url-pattern",
                               "addURLPattern", 0);

        digester.addCallMethod(fullPrefix + "/filter-mapping/dispatcher",
                               "setDispatcher", 0);

         digester.addCallMethod(fullPrefix + "/listener/listener-class",
                                "addListener", 0);
         
        digester.addRule(fullPrefix + "/jsp-config",
                         jspConfig);

        digester.addObjectCreate(fullPrefix + "/jsp-config/jsp-property-group",
                                 "org.apache.catalina.deploy.JspPropertyGroup");
        digester.addSetNext(fullPrefix + "/jsp-config/jsp-property-group",
                            "addJspPropertyGroup",
                            "org.apache.catalina.deploy.JspPropertyGroup");
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/deferred-syntax-allowed-as-literal",
                               "setDeferredSyntax", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/el-ignored",
                               "setElIgnored", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/include-coda",
                               "addIncludeCoda", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/include-prelude",
                               "addIncludePrelude", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/is-xml",
                               "setIsXml", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/page-encoding",
                               "setPageEncoding", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/scripting-invalid",
                               "setScriptingInvalid", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/trim-directive-whitespaces",
                               "setTrimWhitespace", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/url-pattern",
                               "addUrlPattern", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/default-content-type",
                               "setDefaultContentType", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/buffer",
                               "setBuffer", 0);
        digester.addCallMethod(fullPrefix + "/jsp-config/jsp-property-group/error-on-undeclared-namespace",
                               "setErrorOnUndeclaredNamespace", 0);

        digester.addRule(fullPrefix + "/login-config",
                         loginConfig);

        digester.addObjectCreate(fullPrefix + "/login-config",
                                 "org.apache.catalina.deploy.LoginConfig");
        digester.addSetNext(fullPrefix + "/login-config",
                            "setLoginConfig",
                            "org.apache.catalina.deploy.LoginConfig");

        digester.addCallMethod(fullPrefix + "/login-config/auth-method",
                               "setAuthMethod", 0);
        digester.addCallMethod(fullPrefix + "/login-config/realm-name",
                               "setRealmName", 0);
        digester.addCallMethod(fullPrefix + "/login-config/form-login-config/form-error-page",
                               "setErrorPage", 0);
        digester.addCallMethod(fullPrefix + "/login-config/form-login-config/form-login-page",
                               "setLoginPage", 0);

        digester.addCallMethod(fullPrefix + "/mime-mapping",
                               "addMimeMapping", 2);
        digester.addCallParam(fullPrefix + "/mime-mapping/extension", 0);
        digester.addCallParam(fullPrefix + "/mime-mapping/mime-type", 1);


        digester.addObjectCreate(fullPrefix + "/security-constraint",
                                 "org.apache.catalina.deploy.SecurityConstraint");
        digester.addSetNext(fullPrefix + "/security-constraint",
                            "addSecurityConstraint",
                            "org.apache.catalina.deploy.SecurityConstraint");

        digester.addRule(fullPrefix + "/security-constraint/auth-constraint",
                         new SetAuthConstraintRule());
        digester.addCallMethod(fullPrefix + "/security-constraint/auth-constraint/role-name",
                               "addAuthRole", 0);
        digester.addCallMethod(fullPrefix + "/security-constraint/display-name",
                               "setDisplayName", 0);
        digester.addCallMethod(fullPrefix + "/security-constraint/user-data-constraint/transport-guarantee",
                               "setUserConstraint", 0);

        digester.addObjectCreate(fullPrefix + "/security-constraint/web-resource-collection",
                                 "org.apache.catalina.deploy.SecurityCollection");
        digester.addSetNext(fullPrefix + "/security-constraint/web-resource-collection",
                            "addCollection",
                            "org.apache.catalina.deploy.SecurityCollection");
        digester.addCallMethod(fullPrefix + "/security-constraint/web-resource-collection/http-method",
                               "addMethod", 0);
        digester.addCallMethod(fullPrefix + "/security-constraint/web-resource-collection/http-method-omission",
                               "addOmittedMethod", 0);
        digester.addCallMethod(fullPrefix + "/security-constraint/web-resource-collection/url-pattern",
                               "addPattern", 0);
        digester.addCallMethod(fullPrefix + "/security-constraint/web-resource-collection/web-resource-name",
                               "setName", 0);

        digester.addCallMethod(fullPrefix + "/security-role/role-name",
                               "addSecurityRole", 0);

        digester.addRule(fullPrefix + "/servlet",
                         new ServletDefCreateRule());
        digester.addSetNext(fullPrefix + "/servlet",
                            "addServlet",
                            "org.apache.catalina.deploy.ServletDef");

        digester.addCallMethod(fullPrefix + "/servlet/init-param",
                               "addInitParameter", 2);
        digester.addCallParam(fullPrefix + "/servlet/init-param/param-name",
                              0);
        digester.addCallParam(fullPrefix + "/servlet/init-param/param-value",
                              1);

        digester.addCallMethod(fullPrefix + "/servlet/jsp-file",
                               "setJspFile", 0);
        digester.addCallMethod(fullPrefix + "/servlet/load-on-startup",
                               "setLoadOnStartup", 0);
        digester.addCallMethod(fullPrefix + "/servlet/run-as/role-name",
                               "setRunAs", 0);

        digester.addObjectCreate(fullPrefix + "/servlet/security-role-ref",
                                 "org.apache.catalina.deploy.SecurityRoleRef");
        digester.addSetNext(fullPrefix + "/servlet/security-role-ref",
                            "addSecurityRoleRef",
                            "org.apache.catalina.deploy.SecurityRoleRef");
        digester.addCallMethod(fullPrefix + "/servlet/security-role-ref/role-link",
                               "setLink", 0);
        digester.addCallMethod(fullPrefix + "/servlet/security-role-ref/role-name",
                               "setName", 0);

        digester.addCallMethod(fullPrefix + "/servlet/servlet-class",
                              "setServletClass", 0);
        digester.addCallMethod(fullPrefix + "/servlet/servlet-name",
                              "setServletName", 0);
        
        digester.addObjectCreate(fullPrefix + "/servlet/multipart-config",
                                 "org.apache.catalina.deploy.MultipartDef");
        digester.addSetNext(fullPrefix + "/servlet/multipart-config",
                            "setMultipartDef",
                            "org.apache.catalina.deploy.MultipartDef");
        digester.addCallMethod(fullPrefix + "/servlet/multipart-config/location",
                               "setLocation", 0);
        digester.addCallMethod(fullPrefix + "/servlet/multipart-config/max-file-size",
                               "setMaxFileSize", 0);
        digester.addCallMethod(fullPrefix + "/servlet/multipart-config/max-request-size",
                               "setMaxRequestSize", 0);
        digester.addCallMethod(fullPrefix + "/servlet/multipart-config/file-size-threshold",
                               "setFileSizeThreshold", 0);

        digester.addCallMethod(fullPrefix + "/servlet/async-supported",
                               "setAsyncSupported", 0);
        digester.addCallMethod(fullPrefix + "/servlet/enabled",
                               "setEnabled", 0);

        
        digester.addRule(fullPrefix + "/servlet-mapping",
                               new CallMethodMultiRule("addServletMapping", 2, 0));
        digester.addCallParam(fullPrefix + "/servlet-mapping/servlet-name", 1);
        digester.addRule(fullPrefix + "/servlet-mapping/url-pattern", new CallParamMultiRule(0));

        digester.addRule(fullPrefix + "/session-config", sessionConfig);
        digester.addObjectCreate(fullPrefix + "/session-config",
                                 "org.apache.catalina.deploy.SessionConfig");
        digester.addSetNext(fullPrefix + "/session-config", "setSessionConfig",
                            "org.apache.catalina.deploy.SessionConfig");
        digester.addCallMethod(fullPrefix + "/session-config/session-timeout",
                               "setSessionTimeout", 0);
        digester.addCallMethod(fullPrefix + "/session-config/cookie-config/name",
                               "setCookieName", 0);
        digester.addCallMethod(fullPrefix + "/session-config/cookie-config/domain",
                               "setCookieDomain", 0);
        digester.addCallMethod(fullPrefix + "/session-config/cookie-config/path",
                               "setCookiePath", 0);
        digester.addCallMethod(fullPrefix + "/session-config/cookie-config/comment",
                               "setCookieComment", 0);
        digester.addCallMethod(fullPrefix + "/session-config/cookie-config/http-only",
                               "setCookieHttpOnly", 0);
        digester.addCallMethod(fullPrefix + "/session-config/cookie-config/secure",
                               "setCookieSecure", 0);
        digester.addCallMethod(fullPrefix + "/session-config/cookie-config/max-age",
                               "setCookieMaxAge", 0);
        digester.addCallMethod(fullPrefix + "/session-config/tracking-mode",
                               "addSessionTrackingMode", 0);

        // Taglibs pre Servlet 2.4
        digester.addRule(fullPrefix + "/taglib", new TaglibLocationRule(false));
        digester.addCallMethod(fullPrefix + "/taglib",
                               "addTaglib", 2);
        digester.addCallParam(fullPrefix + "/taglib/taglib-location", 1);
        digester.addCallParam(fullPrefix + "/taglib/taglib-uri", 0);

        // Taglibs Servlet 2.4 onwards
        digester.addRule(fullPrefix + "/jsp-config/taglib", new TaglibLocationRule(true));
        digester.addCallMethod(fullPrefix + "/jsp-config/taglib",
                "addTaglib", 2);
        digester.addCallParam(fullPrefix + "/jsp-config/taglib/taglib-location", 1);
        digester.addCallParam(fullPrefix + "/jsp-config/taglib/taglib-uri", 0);

        digester.addCallMethod(fullPrefix + "/welcome-file-list/welcome-file",
                               "addWelcomeFile", 0);

        digester.addCallMethod(fullPrefix + "/locale-encoding-mapping-list/locale-encoding-mapping",
                              "addLocaleEncodingMapping", 2);
        digester.addCallParam(fullPrefix + "/locale-encoding-mapping-list/locale-encoding-mapping/locale", 0);
        digester.addCallParam(fullPrefix + "/locale-encoding-mapping-list/locale-encoding-mapping/encoding", 1);

        digester.addRule(fullPrefix + "/post-construct",
                new LifecycleCallbackRule("addPostConstructMethods", 2, true));
        digester.addCallParam(fullPrefix + "/post-construct/lifecycle-callback-class", 0);
        digester.addCallParam(fullPrefix + "/post-construct/lifecycle-callback-method", 1);

        digester.addRule(fullPrefix + "/pre-destroy",
                new LifecycleCallbackRule("addPreDestroyMethods", 2, false));
        digester.addCallParam(fullPrefix + "/pre-destroy/lifecycle-callback-class", 0);
        digester.addCallParam(fullPrefix + "/pre-destroy/lifecycle-callback-method", 1);
    }

    protected void configureNamingRules(Digester digester) {
        //ejb-local-ref
        digester.addObjectCreate(fullPrefix + "/ejb-local-ref",
                                 "org.apache.catalina.deploy.ContextLocalEjb");
        digester.addSetNext(fullPrefix + "/ejb-local-ref",
                            "addEjbLocalRef",
                            "org.apache.catalina.deploy.ContextLocalEjb");
        digester.addCallMethod(fullPrefix + "/ejb-local-ref/description",
                               "setDescription", 0);
        digester.addCallMethod(fullPrefix + "/ejb-local-ref/ejb-link",
                               "setLink", 0);
        digester.addCallMethod(fullPrefix + "/ejb-local-ref/ejb-ref-name",
                               "setName", 0);
        digester.addCallMethod(fullPrefix + "/ejb-local-ref/ejb-ref-type",
                               "setType", 0);
        digester.addCallMethod(fullPrefix + "/ejb-local-ref/local",
                               "setLocal", 0);
        digester.addCallMethod(fullPrefix + "/ejb-local-ref/local-home",
                               "setHome", 0);
        digester.addRule(fullPrefix + "/ejb-local-ref/mapped-name",
                         new MappedNameRule());
        configureInjectionRules(digester, "web-app/ejb-local-ref/");

        //ejb-ref
        digester.addObjectCreate(fullPrefix + "/ejb-ref",
                                 "org.apache.catalina.deploy.ContextEjb");
        digester.addSetNext(fullPrefix + "/ejb-ref", 
                            "addEjbRef",
                            "org.apache.catalina.deploy.ContextEjb");
        digester.addCallMethod(fullPrefix + "/ejb-ref/description",
                               "setDescription", 0);
        digester.addCallMethod(fullPrefix + "/ejb-ref/ejb-link",
                               "setLink", 0);
        digester.addCallMethod(fullPrefix + "/ejb-ref/ejb-ref-name",
                               "setName", 0);
        digester.addCallMethod(fullPrefix + "/ejb-ref/ejb-ref-type",
                               "setType", 0);
        digester.addCallMethod(fullPrefix + "/ejb-ref/home",
                               "setHome", 0);
        digester.addCallMethod(fullPrefix + "/ejb-ref/remote",
                               "setRemote", 0);
        digester.addRule(fullPrefix + "/ejb-ref/mapped-name",
                         new MappedNameRule());
        configureInjectionRules(digester, "web-app/ejb-ref/");

        //env-entry
        digester.addObjectCreate(fullPrefix + "/env-entry",
                                 "org.apache.catalina.deploy.ContextEnvironment");
        digester.addSetNext(fullPrefix + "/env-entry",
                            "addEnvEntry",
                            "org.apache.catalina.deploy.ContextEnvironment");
        digester.addRule(fullPrefix + "/env-entry", new SetOverrideRule());
        digester.addCallMethod(fullPrefix + "/env-entry/description",
                               "setDescription", 0);
        digester.addCallMethod(fullPrefix + "/env-entry/env-entry-name",
                               "setName", 0);
        digester.addCallMethod(fullPrefix + "/env-entry/env-entry-type",
                               "setType", 0);
        digester.addCallMethod(fullPrefix + "/env-entry/env-entry-value",
                               "setValue", 0);
        digester.addRule(fullPrefix + "/env-entry/mapped-name",
                         new MappedNameRule());
        configureInjectionRules(digester, "web-app/env-entry/");

        //resource-env-ref
        digester.addObjectCreate(fullPrefix + "/resource-env-ref",
            "org.apache.catalina.deploy.ContextResourceEnvRef");
        digester.addSetNext(fullPrefix + "/resource-env-ref",
                            "addResourceEnvRef",
                            "org.apache.catalina.deploy.ContextResourceEnvRef");
        digester.addCallMethod(fullPrefix + "/resource-env-ref/resource-env-ref-name",
                "setName", 0);
        digester.addCallMethod(fullPrefix + "/resource-env-ref/resource-env-ref-type",
                "setType", 0);
        digester.addRule(fullPrefix + "/resource-env-ref/mapped-name",
                         new MappedNameRule());
        configureInjectionRules(digester, "web-app/resource-env-ref/");

        //message-destination
        digester.addObjectCreate(fullPrefix + "/message-destination",
                                 "org.apache.catalina.deploy.MessageDestination");
        digester.addSetNext(fullPrefix + "/message-destination",
                            "addMessageDestination",
                            "org.apache.catalina.deploy.MessageDestination");
        digester.addCallMethod(fullPrefix + "/message-destination/description",
                               "setDescription", 0);
        digester.addCallMethod(fullPrefix + "/message-destination/display-name",
                               "setDisplayName", 0);
        digester.addCallMethod(fullPrefix + "/message-destination/icon/large-icon",
                               "setLargeIcon", 0);
        digester.addCallMethod(fullPrefix + "/message-destination/icon/small-icon",
                               "setSmallIcon", 0);
        digester.addCallMethod(fullPrefix + "/message-destination/message-destination-name",
                               "setName", 0);
        digester.addRule(fullPrefix + "/message-destination/mapped-name",
                         new MappedNameRule());

        //message-destination-ref
        digester.addObjectCreate(fullPrefix + "/message-destination-ref",
                                 "org.apache.catalina.deploy.MessageDestinationRef");
        digester.addSetNext(fullPrefix + "/message-destination-ref",
                            "addMessageDestinationRef",
                            "org.apache.catalina.deploy.MessageDestinationRef");
        digester.addCallMethod(fullPrefix + "/message-destination-ref/description",
                               "setDescription", 0);
        digester.addCallMethod(fullPrefix + "/message-destination-ref/message-destination-link",
                               "setLink", 0);
        digester.addCallMethod(fullPrefix + "/message-destination-ref/message-destination-ref-name",
                               "setName", 0);
        digester.addCallMethod(fullPrefix + "/message-destination-ref/message-destination-type",
                               "setType", 0);
        digester.addCallMethod(fullPrefix + "/message-destination-ref/message-destination-usage",
                               "setUsage", 0);
        digester.addRule(fullPrefix + "/message-destination-ref/mapped-name",
                         new MappedNameRule());
        configureInjectionRules(digester, "web-app/message-destination-ref/");

        //resource-ref
        digester.addObjectCreate(fullPrefix + "/resource-ref",
                                 "org.apache.catalina.deploy.ContextResource");
        digester.addSetNext(fullPrefix + "/resource-ref",
                            "addResourceRef",
                            "org.apache.catalina.deploy.ContextResource");
        digester.addCallMethod(fullPrefix + "/resource-ref/description",
                               "setDescription", 0);
        digester.addCallMethod(fullPrefix + "/resource-ref/res-auth",
                               "setAuth", 0);
        digester.addCallMethod(fullPrefix + "/resource-ref/res-ref-name",
                               "setName", 0);
        digester.addCallMethod(fullPrefix + "/resource-ref/res-sharing-scope",
                               "setScope", 0);
        digester.addCallMethod(fullPrefix + "/resource-ref/res-type",
                               "setType", 0);
        digester.addRule(fullPrefix + "/resource-ref/mapped-name",
                         new MappedNameRule());
        configureInjectionRules(digester, "web-app/resource-ref/");

        //service-ref
        digester.addObjectCreate(fullPrefix + "/service-ref",
                                 "org.apache.catalina.deploy.ContextService");
        digester.addSetNext(fullPrefix + "/service-ref",
                            "addServiceRef",
                            "org.apache.catalina.deploy.ContextService");
        digester.addCallMethod(fullPrefix + "/service-ref/description",
                               "setDescription", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/display-name",
                               "setDisplayname", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/icon/large-icon",
                               "setLargeIcon", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/icon/small-icon",
                               "setSmallIcon", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/service-ref-name",
                               "setName", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/service-interface",
                               "setInterface", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/service-ref-type",
                               "setType", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/wsdl-file",
                               "setWsdlfile", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/jaxrpc-mapping-file",
                               "setJaxrpcmappingfile", 0);
        digester.addRule(fullPrefix + "/service-ref/service-qname", new ServiceQnameRule());

        digester.addRule(fullPrefix + "/service-ref/port-component-ref",
                               new CallMethodMultiRule("addPortcomponent", 2, 1));
        digester.addCallParam(fullPrefix + "/service-ref/port-component-ref/service-endpoint-interface", 0);
        digester.addRule(fullPrefix + "/service-ref/port-component-ref/port-component-link", new CallParamMultiRule(1));

        digester.addObjectCreate(fullPrefix + "/service-ref/handler",
                                 "org.apache.catalina.deploy.ContextHandler");
        digester.addRule(fullPrefix + "/service-ref/handler",
                         new SetNextRule("addHandler",
                         "org.apache.catalina.deploy.ContextHandler"));

        digester.addCallMethod(fullPrefix + "/service-ref/handler/handler-name",
                               "setName", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/handler/handler-class",
                               "setHandlerclass", 0);

        digester.addCallMethod(fullPrefix + "/service-ref/handler/init-param",
                               "setProperty", 2);
        digester.addCallParam(fullPrefix + "/service-ref/handler/init-param/param-name",
                              0);
        digester.addCallParam(fullPrefix + "/service-ref/handler/init-param/param-value",
                              1);

        digester.addRule(fullPrefix + "/service-ref/handler/soap-header", new SoapHeaderRule());

        digester.addCallMethod(fullPrefix + "/service-ref/handler/soap-role",
                               "addSoapRole", 0);
        digester.addCallMethod(fullPrefix + "/service-ref/handler/port-name",
                               "addPortName", 0);
        digester.addRule(fullPrefix + "/service-ref/mapped-name",
                         new MappedNameRule());
        configureInjectionRules(digester, "web-app/service-ref/");
    }

    protected void configureInjectionRules(Digester digester, String base) {

        digester.addCallMethod(prefix + base + "injection-target", "addInjectionTarget", 2);
        digester.addCallParam(prefix + base + "injection-target/injection-target-class", 0);
        digester.addCallParam(prefix + base + "injection-target/injection-target-name", 1);

    }


    /**
     * Reset counter used for validating the web.xml file.
     */
    public void recycle(){
        jspConfig.isJspConfigSet = false;
        sessionConfig.isSessionConfigSet = false;
        loginConfig.isLoginConfigSet = false;
        name.isNameSet = false;
        absoluteOrdering.isAbsoluteOrderingSet = false;
        relativeOrdering.isRelativeOrderingSet = false;
    }
}


// ----------------------------------------------------------- Private Classes


/**
 * Rule to check that the <code>login-config</code> is occurring 
 * only 1 time within the web.xml
 */
final class SetLoginConfig extends Rule {
    boolean isLoginConfigSet = false;
    public SetLoginConfig() {
        // NO-OP
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        if (isLoginConfigSet){
            throw new IllegalArgumentException(
            "<login-config> element is limited to 1 occurrence");
        }
        isLoginConfigSet = true;
    }

}


/**
 * Rule to check that the <code>jsp-config</code> is occurring 
 * only 1 time within the web.xml
 */
final class SetJspConfig extends Rule {
    boolean isJspConfigSet = false;
    public SetJspConfig() {
        // NO-OP
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        if (isJspConfigSet){
            throw new IllegalArgumentException(
            "<jsp-config> element is limited to 1 occurrence");
        }
        isJspConfigSet = true;
    }

}


/**
 * Rule to check that the <code>session-config</code> is occurring 
 * only 1 time within the web.xml
 */
final class SetSessionConfig extends Rule {
    boolean isSessionConfigSet = false;
    public SetSessionConfig() {
        // NO-OP
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        if (isSessionConfigSet){
            throw new IllegalArgumentException(
            "<session-config> element is limited to 1 occurrence");
        }
        isSessionConfigSet = true;
    }

}

/**
 * A Rule that calls the <code>setAuthConstraint(true)</code> method of
 * the top item on the stack, which must be of type
 * <code>org.apache.catalina.deploy.SecurityConstraint</code>.
 */

final class SetAuthConstraintRule extends Rule {

    public SetAuthConstraintRule() {
        // NO-OP
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        SecurityConstraint securityConstraint =
            (SecurityConstraint) digester.peek();
        securityConstraint.setAuthConstraint(true);
        if (digester.getLogger().isDebugEnabled()) {
            digester.getLogger()
               .debug("Calling SecurityConstraint.setAuthConstraint(true)");
        }
    }

}


/**
 * Class that calls <code>setDistributable(true)</code> for the top object
 * on the stack, which must be a <code>org.apache.catalina.Context</code>.
 */

final class SetDistributableRule extends Rule {

    public SetDistributableRule() {
        // NO-OP
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        WebXml webXml = (WebXml) digester.peek();
        webXml.setDistributable(true);
        if (digester.getLogger().isDebugEnabled()) {
            digester.getLogger().debug
               (webXml.getClass().getName() + ".setDistributable(true)");
        }
    }

}


/**
 * Class that calls a property setter for the top object on the stack,
 * passing the public ID of the entity we are currently processing.
 */

final class SetPublicIdRule extends Rule {

    public SetPublicIdRule(String method) {
        this.method = method;
    }

    private String method = null;

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {

        Object top = digester.peek();
        Class<?> paramClasses[] = new Class[1];
        paramClasses[0] = "String".getClass();
        String paramValues[] = new String[1];
        paramValues[0] = digester.getPublicId();

        Method m = null;
        try {
            m = top.getClass().getMethod(method, paramClasses);
        } catch (NoSuchMethodException e) {
            digester.getLogger().error("Can't find method " + method + " in "
                                       + top + " CLASS " + top.getClass());
            return;
        }

        m.invoke(top, (Object [])paramValues);
        if (digester.getLogger().isDebugEnabled())
            digester.getLogger().debug("" + top.getClass().getName() + "." 
                                       + method + "(" + paramValues[0] + ")");

    }

}


/**
 * A Rule that calls the factory method on the specified Context to
 * create the object that is to be added to the stack.
 */

final class ServletDefCreateRule extends Rule {

    public ServletDefCreateRule() {
        // NO-OP
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        ServletDef servletDef = new ServletDef();
        digester.push(servletDef);
        if (digester.getLogger().isDebugEnabled())
            digester.getLogger().debug("new " + servletDef.getClass().getName());
    }

    @Override
    public void end(String namespace, String name)
        throws Exception {
        ServletDef servletDef = (ServletDef) digester.pop();
        if (digester.getLogger().isDebugEnabled())
            digester.getLogger().debug("pop " + servletDef.getClass().getName());
    }

}


/**
 * A Rule that can be used to call multiple times a method as many times as needed
 * (used for addServletMapping).
 */
final class CallParamMultiRule extends CallParamRule {

    public CallParamMultiRule(int paramIndex) {
        super(paramIndex);
    }

    @Override
    public void end(String namespace, String name) {
        if (bodyTextStack != null && !bodyTextStack.empty()) {
            // what we do now is push one parameter onto the top set of parameters
            Object parameters[] = (Object[]) digester.peekParams();
            @SuppressWarnings("unchecked")
            ArrayList<String> params = (ArrayList<String>) parameters[paramIndex];
            if (params == null) {
                params = new ArrayList<String>();
                parameters[paramIndex] = params;
            }
            params.add(bodyTextStack.pop());
        }
    }

}


/**
 * A Rule that can be used to call multiple times a method as many times as needed
 * (used for addServletMapping).
 */
final class CallMethodMultiRule extends CallMethodRule {

    int multiParamIndex = 0;
    
    public CallMethodMultiRule(String methodName, int paramCount, int multiParamIndex) {
        super(methodName, paramCount);
        this.multiParamIndex = multiParamIndex;
    }

    /**
     * Process the end of this element.
     * 
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     */
    @Override
    public void end(String namespace, String name) throws Exception {

        // Retrieve or construct the parameter values array
        Object parameters[] = null;
        if (paramCount > 0) {
            parameters = (Object[]) digester.popParams();
        } else {
            parameters = new Object[0];
            super.end(namespace, name);
        }
        
        ArrayList<?> multiParams = (ArrayList<?>) parameters[multiParamIndex];
        
        // Construct the parameter values array we will need
        // We only do the conversion if the param value is a String and
        // the specified paramType is not String. 
        Object paramValues[] = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            if (i != multiParamIndex) {
                // convert nulls and convert stringy parameters 
                // for non-stringy param types
                if(parameters[i] == null || (parameters[i] instanceof String 
                        && !String.class.isAssignableFrom(paramTypes[i]))) {
                    paramValues[i] =
                        IntrospectionUtils.convert((String) parameters[i], paramTypes[i]);
                } else {
                    paramValues[i] = parameters[i];
                }
            }
        }

        // Determine the target object for the method call
        Object target;
        if (targetOffset >= 0) {
            target = digester.peek(targetOffset);
        } else {
            target = digester.peek(digester.getCount() + targetOffset);
        }

        if (target == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[CallMethodRule]{");
            sb.append("");
            sb.append("} Call target is null (");
            sb.append("targetOffset=");
            sb.append(targetOffset);
            sb.append(",stackdepth=");
            sb.append(digester.getCount());
            sb.append(")");
            throw new org.xml.sax.SAXException(sb.toString());
        }
        
        if (multiParams == null) {
            paramValues[multiParamIndex] = null;
            IntrospectionUtils.callMethodN(target, methodName, paramValues,
                    paramTypes);   
            return;
        }
        
        for (int j = 0; j < multiParams.size(); j++) {
            Object param = multiParams.get(j);
            if(param == null || (param instanceof String 
                    && !String.class.isAssignableFrom(paramTypes[multiParamIndex]))) {
                paramValues[multiParamIndex] =
                    IntrospectionUtils.convert((String) param, paramTypes[multiParamIndex]);
            } else {
                paramValues[multiParamIndex] = param;
            }
            IntrospectionUtils.callMethodN(target, methodName, paramValues,
                    paramTypes);   
        }
        
    }

}



/**
 * A Rule that check if the annotations have to be loaded.
 * 
 */

final class IgnoreAnnotationsRule extends Rule {

    public IgnoreAnnotationsRule() {
        // NO-OP
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        WebXml webxml = (WebXml) digester.peek(digester.getCount() - 1);
        String value = attributes.getValue("metadata-complete");
        if ("true".equals(value)) {
            webxml.setMetadataComplete(true);
        } else if ("false".equals(value)) {
            webxml.setMetadataComplete(false);
        }
        if (digester.getLogger().isDebugEnabled()) {
            digester.getLogger().debug
                (webxml.getClass().getName() + ".setMetadataComplete( " +
                        webxml.isMetadataComplete() + ")");
        }
    }

}

/**
 * A Rule that records the spec version of the web.xml being parsed
 * 
 */

final class VersionRule extends Rule {

    public VersionRule() {
        // NO-OP
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        WebXml webxml = (WebXml) digester.peek(digester.getCount() - 1);
        webxml.setVersion(attributes.getValue("version"));
        
        if (digester.getLogger().isDebugEnabled()) {
            digester.getLogger().debug
                (webxml.getClass().getName() + ".setVersion( " +
                        webxml.getVersion() + ")");
        }
    }

}


/**
 * A rule that ensures only a single name element is present.
 */
final class NameRule extends Rule {
    
    boolean isNameSet = false;

    public NameRule() {
        // NO-OP
    }
    
    @Override
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        if (isNameSet){
            throw new IllegalArgumentException(WebRuleSet.sm.getString(
                    "webRuleSet.nameCount"));
        }
        isNameSet = true;
    }

    @Override
    public void body(String namespace, String name, String text)
            throws Exception {
        super.body(namespace, name, text);
        ((WebXml) digester.peek()).setName(text);
    } 
}


/**
 * A rule that logs a warning if absolute ordering is configured for a fragment
 * and fails if multiple absolute orders are configured.
 */
final class AbsoluteOrderingRule extends Rule {

    boolean isAbsoluteOrderingSet = false;
    private final boolean fragment;

    public AbsoluteOrderingRule(boolean fragment) {
        this.fragment = fragment;
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
            throws Exception {
        if (fragment) {
            digester.getLogger().warn(
                    WebRuleSet.sm.getString("webRuleSet.absoluteOrdering"));
        }
        if (isAbsoluteOrderingSet) {
            throw new IllegalArgumentException(WebRuleSet.sm.getString(
                    "webRuleSet.absoluteOrderingCount"));
        } else {
            isAbsoluteOrderingSet = true;
            WebXml webXml = (WebXml) digester.peek();
            webXml.createAbsoluteOrdering();
            if (digester.getLogger().isDebugEnabled()) {
                digester.getLogger().debug(
                        webXml.getClass().getName() + ".setAbsoluteOrdering()");
            }
        }
    }
}

/**
 * A rule that logs a warning if relative ordering is configured.
 */
final class RelativeOrderingRule extends Rule {
    
    boolean isRelativeOrderingSet = false;
    private final boolean fragment;

    public RelativeOrderingRule(boolean fragment) {
        this.fragment = fragment;
    }
    
    @Override
    public void begin(String namespace, String name, Attributes attributes)
            throws Exception {
        if (!fragment) {
            digester.getLogger().warn(
                    WebRuleSet.sm.getString("webRuleSet.relativeOrdering"));
        }
        if (isRelativeOrderingSet) {
            throw new IllegalArgumentException(WebRuleSet.sm.getString(
                    "webRuleSet.relativeOrderingCount"));
        } else {
            isRelativeOrderingSet = true;
        }
    } 
}

/**
 * A Rule that sets soap headers on the ContextHandler.
 * 
 */
final class SoapHeaderRule extends Rule {

    public SoapHeaderRule() {
        // NO-OP
    }

    /**
     * Process the body text of this element.
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param text The body text of this element
     */
    @Override
    public void body(String namespace, String name, String text)
            throws Exception {
        String namespaceuri = null;
        String localpart = text;
        int colon = text.indexOf(':');
        if (colon >= 0) {
            String prefix = text.substring(0,colon);
            namespaceuri = digester.findNamespaceURI(prefix);
            localpart = text.substring(colon+1);
        }
        ContextHandler contextHandler = (ContextHandler)digester.peek();
        contextHandler.addSoapHeaders(localpart,namespaceuri);
    }
}

/**
 * A Rule that sets service qname on the ContextService.
 * 
 */
final class ServiceQnameRule extends Rule {

    public ServiceQnameRule() {
        // NO-OP
    }

    /**
     * Process the body text of this element.
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param text The body text of this element
     */
    @Override
    public void body(String namespace, String name, String text)
            throws Exception {
        String namespaceuri = null;
        String localpart = text;
        int colon = text.indexOf(':');
        if (colon >= 0) {
            String prefix = text.substring(0,colon);
            namespaceuri = digester.findNamespaceURI(prefix);
            localpart = text.substring(colon+1);
        }
        ContextService contextService = (ContextService)digester.peek();
        contextService.setServiceqnameLocalpart(localpart);
        contextService.setServiceqnameNamespaceURI(namespaceuri);
    }
    
}

/**
 * A rule that checks if the taglib element is in the right place. 
 */
final class TaglibLocationRule extends Rule {

    final boolean isServlet24OrLater;
    
    public TaglibLocationRule(boolean isServlet24OrLater) {
        this.isServlet24OrLater = isServlet24OrLater;
    }
    
    @Override
    public void begin(String namespace, String name, Attributes attributes)
            throws Exception {
        WebXml webXml = (WebXml) digester.peek(digester.getCount() - 1);
        // If we have a public ID, this is not a 2.4 or later webapp
        boolean havePublicId = (webXml.getPublicId() != null);
        // havePublicId and isServlet24OrLater should be mutually exclusive
        if (havePublicId == isServlet24OrLater) {
            throw new IllegalArgumentException(
                    "taglib definition not consistent with specification version");
        }
    }
}

/**
 * A Rule that sets mapped name on the ResourceBase.
 */
final class MappedNameRule extends Rule {

    public MappedNameRule() {
        // NO-OP
    }

    /**
     * Process the body text of this element.
     *
     * @param namespace the namespace URI of the matching element, or an
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just
     *   the element name otherwise
     * @param text The body text of this element
     */
    @Override
    public void body(String namespace, String name, String text)
            throws Exception {
        ResourceBase resourceBase = (ResourceBase) digester.peek();
        resourceBase.setProperty("mappedName", text.trim());
    }
}

/**
 * A rule that fails if more than one post construct or pre destroy methods
 * are configured per class.
 */
final class LifecycleCallbackRule extends CallMethodRule {

    private final boolean postConstruct;

    public LifecycleCallbackRule(String methodName, int paramCount,
            boolean postConstruct) {
        super(methodName, paramCount);
        this.postConstruct = postConstruct;
    }

    @Override
    public void end(String namespace, String name) throws Exception {
        Object[] params = (Object[]) digester.peekParams();
        if (params != null && params.length == 2) {
            WebXml webXml = (WebXml) digester.peek();
            if (postConstruct) {
                if (webXml.getPostConstructMethods().containsKey(params[0])) {
                    throw new IllegalArgumentException(WebRuleSet.sm.getString(
                            "webRuleSet.postconstruct.duplicate", params[0]));
                }
            } else {
                if (webXml.getPreDestroyMethods().containsKey(params[0])) {
                    throw new IllegalArgumentException(WebRuleSet.sm.getString(
                            "webRuleSet.predestroy.duplicate", params[0]));
                }
            }
        }
        super.end(namespace, name);
    }
}

final class SetOverrideRule extends Rule {

    public SetOverrideRule() {
        // no-op
    }

    @Override
    public void begin(String namespace, String name, Attributes attributes)
            throws Exception {
        ContextEnvironment envEntry = (ContextEnvironment) digester.peek();
        envEntry.setOverride(false);
        if (digester.getLogger().isDebugEnabled()) {
            digester.getLogger().debug(
                    envEntry.getClass().getName() + ".setOverride(false)");
        }
    }
}
