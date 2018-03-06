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

public class Catch implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        
        //flag for the existence of the var attribute
        boolean hasVar = ctxt.isAttributeSpecified("var");
        
        //temp name for exception and caught
        String exceptionName = ctxt.getTemporaryVariableName();
        String caughtName = ctxt.getTemporaryVariableName();
        
        //main part to generate code
        ctxt.generateJavaSource("boolean " + caughtName + " = false;");
        ctxt.generateJavaSource("try{");
        ctxt.generateBody();
        ctxt.generateJavaSource("}");
        
        //do catch
        ctxt.generateJavaSource("catch(Throwable " + exceptionName + "){");
        
        //if the var specified, the exception object should 
        //be set to the attribute "var" defines in page scope 
        if(hasVar){
            String strVar = ctxt.getConstantAttribute("var");
            ctxt.generateJavaSource("    pageContext.setAttribute(\"" + strVar + "\", " 
                    + exceptionName + ", PageContext.PAGE_SCOPE);");
        }
        
        //whenever there's exception caught, 
        //the flag caught should be set true;
        ctxt.generateJavaSource("    " + caughtName + " = true;");
        ctxt.generateJavaSource("}");
        
        //do finally
        ctxt.generateJavaSource("finally{");
        
        //if var specified, the attribute it defines 
        //in page scope should be removed
        if(hasVar){
            String strVar = ctxt.getConstantAttribute("var");
            ctxt.generateJavaSource("    if(!" + caughtName + "){");
            ctxt.generateJavaSource("        pageContext.removeAttribute(\"" + strVar + "\", PageContext.PAGE_SCOPE);");
            ctxt.generateJavaSource("    }");
        }
        
        ctxt.generateJavaSource("}");
    }
    
}
