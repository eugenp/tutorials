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


package org.apache.tomcat.util.digester;

import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Creates a <code>SAXParser</code> based on the underlying parser.
 * Allows logical properties depending on logical parser versions
 * to be set.
 *
 * @since 1.6
 */
public class ParserFeatureSetterFactory{

    /**
     * <code>true</code> is Xerces is used.
     */
    private static boolean isXercesUsed; 

    static {
        try{
            // Use reflection to avoid a build dependency with Xerces.
            Class.forName("org.apache.xerces.impl.Version");
            isXercesUsed = true;
        } catch (Exception ex){
            isXercesUsed = false;
        }
    }

    /**
     * Create a new <code>SAXParser</code>
     * @param properties (logical) properties to be set on parser
     * @return a <code>SAXParser</code> configured based on the underlying
     * parser implementation.
     */
    public static SAXParser newSAXParser(Properties properties)
            throws ParserConfigurationException, 
                   SAXException,
                   SAXNotRecognizedException, 
                   SAXNotSupportedException {

        if (isXercesUsed){
            return XercesParser.newSAXParser(properties);
        } else {
            return GenericParser.newSAXParser(properties);
        }
    }

}