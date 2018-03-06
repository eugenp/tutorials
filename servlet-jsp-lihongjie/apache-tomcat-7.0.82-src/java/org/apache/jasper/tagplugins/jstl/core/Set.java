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

public class Set implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        
        //the flags to indicate whether the attributes have been specified
        boolean hasValue = false, hasVar = false, hasScope = false, 
        hasTarget = false;
        
        //the scope name
        String strScope;
        //the id of the scope
        int iScope;
        
        //initialize the flags
        hasValue = ctxt.isAttributeSpecified("value");
        hasVar = ctxt.isAttributeSpecified("var");
        hasScope = ctxt.isAttributeSpecified("scope");
        hasTarget = ctxt.isAttributeSpecified("target");
        
        //the temp variables name
        String resultName = ctxt.getTemporaryVariableName();
        String targetName = ctxt.getTemporaryVariableName();
        String propertyName = ctxt.getTemporaryVariableName();
        
        //initialize the "result" which will be assigned to the var or target.property
        ctxt.generateJavaSource("Object " + resultName + " = null;");
        if(hasValue){
            ctxt.generateJavaSource(resultName + " = ");
            ctxt.generateAttribute("value");
            ctxt.generateJavaSource(";");
        }else{
            ctxt.dontUseTagPlugin();
            return;
        }
        
        //initialize the strScope
        if(hasScope){
            strScope = ctxt.getConstantAttribute("scope");
        }else{
            strScope = "page";
        }
        
        //get the iScope according to the strScope
        iScope = Util.getScope(strScope);
        
        //if the attribute var has been specified then assign the result to the var;
        if(hasVar){
            String jspCtxt = null;
            if (ctxt.isTagFile()) {
                jspCtxt = "this.getJspContext()";
            } else {
                jspCtxt = "_jspx_page_context";
            }
            String strVar = ctxt.getConstantAttribute("var");
            ctxt.generateJavaSource("if(null != " + resultName + "){");
            ctxt.generateJavaSource("    " + jspCtxt + ".setAttribute(\"" + strVar + "\"," + resultName + "," + iScope + ");");
            ctxt.generateJavaSource("} else {");
            if(hasScope){
                ctxt.generateJavaSource("    " + jspCtxt + ".removeAttribute(\"" + strVar + "\"," + iScope + ");");
            }else{
                ctxt.generateJavaSource("    " + jspCtxt + ".removeAttribute(\"" + strVar + "\");");
            }
            ctxt.generateJavaSource("}");
            
            //else assign the result to the target.property
        }else if(hasTarget){
            
            //generate the temp variable name
            String pdName = ctxt.getTemporaryVariableName();
            String successFlagName = ctxt.getTemporaryVariableName();
            String index = ctxt.getTemporaryVariableName();
            String methodName = ctxt.getTemporaryVariableName();
            
            //initialize the property
            ctxt.generateJavaSource("String " + propertyName + " = null;");
            ctxt.generateJavaSource("if(");
            ctxt.generateAttribute("property");
            ctxt.generateJavaSource(" != null){");
            ctxt.generateJavaSource("    " + propertyName + " = (");
            ctxt.generateAttribute("property");
            ctxt.generateJavaSource(").toString();");
            ctxt.generateJavaSource("}");
            
            //initialize the target
            ctxt.generateJavaSource("Object " + targetName + " = ");
            ctxt.generateAttribute("target");
            ctxt.generateJavaSource(";");
            
            //the target is ok
            ctxt.generateJavaSource("if(" + targetName + " != null){");
            
            //if the target is a map, then put the result into the map with the key property
            ctxt.generateJavaSource("    if(" + targetName + " instanceof java.util.Map){");
            ctxt.generateJavaSource("        if(null != " + resultName + "){");
            ctxt.generateJavaSource("            ((java.util.Map) " + targetName + ").put(" + propertyName + "," + resultName + ");");
            ctxt.generateJavaSource("        }else{");
            ctxt.generateJavaSource("            ((java.util.Map) " + targetName + ").remove(" + propertyName + ");");
            ctxt.generateJavaSource("        }");
            
            //else assign the result to the target.property
            ctxt.generateJavaSource("    }else{");
            ctxt.generateJavaSource("        try{");
            
            //get all the property of the target
            ctxt.generateJavaSource("            java.beans.PropertyDescriptor " + pdName + "[] = java.beans.Introspector.getBeanInfo(" + targetName + ".getClass()).getPropertyDescriptors();");
            
            //the success flag is to imply whether the assign is successful
            ctxt.generateJavaSource("            boolean " + successFlagName + " = false;");
            
            //find the right property
            ctxt.generateJavaSource("            for(int " + index + "=0;" + index + "<" + pdName + ".length;" + index + "++){");
            ctxt.generateJavaSource("                if(" + pdName + "[" + index + "].getName().equals(" + propertyName + ")){");
            
            //get the "set" method;
            ctxt.generateJavaSource("                    java.lang.reflect.Method " + methodName + " = " + pdName + "[" + index + "].getWriteMethod();");
            ctxt.generateJavaSource("                    if(null == " + methodName + "){");
            ctxt.generateJavaSource("                        throw new JspException(\"No setter method in &lt;set&gt; for property \"+" + propertyName + ");");
            ctxt.generateJavaSource("                    }");
            
            //invoke the method through the reflection
            ctxt.generateJavaSource("                    if(" + resultName + " != null){");
            ctxt.generateJavaSource("                        " + methodName + ".invoke(" + targetName + ", new Object[]{org.apache.el.lang.ELSupport.coerceToType(" + resultName + ", " + methodName + ".getParameterTypes()[0])});");
            ctxt.generateJavaSource("                    }else{");
            ctxt.generateJavaSource("                        " + methodName + ".invoke(" + targetName + ", new Object[]{null});");
            ctxt.generateJavaSource("                    }");
            ctxt.generateJavaSource("                    " + successFlagName + " = true;");
            ctxt.generateJavaSource("                }");
            ctxt.generateJavaSource("            }");
            ctxt.generateJavaSource("            if(!" + successFlagName + "){");
            ctxt.generateJavaSource("                throw new JspException(\"Invalid property in &lt;set&gt;:\"+" + propertyName + ");");
            ctxt.generateJavaSource("            }");
            ctxt.generateJavaSource("        }");
            
            //catch the el exception and throw it as a JspException
            ctxt.generateJavaSource("        catch (IllegalAccessException ex) {");
            ctxt.generateJavaSource("            throw new JspException(ex);");
            ctxt.generateJavaSource("        } catch (java.beans.IntrospectionException ex) {");
            ctxt.generateJavaSource("            throw new JspException(ex);");
            ctxt.generateJavaSource("        } catch (java.lang.reflect.InvocationTargetException ex) {");
            ctxt.generateJavaSource("            if (ex.getCause() instanceof ThreadDeath) {");
            ctxt.generateJavaSource("                throw (ThreadDeath) ex.getCause();");
            ctxt.generateJavaSource("            }");
            ctxt.generateJavaSource("            if (ex.getCause() instanceof VirtualMachineError) {");
            ctxt.generateJavaSource("                throw (VirtualMachineError) ex.getCause();");
            ctxt.generateJavaSource("            }");
            ctxt.generateJavaSource("            throw new JspException(ex);");
            ctxt.generateJavaSource("        }");
            ctxt.generateJavaSource("    }");
            ctxt.generateJavaSource("}else{");
            ctxt.generateJavaSource("    throw new JspException();");
            ctxt.generateJavaSource("}");
        }
    }
    
}
