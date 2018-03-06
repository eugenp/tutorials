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

public class Import implements TagPlugin {
    
    @Override
    public void doTag(TagPluginContext ctxt) {
        boolean hasContext, hasVar, hasScope, hasVarReader, hasCharEncoding;
        
        //flags
        hasContext  = ctxt.isAttributeSpecified("context");
        hasVar = ctxt.isAttributeSpecified("var");
        hasScope = ctxt.isAttributeSpecified("scope");
        hasVarReader = ctxt.isAttributeSpecified("varReader");
        hasCharEncoding = ctxt.isAttributeSpecified("charEncoding");
        
        //variables' names
        String urlName = ctxt.getTemporaryVariableName();           
        String contextName = ctxt.getTemporaryVariableName();       
        String iauName = ctxt.getTemporaryVariableName();           // is absolute url
        String urlObjName = ctxt.getTemporaryVariableName();        //URL object
        String ucName = ctxt.getTemporaryVariableName();            //URLConnection
        String inputStreamName = ctxt.getTemporaryVariableName();   
        String tempReaderName = ctxt.getTemporaryVariableName();
        String tempReaderName2 = ctxt.getTemporaryVariableName();
        String charSetName = ctxt.getTemporaryVariableName();
        String charEncodingName = ctxt.getTemporaryVariableName();
        String contentTypeName = ctxt.getTemporaryVariableName();
        String varReaderName = ctxt.getTemporaryVariableName();
        String servletContextName = ctxt.getTemporaryVariableName();
        String servletPathName = ctxt.getTemporaryVariableName();
        String requestDispatcherName = ctxt.getTemporaryVariableName();
        String irwName = ctxt.getTemporaryVariableName();           //ImportResponseWrapper name
        String brName = ctxt.getTemporaryVariableName();            //BufferedReader name
        String sbName = ctxt.getTemporaryVariableName();            //StringBuilder name
        String tempStringName = ctxt.getTemporaryVariableName();
        
        //is absolute url
        ctxt.generateJavaSource("boolean " + iauName + ";");
        
        //get the url value
        ctxt.generateJavaSource("String " + urlName + " = ");
        ctxt.generateAttribute("url");
        ctxt.generateJavaSource(";");
        
        //validate the url
        ctxt.generateJavaSource("if(" + urlName + " == null || " + urlName + ".equals(\"\")){");
        ctxt.generateJavaSource("    throw new JspTagException(\"The \\\"url\\\" attribute " +
        "illegally evaluated to \\\"null\\\" or \\\"\\\" in &lt;import&gt;\");");
        ctxt.generateJavaSource("}");
        
        //initialize the is_absolute_url
        ctxt.generateJavaSource(iauName + " = " +
                "org.apache.jasper.tagplugins.jstl.Util.isAbsoluteUrl(" + urlName + ");");
        
        //validate the context
        if(hasContext){
            
            ctxt.generateJavaSource("String " + contextName + " = ");
            ctxt.generateAttribute("context");
            ctxt.generateJavaSource(";");
            
            ctxt.generateJavaSource("if((!" + contextName + ".startsWith(\"/\")) " +
                    "|| (!" + urlName + ".startsWith(\"/\"))){");
            ctxt.generateJavaSource("    throw new JspTagException" +
                    "(\"In URL tags, when the \\\"context\\\" attribute is specified, " +
            "values of both \\\"context\\\" and \\\"url\\\" must start with \\\"/\\\".\");");
            ctxt.generateJavaSource("}");
            
        }
        
        //define charset
        ctxt.generateJavaSource("String " + charSetName + " = null;");
        
        //if the charEncoding attribute is specified
        if(hasCharEncoding){
            
            //initialize the charEncoding
            ctxt.generateJavaSource("String " + charEncodingName + " = ");
            ctxt.generateAttribute("charEncoding");
            ctxt.generateJavaSource(";");
            
            //assign appropriate value to the charset
            ctxt.generateJavaSource("if(null != " + charEncodingName + " " +
                    "&& !" + charEncodingName + ".equals(\"\")){");
            ctxt.generateJavaSource("    " + charSetName + " = " 
                    + charEncodingName + ";");
            ctxt.generateJavaSource("}");
        }
        
        //reshape the url string
        ctxt.generateJavaSource("if(!"+iauName+"){");
        ctxt.generateJavaSource("    if(!" + urlName + ".startsWith(\"/\")){");
        ctxt.generateJavaSource("        String " + servletPathName + " = " +
        "((HttpServletRequest)pageContext.getRequest()).getServletPath();");
        ctxt.generateJavaSource("        " + urlName + " = " 
                + servletPathName + ".substring(0," + servletPathName + ".lastIndexOf('/')) + '/' + " + urlName + ";");
        ctxt.generateJavaSource("    }");
        ctxt.generateJavaSource("}");
        
        //if the varReader attribute specified
        if(hasVarReader){
            
            //get the String value of varReader
            ctxt.generateJavaSource("String " + varReaderName + " = ");
            ctxt.generateAttribute("varReader");
            ctxt.generateJavaSource(";");
            
            //if the url is absolute url
            ctxt.generateJavaSource("if(" + iauName + "){");
            
            //get the content of the target
            ctxt.generateJavaSource("    java.net.URL " + urlObjName + " = new java.net.URL(" + urlName + ");");
            ctxt.generateJavaSource("    java.net.URLConnection " + ucName + " = " 
                    + urlObjName + ".openConnection();");
            ctxt.generateJavaSource("    java.io.InputStream " + inputStreamName + " = " 
                    + ucName + ".getInputStream();");
            
            ctxt.generateJavaSource("    if(" + charSetName + " == null){");
            ctxt.generateJavaSource("        String " + contentTypeName + " = " 
                    + ucName + ".getContentType();");
            ctxt.generateJavaSource("        if(null != " + contentTypeName + "){");
            ctxt.generateJavaSource("            " + charSetName + " = " +
                    "org.apache.jasper.tagplugins.jstl.Util.getContentTypeAttribute(" + contentTypeName + ", \"charset\");");
            ctxt.generateJavaSource("            if(" + charSetName + " == null) " 
                    + charSetName + " = org.apache.jasper.tagplugins.jstl.Util.DEFAULT_ENCODING;");
            ctxt.generateJavaSource("        }else{");
            ctxt.generateJavaSource("            " + charSetName + " = org.apache.jasper.tagplugins.jstl.Util.DEFAULT_ENCODING;");
            ctxt.generateJavaSource("        }");
            ctxt.generateJavaSource("    }");
            
            if(!hasCharEncoding){
                ctxt.generateJavaSource("    String " + contentTypeName + " = " + ucName + ".getContentType();");
            }
            
            //define the Reader
            ctxt.generateJavaSource("    java.io.Reader " + tempReaderName + " = null;");
            
            //initialize the Reader object
            ctxt.generateJavaSource("    try{");
            ctxt.generateJavaSource("        " + tempReaderName + " = new java.io.InputStreamReader(" + inputStreamName + ", " + charSetName + ");");
            ctxt.generateJavaSource("    }catch(Exception ex){");
            ctxt.generateJavaSource("        " + tempReaderName + " = new java.io.InputStreamReader(" + inputStreamName + ", org.apache.jasper.tagplugins.jstl.Util.DEFAULT_ENCODING);");
            ctxt.generateJavaSource("    }");
            
            //validate the response
            ctxt.generateJavaSource("    if(" + ucName + " instanceof java.net.HttpURLConnection){");
            ctxt.generateJavaSource("        int status = ((java.net.HttpURLConnection) " + ucName + ").getResponseCode();");
            ctxt.generateJavaSource("        if(status < 200 || status > 299){");
            ctxt.generateJavaSource("            throw new JspTagException(status + \" \" + " + urlName + ");");
            ctxt.generateJavaSource("        }");
            ctxt.generateJavaSource("    }");
            
            //set attribute in the page context scope
            ctxt.generateJavaSource("    pageContext.setAttribute(" + varReaderName + ", " + tempReaderName + ");");
            
            //if the url is relative
            ctxt.generateJavaSource("}else{");
            
            //if the url is relative, http request is needed
            ctxt.generateJavaSource("    if (!(pageContext.getRequest() instanceof HttpServletRequest  " +
            "&& pageContext.getResponse() instanceof HttpServletResponse)){");
            ctxt.generateJavaSource("        throw new JspTagException(\"Relative &lt;import&gt; from non-HTTP request not allowed\");");
            ctxt.generateJavaSource("    }");
            
            //get the servlet context of the context defined in the context attribute
            ctxt.generateJavaSource("    ServletContext " + servletContextName + " = null;");
            if(hasContext){
                ctxt.generateJavaSource("    if(null != " + contextName + "){");
                ctxt.generateJavaSource("        " + servletContextName + " = pageContext.getServletContext().getContext(" + contextName + ");" );
                ctxt.generateJavaSource("    }else{");
                ctxt.generateJavaSource("        " + servletContextName + " = pageContext.getServletContext();");
                ctxt.generateJavaSource("    }");
            }else{
                ctxt.generateJavaSource("    " + servletContextName + " = pageContext.getServletContext();");
            }
            
            //
            ctxt.generateJavaSource("    if(" + servletContextName + " == null){");
            if(hasContext){
                ctxt.generateJavaSource("        throw new JspTagException(\"Unable to get RequestDispatcher for Context: \\\" \"+" + contextName + "+\" \\\" and URL: \\\" \" +" + urlName + "+ \" \\\". Verify values and/or enable cross context access.\");");
            }else{
                ctxt.generateJavaSource("        throw new JspTagException(\"Unable to get RequestDispatcher for  URL: \\\" \" +" + urlName + "+ \" \\\". Verify values and/or enable cross context access.\");");
            }
            ctxt.generateJavaSource("    }");
            
            //get the request dispatcher
            ctxt.generateJavaSource("    RequestDispatcher " + requestDispatcherName + " = " + servletContextName + ".getRequestDispatcher(org.apache.jasper.tagplugins.jstl.Util.stripSession("+urlName+"));");
            ctxt.generateJavaSource("    if(" + requestDispatcherName + " == null) throw new JspTagException(org.apache.jasper.tagplugins.jstl.Util.stripSession("+urlName+"));");
            
            //initialize a ImportResponseWrapper to include the resource
            ctxt.generateJavaSource("    org.apache.jasper.tagplugins.jstl.Util.ImportResponseWrapper " + irwName + " = new org.apache.jasper.tagplugins.jstl.Util.ImportResponseWrapper((HttpServletResponse) pageContext.getResponse());");
            ctxt.generateJavaSource("    if(" + charSetName + " == null){");
            ctxt.generateJavaSource("        " + charSetName + " = org.apache.jasper.tagplugins.jstl.Util.DEFAULT_ENCODING;");
            ctxt.generateJavaSource("    }");
            ctxt.generateJavaSource("    " + irwName + ".setCharEncoding(" + charSetName + ");");
            ctxt.generateJavaSource("    try{");
            ctxt.generateJavaSource("        " + requestDispatcherName + ".include(pageContext.getRequest(), " + irwName + ");");
            ctxt.generateJavaSource("    }catch(java.io.IOException ex){");
            ctxt.generateJavaSource("        throw new JspException(ex);");
            ctxt.generateJavaSource("    }catch(RuntimeException ex){");
            ctxt.generateJavaSource("        throw new JspException(ex);");
            ctxt.generateJavaSource("    }catch(ServletException ex){");
            ctxt.generateJavaSource("        Throwable rc = ex.getRootCause();");
            ctxt.generateJavaSource("        if (rc == null)");
            ctxt.generateJavaSource("            throw new JspException(ex);");
            ctxt.generateJavaSource("        else");
            ctxt.generateJavaSource("            throw new JspException(rc);");
            ctxt.generateJavaSource("    }");
            
            //validate the response status
            ctxt.generateJavaSource("    if(" + irwName + ".getStatus() < 200 || " + irwName + ".getStatus() > 299){");
            ctxt.generateJavaSource("        throw new JspTagException(" + irwName + ".getStatus()+\" \" + org.apache.jasper.tagplugins.jstl.Util.stripSession(" + urlName + "));");
            ctxt.generateJavaSource("    }");
            
            //push in the page context
            ctxt.generateJavaSource("    java.io.Reader " + tempReaderName + " = new java.io.StringReader(" + irwName + ".getString());");
            ctxt.generateJavaSource("    pageContext.setAttribute(" + varReaderName + ", " + tempReaderName + ");");
            
            ctxt.generateJavaSource("}");
            
            //execute the body action
            ctxt.generateBody();
            
            //close the reader
            ctxt.generateJavaSource("java.io.Reader " + tempReaderName2 + " = (java.io.Reader)pageContext.getAttribute(" + varReaderName + ");");
            ctxt.generateJavaSource("if(" + tempReaderName2 + " != null) " + tempReaderName2 + ".close();");
            ctxt.generateJavaSource("pageContext.removeAttribute(" + varReaderName + ",1);");
        }
        
        //if the varReader is not specified 
        else{
            
            ctxt.generateJavaSource("pageContext.setAttribute(\"url_without_param\"," + urlName + ");");
            ctxt.generateBody();
            ctxt.generateJavaSource(urlName + " = (String)pageContext.getAttribute(\"url_without_param\");");
            ctxt.generateJavaSource("pageContext.removeAttribute(\"url_without_param\");");
            String strScope = "page";
            if(hasScope){
                strScope = ctxt.getConstantAttribute("scope");
            }
            int iScope = Util.getScope(strScope);
            
            ctxt.generateJavaSource("String " + tempStringName + " = null;");
            
            ctxt.generateJavaSource("if(" + iauName + "){");
            
            //get the content of the target
            ctxt.generateJavaSource("    java.net.URL " + urlObjName + " = new java.net.URL(" + urlName + ");");
            ctxt.generateJavaSource("    java.net.URLConnection " + ucName + " = " + urlObjName + ".openConnection();");
            ctxt.generateJavaSource("    java.io.InputStream " + inputStreamName + " = " + ucName + ".getInputStream();");
            ctxt.generateJavaSource("    java.io.Reader " + tempReaderName + " = null;");
            
            ctxt.generateJavaSource("    if(" + charSetName + " == null){");
            ctxt.generateJavaSource("        String " + contentTypeName + " = " 
                    + ucName + ".getContentType();");
            ctxt.generateJavaSource("        if(null != " + contentTypeName + "){");
            ctxt.generateJavaSource("            " + charSetName + " = " +
                    "org.apache.jasper.tagplugins.jstl.Util.getContentTypeAttribute(" + contentTypeName + ", \"charset\");");
            ctxt.generateJavaSource("            if(" + charSetName + " == null) " 
                    + charSetName + " = org.apache.jasper.tagplugins.jstl.Util.DEFAULT_ENCODING;");
            ctxt.generateJavaSource("        }else{");
            ctxt.generateJavaSource("            " + charSetName + " = org.apache.jasper.tagplugins.jstl.Util.DEFAULT_ENCODING;");
            ctxt.generateJavaSource("        }");
            ctxt.generateJavaSource("    }");
            
            ctxt.generateJavaSource("    try{");
            ctxt.generateJavaSource("        " + tempReaderName + " = new java.io.InputStreamReader(" + inputStreamName + "," + charSetName + ");");
            ctxt.generateJavaSource("    }catch(Exception ex){");
            //ctxt.generateJavaSource("        throw new JspTagException(ex.toString());");
            ctxt.generateJavaSource("        " + tempReaderName + " = new java.io.InputStreamReader(" + inputStreamName + ",org.apache.jasper.tagplugins.jstl.Util.DEFAULT_ENCODING);");
            ctxt.generateJavaSource("    }");
            
            //validate the response
            ctxt.generateJavaSource("    if(" + ucName + " instanceof java.net.HttpURLConnection){");
            ctxt.generateJavaSource("        int status = ((java.net.HttpURLConnection) " + ucName + ").getResponseCode();");
            ctxt.generateJavaSource("        if(status < 200 || status > 299){");
            ctxt.generateJavaSource("            throw new JspTagException(status + \" \" + " + urlName + ");");
            ctxt.generateJavaSource("        }");
            ctxt.generateJavaSource("    }");
            
            ctxt.generateJavaSource("    java.io.BufferedReader " + brName + " =  new java.io.BufferedReader(" + tempReaderName + ");");
            ctxt.generateJavaSource("    StringBuilder " + sbName + " = new StringBuilder();");
            String index = ctxt.getTemporaryVariableName();
            ctxt.generateJavaSource("    int " + index + ";");
            ctxt.generateJavaSource("    while(("+index+" = "+brName+".read()) != -1) "+sbName+".append((char)"+index+");");
            ctxt.generateJavaSource("    " + tempStringName + " = " +sbName + ".toString();");
            
            ctxt.generateJavaSource("}else{");
            
            //if the url is relative, http request is needed.
            ctxt.generateJavaSource("    if (!(pageContext.getRequest() instanceof HttpServletRequest  " +
            "&& pageContext.getResponse() instanceof HttpServletResponse)){");
            ctxt.generateJavaSource("        throw new JspTagException(\"Relative &lt;import&gt; from non-HTTP request not allowed\");");
            ctxt.generateJavaSource("    }");
            
            //get the servlet context of the context defined in the context attribute
            ctxt.generateJavaSource("    ServletContext " + servletContextName + " = null;");
            if(hasContext){
                ctxt.generateJavaSource("    if(null != " + contextName + "){");
                ctxt.generateJavaSource("        " + servletContextName + " = pageContext.getServletContext().getContext(" + contextName + ");" );
                ctxt.generateJavaSource("    }else{");
                ctxt.generateJavaSource("        " + servletContextName + " = pageContext.getServletContext();");
                ctxt.generateJavaSource("    }");
            }else{
                ctxt.generateJavaSource("    " + servletContextName + " = pageContext.getServletContext();");
            }
            
            //
            ctxt.generateJavaSource("    if(" + servletContextName + " == null){");
            if(hasContext){
                ctxt.generateJavaSource("        throw new JspTagException(\"Unable to get RequestDispatcher for Context: \\\" \" +" + contextName + "+ \" \\\" and URL: \\\" \" +" + urlName + "+ \" \\\". Verify values and/or enable cross context access.\");");
            }else{
                ctxt.generateJavaSource("        throw new JspTagException(\"Unable to get RequestDispatcher for URL: \\\" \" +" + urlName + "+ \" \\\". Verify values and/or enable cross context access.\");");
            }
            ctxt.generateJavaSource("    }");
            
            //get the request dispatcher
            ctxt.generateJavaSource("    RequestDispatcher " + requestDispatcherName + " = " + servletContextName + ".getRequestDispatcher(org.apache.jasper.tagplugins.jstl.Util.stripSession("+urlName+"));");
            ctxt.generateJavaSource("    if(" + requestDispatcherName + " == null) throw new JspTagException(org.apache.jasper.tagplugins.jstl.Util.stripSession("+urlName+"));");
            
            //initialize a ImportResponseWrapper to include the resource
            ctxt.generateJavaSource("    org.apache.jasper.tagplugins.jstl.Util.ImportResponseWrapper " + irwName + " = new org.apache.jasper.tagplugins.jstl.Util.ImportResponseWrapper((HttpServletResponse) pageContext.getResponse());");
            ctxt.generateJavaSource("    if(" + charSetName + " == null){");
            ctxt.generateJavaSource("        " + charSetName + " = org.apache.jasper.tagplugins.jstl.Util.DEFAULT_ENCODING;");
            ctxt.generateJavaSource("    }");
            ctxt.generateJavaSource("    " + irwName + ".setCharEncoding(" + charSetName + ");");
            ctxt.generateJavaSource("    try{");
            ctxt.generateJavaSource("        " + requestDispatcherName + ".include(pageContext.getRequest(), " + irwName + ");");
            ctxt.generateJavaSource("    }catch(java.io.IOException ex){");
            ctxt.generateJavaSource("        throw new JspException(ex);");
            ctxt.generateJavaSource("    }catch(RuntimeException ex){");
            ctxt.generateJavaSource("        throw new JspException(ex);");
            ctxt.generateJavaSource("    }catch(ServletException ex){");
            ctxt.generateJavaSource("        Throwable rc = ex.getRootCause();");
            ctxt.generateJavaSource("        if (rc == null)");
            ctxt.generateJavaSource("            throw new JspException(ex);");
            ctxt.generateJavaSource("        else");
            ctxt.generateJavaSource("            throw new JspException(rc);");
            ctxt.generateJavaSource("    }");
            
            //validate the response status
            ctxt.generateJavaSource("    if(" + irwName + ".getStatus() < 200 || " + irwName + ".getStatus() > 299){");
            ctxt.generateJavaSource("        throw new JspTagException(" + irwName + ".getStatus()+\" \" + org.apache.jasper.tagplugins.jstl.Util.stripSession(" + urlName + "));");
            ctxt.generateJavaSource("    }");
            
            ctxt.generateJavaSource("    " + tempStringName + " = " + irwName + ".getString();");
            
            ctxt.generateJavaSource("}");
            
            if(hasVar){
                String strVar = ctxt.getConstantAttribute("var");
                ctxt.generateJavaSource("pageContext.setAttribute(\""+strVar+"\"," + tempStringName + "," + iScope + ");");
            }else{
                ctxt.generateJavaSource("pageContext.getOut().print(" + tempStringName + ");");
            }
        }
    }


}
