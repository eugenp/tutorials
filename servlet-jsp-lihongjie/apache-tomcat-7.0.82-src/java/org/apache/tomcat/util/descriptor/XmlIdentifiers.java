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
package org.apache.tomcat.util.descriptor;

/**
 * Defines constants for well-known Public and System identifiers documented by
 * the Servlet and JSP specifications.
 */
public final class XmlIdentifiers {

    // from W3C
    public static final String XML_2001_XSD = "http://www.w3.org/2001/xml.xsd";
    public static final String DATATYPES_PUBLIC = "datatypes";
    public static final String XSD_10_PUBLIC =
            "-//W3C//DTD XMLSCHEMA 200102//EN";

    // from J2EE 1.2
    public static final String WEB_22_PUBLIC =
            "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN";
    public static final String WEB_22_SYSTEM =
            "http://java.sun.com/dtd/web-app_2_2.dtd";
    public static final String TLD_11_PUBLIC =
            "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN";
    public static final String TLD_11_SYSTEM =
            "http://java.sun.com/dtd/web-jsptaglibrary_1_1.dtd";

    // from J2EE 1.3
    public static final String WEB_23_PUBLIC =
            "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN";
    public static final String WEB_23_SYSTEM =
            "http://java.sun.com/dtd/web-app_2_3.dtd";
    public static final String TLD_12_PUBLIC =
            "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN";
    public static final String TLD_12_SYSTEM =
            "http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd";

    // from J2EE 1.4
    public static final String JAVAEE_1_4_NS = "http://java.sun.com/xml/ns/j2ee";
    public static final String WEB_24_XSD = JAVAEE_1_4_NS + "/web-app_2_4.xsd";
    public static final String TLD_20_XSD = JAVAEE_1_4_NS + "/web-jsptaglibrary_2_0.xsd";
    public static final String WEBSERVICES_11_XSD =
            "http://www.ibm.com/webservices/xsd/j2ee_web_services_1_1.xsd";

    // from JavaEE 5
    public static final String JAVAEE_5_NS = "http://java.sun.com/xml/ns/javaee";
    public static final String WEB_25_XSD = JAVAEE_5_NS + "/web-app_2_5.xsd";
    public static final String TLD_21_XSD = JAVAEE_5_NS + "/web-jsptaglibrary_2_1.xsd";
    public static final String WEBSERVICES_12_XSD = JAVAEE_5_NS + "javaee_web_services_1_2.xsd";

    // from JavaEE 6
    public static final String JAVAEE_6_NS = JAVAEE_5_NS;
    public static final String WEB_30_XSD = JAVAEE_6_NS + "/web-app_3_0.xsd";
    public static final String WEB_FRAGMENT_30_XSD = JAVAEE_6_NS + "/web-fragment_3_0.xsd";
    public static final String WEBSERVICES_13_XSD = JAVAEE_6_NS + "/javaee_web_services_1_3.xsd";

    private XmlIdentifiers() {
    }
}