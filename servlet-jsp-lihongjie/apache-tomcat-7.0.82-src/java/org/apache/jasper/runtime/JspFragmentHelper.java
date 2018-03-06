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

package org.apache.jasper.runtime;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;

/**
 * Helper class from which all Jsp Fragment helper classes extend.
 * This class allows for the emulation of numerous fragments within
 * a single class, which in turn reduces the load on the class loader
 * since there are potentially many JspFragments in a single page.
 * <p>
 * The class also provides various utility methods for JspFragment
 * implementations.
 *
 * @author Mark Roth
 */
public abstract class JspFragmentHelper 
    extends JspFragment 
{
    
    protected int discriminator;
    protected JspContext jspContext;
    protected PageContext _jspx_page_context;
    protected JspTag parentTag;

    public JspFragmentHelper( int discriminator, JspContext jspContext, 
        JspTag parentTag ) 
    {
        this.discriminator = discriminator;
        this.jspContext = jspContext;
        this._jspx_page_context = null;
        if( jspContext instanceof PageContext ) {
            _jspx_page_context = (PageContext)jspContext;
        }
        this.parentTag = parentTag;
    }
    
    @Override
    public JspContext getJspContext() {
        return this.jspContext;
    }
    
    public JspTag getParentTag() {
        return this.parentTag;
    }
    
}
