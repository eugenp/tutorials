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


/**
 * String constants for the startup package.
 *
 * @author Craig R. McClanahan
 * @author Jean-Francois Arcand
 */
public final class Constants {

    public static final String Package = "org.apache.catalina.startup";

    public static final String ApplicationContextXml = "META-INF/context.xml";
    public static final String ApplicationWebXml = "/WEB-INF/web.xml";
    public static final String DefaultContextXml = "conf/context.xml";
    public static final String DefaultWebXml = "conf/web.xml";
    public static final String HostContextXml = "context.xml.default";
    public static final String HostWebXml = "web.xml.default";

    public static final String DEFAULT_JARS_TO_SKIP =
            "tomcat.util.scan.DefaultJarScanner.jarsToSkip";
    public static final String PLUGGABILITY_JARS_TO_SKIP =
            "org.apache.catalina.startup.ContextConfig.jarsToSkip";
    public static final String TLD_JARS_TO_SKIP =
            "org.apache.catalina.startup.TldConfig.jarsToSkip";

    /**
     * A dummy value used to suppress loading the default web.xml file.
     *
     * <p>
     * It is useful when embedding Tomcat, when the default configuration is
     * done programmatically, e.g. by calling
     * <code>Tomcat.initWebappDefaults(context)</code>.
     *
     * @see Tomcat
     */
    public static final String NoDefaultWebXml = "org/apache/catalina/startup/NO_DEFAULT_XML";

    // J2EE
    public static final String J2eeSchemaPublicId_14 =
        "j2ee_1_4.xsd";
    public static final String J2eeSchemaResourcePath_14 =
        "/javax/servlet/resources/j2ee_1_4.xsd";

    public static final String JavaeeSchemaPublicId_5 =
        "javaee_5.xsd";
    public static final String JavaeeSchemaResourcePath_5 =
        "/javax/servlet/resources/javaee_5.xsd";

    public static final String JavaeeSchemaPublicId_6 =
        "javaee_6.xsd";
    public static final String JavaeeSchemaResourcePath_6 =
        "/javax/servlet/resources/javaee_6.xsd";

    
    // W3C
    public static final String W3cSchemaPublicId_10 =
        "xml.xsd";
    public static final String W3cSchemaResourcePath_10 =
        "/javax/servlet/resources/xml.xsd";

    public static final String W3cSchemaDTDPublicId_10 =
        "XMLSchema.dtd";
    public static final String W3cSchemaDTDResourcePath_10 =
        "/javax/servlet/resources/XMLSchema.dtd";

    public static final String W3cDatatypesDTDPublicId_10 =
        "datatypes.dtd";
    public static final String W3cDatatypesDTDResourcePath_10 =
        "/javax/servlet/resources/datatypes.dtd";

    
    // JSP
    public static final String JspSchemaPublicId_20 =
        "jsp_2_0.xsd";
    public static final String JspSchemaResourcePath_20 =
        "/javax/servlet/jsp/resources/jsp_2_0.xsd";
    
    public static final String JspSchemaPublicId_21 =
        "jsp_2_1.xsd";
    public static final String JspSchemaResourcePath_21 =
        "/javax/servlet/jsp/resources/jsp_2_1.xsd";

    public static final String JspSchemaPublicId_22 =
        "jsp_2_2.xsd";
    public static final String JspSchemaResourcePath_22 =
        "/javax/servlet/jsp/resources/jsp_2_2.xsd";


    // TLD
    public static final String TldDtdPublicId_11 =
        "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN";
    public static final String TldDtdResourcePath_11 =
        "/javax/servlet/jsp/resources/web-jsptaglibrary_1_1.dtd";

    public static final String TldDtdPublicId_12 =
        "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";
    public static final String TldDtdResourcePath_12 =
        "/javax/servlet/jsp/resources/web-jsptaglibrary_1_2.dtd";

    public static final String TldSchemaPublicId_20 =
        "web-jsptaglibrary_2_0.xsd";
    public static final String TldSchemaResourcePath_20 =
        "/javax/servlet/jsp/resources/web-jsptaglibrary_2_0.xsd";

    public static final String TldSchemaPublicId_21 =
        "web-jsptaglibrary_2_1.xsd";
    public static final String TldSchemaResourcePath_21 =
        "/javax/servlet/jsp/resources/web-jsptaglibrary_2_1.xsd";

    
    // web.xml
    public static final String WebDtdPublicId_22 =
        "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN";
    public static final String WebDtdResourcePath_22 =
        "/javax/servlet/resources/web-app_2_2.dtd";

    public static final String WebDtdPublicId_23 =
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN";
    public static final String WebDtdResourcePath_23 =
        "/javax/servlet/resources/web-app_2_3.dtd";

    public static final String WebSchemaPublicId_24 =
        "web-app_2_4.xsd";
    public static final String WebSchemaResourcePath_24 =
        "/javax/servlet/resources/web-app_2_4.xsd";

    public static final String WebSchemaPublicId_25 =
        "web-app_2_5.xsd";
    public static final String WebSchemaResourcePath_25 =
        "/javax/servlet/resources/web-app_2_5.xsd";

    public static final String WebSchemaPublicId_30 =
        "web-app_3_0.xsd";
    public static final String WebSchemaResourcePath_30 =
        "/javax/servlet/resources/web-app_3_0.xsd";

    public static final String WebCommonSchemaPublicId_30 =
        "web-common_3_0.xsd";
    public static final String WebCommonSchemaResourcePath_30 =
        "/javax/servlet/resources/web-common_3_0.xsd";

    public static final String WebFragmentSchemaPublicId_30 =
        "web-fragment_3_0.xsd";
    public static final String WebFragmentSchemaResourcePath_30 =
        "/javax/servlet/resources/web-fragment_3_0.xsd";
    
    // Web service
    public static final String J2eeWebServiceSchemaPublicId_11 =
            "j2ee_web_services_1_1.xsd";
    public static final String J2eeWebServiceSchemaResourcePath_11 =
            "/javax/servlet/resources/j2ee_web_services_1_1.xsd";
    
    public static final String J2eeWebServiceClientSchemaPublicId_11 =
            "j2ee_web_services_client_1_1.xsd";
    public static final String J2eeWebServiceClientSchemaResourcePath_11 =
            "/javax/servlet/resources/j2ee_web_services_client_1_1.xsd";

    public static final String JavaeeWebServiceSchemaPublicId_12 =
        "javaee_web_services_1_2.xsd";
    public static final String JavaeeWebServiceSchemaResourcePath_12 =
        "/javax/servlet/resources/javaee_web_services_1_2.xsd";

    public static final String JavaeeWebServiceClientSchemaPublicId_12 =
        "javaee_web_services_client_1_2.xsd";
    public static final String JavaeeWebServiceClientSchemaResourcePath_12 =
        "/javax/servlet/resources/javaee_web_services_client_1_2.xsd";

    public static final String JavaeeWebServiceSchemaPublicId_13 =
        "javaee_web_services_1_3.xsd";
    public static final String JavaeeWebServiceSchemaResourcePath_13 =
        "/javax/servlet/resources/javaee_web_services_1_3.xsd";

    public static final String JavaeeWebServiceClientSchemaPublicId_13 =
        "javaee_web_services_client_1_3.xsd";
    public static final String JavaeeWebServiceClientSchemaResourcePath_13 =
        "/javax/servlet/resources/javaee_web_services_client_1_3.xsd";

}
