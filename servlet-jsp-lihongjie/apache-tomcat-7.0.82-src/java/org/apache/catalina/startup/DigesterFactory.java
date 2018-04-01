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

import java.net.URL;

import org.apache.catalina.util.SchemaResolver;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.digester.Digester;
import org.apache.tomcat.util.digester.RuleSet;

/**
 * Wrapper class around the Digester that hide Digester's initialization details
 *
 * @author Jean-Francois Arcand
 * 
 * @deprecated Use {@link org.apache.tomcat.util.descriptor.DigesterFactory}
 */
@Deprecated
public class DigesterFactory {
    /**
     * The log.
     */
    private static final Log log = LogFactory.getLog(DigesterFactory.class);

    /**
     * Create a <code>Digester</code> parser with no <code>Rule</code>
     * associated and XML validation turned off.
     *
     * @deprecated  Unused - will be removed in 8.0.x
     */
    @Deprecated
    public static Digester newDigester(){
        return newDigester(false, false, null);
    }

    
    /**
     * Create a <code>Digester</code> parser with XML validation turned off.
     * @param rule an instance of <code>RuleSet</code> used for parsing the xml.
     *
     * @deprecated  Unused - will be removed in 8.0.x
     */
    @Deprecated
    public static Digester newDigester(RuleSet rule){
        return newDigester(false,false,rule);
    }

    
    /**
     * Create a <code>Digester</code> parser.
     * @param xmlValidation turn on/off xml validation
     * @param xmlNamespaceAware turn on/off namespace validation
     * @param rule an instance of <code>RuleSet</code> used for parsing the xml.
     */
    public static Digester newDigester(boolean xmlValidation,
                                       boolean xmlNamespaceAware,
                                       RuleSet rule) {
        Digester digester = new Digester();
        digester.setNamespaceAware(xmlNamespaceAware);
        digester.setValidating(xmlValidation);
        digester.setUseContextClassLoader(true);

        SchemaResolver schemaResolver = new SchemaResolver(digester);
        registerLocalSchema(schemaResolver);
        
        digester.setEntityResolver(schemaResolver);
        if ( rule != null ) {
            digester.addRuleSet(rule);
        }

        return (digester);
    }


    /**
     * Utilities used to force the parser to use local schema, when available,
     * instead of the <code>schemaLocation</code> XML element.
     */
    protected static void registerLocalSchema(SchemaResolver schemaResolver){
        // J2EE
        register(Constants.J2eeSchemaResourcePath_14,
                 Constants.J2eeSchemaPublicId_14,
                 schemaResolver);

        register(Constants.JavaeeSchemaResourcePath_5,
                Constants.JavaeeSchemaPublicId_5,
                schemaResolver);

        register(Constants.JavaeeSchemaResourcePath_6,
                Constants.JavaeeSchemaPublicId_6,
                schemaResolver);

        // W3C
        register(Constants.W3cSchemaResourcePath_10,
                 Constants.W3cSchemaPublicId_10,
                 schemaResolver);

        register(Constants.W3cSchemaDTDResourcePath_10,
                Constants.W3cSchemaDTDPublicId_10,
                schemaResolver);

        register(Constants.W3cDatatypesDTDResourcePath_10,
                Constants.W3cDatatypesDTDPublicId_10,
                schemaResolver);

        // JSP
        register(Constants.JspSchemaResourcePath_20,
                 Constants.JspSchemaPublicId_20,
                 schemaResolver);

        register(Constants.JspSchemaResourcePath_21,
                Constants.JspSchemaPublicId_21,
                schemaResolver);

        register(Constants.JspSchemaResourcePath_22,
                Constants.JspSchemaPublicId_22,
                schemaResolver);

        // TLD
        register(Constants.TldDtdResourcePath_11,  
                 Constants.TldDtdPublicId_11,
                 schemaResolver);
        
        register(Constants.TldDtdResourcePath_12,
                 Constants.TldDtdPublicId_12,
                 schemaResolver);

        register(Constants.TldSchemaResourcePath_20,
                 Constants.TldSchemaPublicId_20,
                 schemaResolver);

        register(Constants.TldSchemaResourcePath_21,
                Constants.TldSchemaPublicId_21,
                schemaResolver);

        // web.xml    
        register(Constants.WebDtdResourcePath_22,
                 Constants.WebDtdPublicId_22,
                 schemaResolver);

        register(Constants.WebDtdResourcePath_23,
                 Constants.WebDtdPublicId_23,
                 schemaResolver);

        register(Constants.WebSchemaResourcePath_24,
                 Constants.WebSchemaPublicId_24,
                 schemaResolver);

        register(Constants.WebSchemaResourcePath_25,
                Constants.WebSchemaPublicId_25,
                schemaResolver);

        register(Constants.WebSchemaResourcePath_30,
                Constants.WebSchemaPublicId_30,
                schemaResolver);

        register(Constants.WebCommonSchemaResourcePath_30,
                Constants.WebCommonSchemaPublicId_30,
                schemaResolver);
        
        register(Constants.WebFragmentSchemaResourcePath_30,
                Constants.WebFragmentSchemaPublicId_30,
                schemaResolver);

        // Web Service
        register(Constants.J2eeWebServiceSchemaResourcePath_11,
                 Constants.J2eeWebServiceSchemaPublicId_11,
                 schemaResolver);

        register(Constants.J2eeWebServiceClientSchemaResourcePath_11,
                 Constants.J2eeWebServiceClientSchemaPublicId_11,
                 schemaResolver);

        register(Constants.JavaeeWebServiceSchemaResourcePath_12,
                Constants.JavaeeWebServiceSchemaPublicId_12,
                schemaResolver);

        register(Constants.JavaeeWebServiceClientSchemaResourcePath_12,
                Constants.JavaeeWebServiceClientSchemaPublicId_12,
                schemaResolver);

        register(Constants.JavaeeWebServiceSchemaResourcePath_13,
                Constants.JavaeeWebServiceSchemaPublicId_13,
                schemaResolver);

        register(Constants.JavaeeWebServiceClientSchemaResourcePath_13,
                Constants.JavaeeWebServiceClientSchemaPublicId_13,
                schemaResolver);
    }


    /**
     * Load the resource and add it to the resolver.
     */
    protected static void register(String resourceURL, String resourcePublicId,
            SchemaResolver schemaResolver){
        URL url = DigesterFactory.class.getResource(resourceURL);
   
        if(url == null) {
            log.warn("Could not get url for " + resourceURL);
        } else {
            schemaResolver.register(resourcePublicId , url.toString() );
        }
    }

}
