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

import org.apache.jasper.compiler.tagplugin.TagPlugin;
import org.apache.jasper.compiler.tagplugin.TagPluginContext;
import org.apache.jasper.tagplugins.jstl.Util;

public class Url implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        
        //flags
        boolean hasVar, hasContext, hasScope;
        
        //init flags
        hasVar = ctxt.isAttributeSpecified("var");
        hasContext = ctxt.isAttributeSpecified("context");
        hasScope = ctxt.isAttributeSpecified("scope");
        
        //define name of the temp variables
        String valueName = ctxt.getTemporaryVariableName();
        String contextName = ctxt.getTemporaryVariableName();
        String baseUrlName = ctxt.getTemporaryVariableName();
        String resultName = ctxt.getTemporaryVariableName();
        String responseName = ctxt.getTemporaryVariableName();
        
        //get the scope
        String strScope = "page";
        if(hasScope){
            strScope = ctxt.getConstantAttribute("scope");
        }
        int iScope = Util.getScope(strScope);
        
        //get the value
        ctxt.generateJavaSource("String " + valueName + " = ");
        ctxt.generateAttribute("value");
        ctxt.generateJavaSource(";");
        
        //get the context
        ctxt.generateJavaSource("String " + contextName + " = null;");
        if(hasContext){
            ctxt.generateJavaSource(contextName + " = ");
            ctxt.generateAttribute("context");
            ctxt.generateJavaSource(";");
        }
        
        //get the raw url
        ctxt.generateJavaSource("String " + baseUrlName + " = " +
                "org.apache.jasper.tagplugins.jstl.Util.resolveUrl(" + valueName + ", " + contextName + ", pageContext);");
        ctxt.generateJavaSource("pageContext.setAttribute" +
                "(\"url_without_param\", " + baseUrlName + ");");
        
        //add params
        ctxt.generateBody();
        
        ctxt.generateJavaSource("String " + resultName + " = " +
        "(String)pageContext.getAttribute(\"url_without_param\");");
        ctxt.generateJavaSource("pageContext.removeAttribute(\"url_without_param\");");
        
        //if the url is relative, encode it
        ctxt.generateJavaSource("if(!org.apache.jasper.tagplugins.jstl.Util.isAbsoluteUrl(" + resultName + ")){");
        ctxt.generateJavaSource("    HttpServletResponse " + responseName + " = " +
        "((HttpServletResponse) pageContext.getResponse());");
        ctxt.generateJavaSource("    " + resultName + " = "
                + responseName + ".encodeURL(" + resultName + ");");
        ctxt.generateJavaSource("}");
        
        //if "var" is specified, the url string store in the attribute var defines
        if(hasVar){
            String strVar = ctxt.getConstantAttribute("var");
            ctxt.generateJavaSource("pageContext.setAttribute" +
                    "(\"" + strVar + "\", " + resultName + ", " + iScope + ");");
            
            //if var is not specified, just print out the url string
        }else{
            ctxt.generateJavaSource("try{");
            ctxt.generateJavaSource("    pageContext.getOut().print(" + resultName + ");");
            ctxt.generateJavaSource("}catch(java.io.IOException ex){");
            ctxt.generateJavaSource("    throw new JspTagException(ex.toString(), ex);");
            ctxt.generateJavaSource("}");
        }
    }
    
}
