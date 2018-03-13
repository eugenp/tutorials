/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package examples;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public abstract class ExampleTagBase extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    @Override
    public void setParent(Tag parent) {
        this.parent = parent;
    }

    @Override
    public void setBodyContent(BodyContent bodyOut) {
        this.bodyOut = bodyOut;
    }

    @Override
    public Tag getParent() {
        return this.parent;
    }

    @Override
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }


    @Override
    public void doInitBody() throws JspException {
        // Default implementations for BodyTag methods as well
        // just in case a tag decides to implement BodyTag.
    }

    @Override
    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public void release() {
        bodyOut = null;
        pageContext = null;
        parent = null;
    }

    protected BodyContent bodyOut;
    protected Tag parent;
}
