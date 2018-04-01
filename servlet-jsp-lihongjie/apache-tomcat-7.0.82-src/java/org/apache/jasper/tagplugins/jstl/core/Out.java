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


package org.apache.jasper.tagplugins.jstl.core;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.jsp.JspWriter;

import org.apache.jasper.compiler.tagplugin.TagPlugin;
import org.apache.jasper.compiler.tagplugin.TagPluginContext;
import org.apache.jasper.tagplugins.jstl.Util;


public final class Out implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        
        //these two data member are to indicate 
        //whether the corresponding attribute is specified
        boolean hasDefault=false, hasEscapeXml=false;
        hasDefault = ctxt.isAttributeSpecified("default");
        hasEscapeXml = ctxt.isAttributeSpecified("escapeXml");
        
        //strValName, strEscapeXmlName & strDefName are two variables' name 
        //standing for value, escapeXml and default attribute
        String strObjectName = ctxt.getTemporaryVariableName();
        String strValName = ctxt.getTemporaryVariableName();
        String strDefName = ctxt.getTemporaryVariableName();
        String strEscapeXmlName = ctxt.getTemporaryVariableName();
        String strSkipBodyName = ctxt.getTemporaryVariableName();

        //according to the tag file, the value attribute is mandatory.
        ctxt.generateImport("java.io.Reader");
        ctxt.generateJavaSource("Object " + strObjectName + "=");
        ctxt.generateAttribute("value");
        ctxt.generateJavaSource(";");
        ctxt.generateJavaSource("String " + strValName + "=null;");
        ctxt.generateJavaSource("if(!(" + strObjectName +
                " instanceof Reader) && "+ strObjectName + " != null){");
        ctxt.generateJavaSource(
                strValName + " = " + strObjectName + ".toString();");
        ctxt.generateJavaSource("}");
        
        //initiate the strDefName with null.
        //if the default has been specified, then assign the value to it;
        ctxt.generateJavaSource("String " + strDefName + " = null;");
        if(hasDefault){
            ctxt.generateJavaSource("if(");
            ctxt.generateAttribute("default");
            ctxt.generateJavaSource(" != null){");
            ctxt.generateJavaSource(strDefName + " = (");
            ctxt.generateAttribute("default");
            ctxt.generateJavaSource(").toString();");
            ctxt.generateJavaSource("}");
        }
        
        //initiate the strEscapeXmlName with true;
        //if the escapeXml is specified, assign the value to it;
        ctxt.generateJavaSource("boolean " + strEscapeXmlName + " = true;");
        if(hasEscapeXml){
            ctxt.generateJavaSource(strEscapeXmlName + " = ");
            ctxt.generateAttribute("escapeXml");
            ctxt.generateJavaSource(";");
        }
        
        //main part. 
        ctxt.generateJavaSource(
                "boolean " + strSkipBodyName + " = " +
                "org.apache.jasper.tagplugins.jstl.core.Out.output(out, " +
                strObjectName + ", " + strValName + ", " + strDefName + ", " +
                strEscapeXmlName + ");");
        ctxt.generateJavaSource("if(!" + strSkipBodyName + ") {");
        ctxt.generateBody();
        ctxt.generateJavaSource("}");
    }

    public static boolean output(JspWriter out, Object input, String value,
            String defaultValue, boolean escapeXml) throws IOException {
        if (input instanceof Reader) {
            char[] buffer = new char[8096];
            int read = 0;
            while (read != -1) {
                read = ((Reader) input).read(buffer);
                if (read != -1) {
                    if (escapeXml) {
                        String escaped = Util.escapeXml(buffer, read);
                        if (escaped == null) {
                            out.write(buffer, 0, read);
                        } else {
                            out.print(escaped);
                        }
                    } else {
                        out.write(buffer, 0, read);
                    }
                }
            }
            return true;
        } else {
            String v = value != null ? value : defaultValue;
            if (v != null) {
                if(escapeXml){
                    v = Util.escapeXml(v);
                }
                out.write(v);
                return true;
            } else {
                return false;
            }
        }
    }
}
