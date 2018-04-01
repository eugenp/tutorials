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

public class ForTokens implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        boolean hasVar, hasVarStatus, hasBegin, hasEnd, hasStep;
        
        //init the flags
        hasVar = ctxt.isAttributeSpecified("var");
        hasVarStatus = ctxt.isAttributeSpecified("varStatus");
        hasBegin = ctxt.isAttributeSpecified("begin");
        hasEnd = ctxt.isAttributeSpecified("end");
        hasStep = ctxt.isAttributeSpecified("step");
        
        if(hasVarStatus){
            ctxt.dontUseTagPlugin();
            return;
        }
        
        //define all the temp variables' names
        String itemsName = ctxt.getTemporaryVariableName();
        String delimsName = ctxt.getTemporaryVariableName();
        String stName = ctxt.getTemporaryVariableName();
        String beginName = ctxt.getTemporaryVariableName();
        String endName  = ctxt.getTemporaryVariableName();
        String stepName = ctxt.getTemporaryVariableName();
        String index = ctxt.getTemporaryVariableName();
        String temp  = ctxt.getTemporaryVariableName();
        String tokensCountName = ctxt.getTemporaryVariableName();
        
        //get the value of the "items" attribute 
        ctxt.generateJavaSource("String " + itemsName + " = (String)");
        ctxt.generateAttribute("items");
        ctxt.generateJavaSource(";");
        
        //get the value of the "delim" attribute
        ctxt.generateJavaSource("String " + delimsName + " = (String)");
        ctxt.generateAttribute("delims");
        ctxt.generateJavaSource(";");
        
        //new a StringTokenizer Object according to the "items" and the "delim"
        ctxt.generateJavaSource("java.util.StringTokenizer " + stName + " = " +
                "new java.util.StringTokenizer(" + itemsName + ", " + delimsName + ");");
        
        //if "begin" specified, move the token to the "begin" place
        //and record the begin index. default begin place is 0.
        ctxt.generateJavaSource("int " + tokensCountName + " = " + stName + ".countTokens();");
        if(hasBegin){
            ctxt.generateJavaSource("int " + beginName + " = "  );
            ctxt.generateAttribute("begin");
            ctxt.generateJavaSource(";");
            ctxt.generateJavaSource("for(int " + index + " = 0; " + index + " < " + beginName + " && " + stName + ".hasMoreTokens(); " + index + "++, " + stName + ".nextToken()){}");
        }else{
            ctxt.generateJavaSource("int " + beginName + " = 0;");
        }
        
        //when "end" is specified, if the "end" is more than the last index,
        //record the end place as the last index, otherwise, record it as "end";
        //default end place is the last index 
        if(hasEnd){
            ctxt.generateJavaSource("int " + endName + " = 0;"  );
            ctxt.generateJavaSource("if((" + tokensCountName + " - 1) < ");
            ctxt.generateAttribute("end");
            ctxt.generateJavaSource("){");
            ctxt.generateJavaSource("    " + endName + " = " + tokensCountName + " - 1;");
            ctxt.generateJavaSource("}else{");
            ctxt.generateJavaSource("    " + endName + " = ");
            ctxt.generateAttribute("end");
            ctxt.generateJavaSource(";}");
        }else{
            ctxt.generateJavaSource("int " + endName + " = " + tokensCountName + " - 1;");
        }
        
        //get the step value from "step" if specified.
        //default step value is 1.
        if(hasStep){
            ctxt.generateJavaSource("int " + stepName + " = "  );
            ctxt.generateAttribute("step");
            ctxt.generateJavaSource(";");
        }else{
            ctxt.generateJavaSource("int " + stepName + " = 1;");
        }
        
        //the loop
        ctxt.generateJavaSource("for(int " + index + " = " + beginName + "; " + index + " <= " + endName + "; " + index + "++){");
        ctxt.generateJavaSource("    String " + temp + " = " + stName + ".nextToken();");
        ctxt.generateJavaSource("    if(((" + index + " - " + beginName + ") % " + stepName + ") == 0){");
        //if var specified, put the current token into the attribute "var" defines.
        if(hasVar){
            String strVar = ctxt.getConstantAttribute("var");
            ctxt.generateJavaSource("        pageContext.setAttribute(\"" + strVar + "\", " + temp + ");");
        }
        ctxt.generateBody();
        ctxt.generateJavaSource("    }");
        ctxt.generateJavaSource("}");
    }
    
}
