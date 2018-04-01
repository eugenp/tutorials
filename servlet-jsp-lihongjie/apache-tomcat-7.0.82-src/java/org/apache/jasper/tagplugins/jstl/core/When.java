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

public final class When implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        // Get the parent context to determine if this is the first <c:when>
        TagPluginContext parentContext = ctxt.getParentContext();
        if (parentContext == null) {
            ctxt.dontUseTagPlugin();
            return;
        }
        
        if ("true".equals(parentContext.getPluginAttribute("hasBeenHere"))) {
            ctxt.generateJavaSource("} else if(");
            // See comment below for the reason we generate the extra "}" here.
        }
        else {
            ctxt.generateJavaSource("if(");
            parentContext.setPluginAttribute("hasBeenHere", "true");
        }
        ctxt.generateAttribute("test");
        ctxt.generateJavaSource("){");
        ctxt.generateBody();
        
        // We don't generate the closing "}" for the "if" here because there
        // may be whitespaces in between <c:when>'s.  Instead we delay
        // generating it until the next <c:when> or <c:otherwise> or
        // <c:choose>
    }
}
