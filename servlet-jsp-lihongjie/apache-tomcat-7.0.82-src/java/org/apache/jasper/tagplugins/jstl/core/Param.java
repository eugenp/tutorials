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

public class Param implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        
        //don't support the body content
        
        //define names of all the temp variables
        String nameName = ctxt.getTemporaryVariableName();
        String valueName = ctxt.getTemporaryVariableName();
        String urlName = ctxt.getTemporaryVariableName();
        String encName = ctxt.getTemporaryVariableName();
        String index = ctxt.getTemporaryVariableName();
        
        //if the param tag has no parents, throw a exception
        TagPluginContext parent = ctxt.getParentContext();
        if(parent == null){
            ctxt.generateJavaSource(" throw new JspTagException" +
            "(\"&lt;param&gt; outside &lt;import&gt; or &lt;urlEncode&gt;\");");
            return;
        }
        
        //get the url string before adding this param
        ctxt.generateJavaSource("String " + urlName + " = " +
        "(String)pageContext.getAttribute(\"url_without_param\");");
        
        //get the value of "name"
        ctxt.generateJavaSource("String " + nameName + " = ");
        ctxt.generateAttribute("name");
        ctxt.generateJavaSource(";");
        
        //if the "name" is null then do nothing.
        //else add such string "name=value" to the url.
        //and the url should be encoded
        ctxt.generateJavaSource("if(" + nameName + " != null && !" + nameName + ".equals(\"\")){");
        ctxt.generateJavaSource("    String " + valueName + " = ");
        ctxt.generateAttribute("value");
        ctxt.generateJavaSource(";");
        ctxt.generateJavaSource("    if(" + valueName + " == null) " + valueName + " = \"\";");
        ctxt.generateJavaSource("    String " + encName + " = pageContext.getResponse().getCharacterEncoding();");
        ctxt.generateJavaSource("    " + nameName + " = java.net.URLEncoder.encode(" + nameName + ", " + encName + ");");
        ctxt.generateJavaSource("    " + valueName + " = java.net.URLEncoder.encode(" + valueName + ", " + encName + ");");
        ctxt.generateJavaSource("    int " + index + ";");
        ctxt.generateJavaSource("    " + index + " = " + urlName + ".indexOf(\'?\');");
        //if the current param is the first one, add a "?" ahead of it
        //else add a "&" ahead of it
        ctxt.generateJavaSource("    if(" + index + " == -1){");
        ctxt.generateJavaSource("        " + urlName + " = " + urlName + " + \"?\" + " + nameName + " + \"=\" + " + valueName + ";");
        ctxt.generateJavaSource("    }else{");
        ctxt.generateJavaSource("        " + urlName + " = " + urlName + " + \"&\" + " + nameName + " + \"=\" + " + valueName + ";");
        ctxt.generateJavaSource("    }");
        ctxt.generateJavaSource("    pageContext.setAttribute(\"url_without_param\"," + urlName + ");");
        ctxt.generateJavaSource("}");
    }
}
