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

public class Remove implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        
        //scope flag
        boolean hasScope = ctxt.isAttributeSpecified("scope");
        
        //the value of the "var"
        String strVar = ctxt.getConstantAttribute("var");
        
        //remove attribute from certain scope.
        //default scope is "page".
        if(hasScope){
            int iScope = Util.getScope(ctxt.getConstantAttribute("scope"));
            ctxt.generateJavaSource("pageContext.removeAttribute(\"" + strVar + "\"," + iScope + ");");
        }else{
            ctxt.generateJavaSource("pageContext.removeAttribute(\"" + strVar + "\");");
        }
    }
    
}
