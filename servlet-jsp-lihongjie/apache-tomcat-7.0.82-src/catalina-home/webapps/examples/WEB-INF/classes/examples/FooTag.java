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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * Example1: the simplest tag
 * Collect attributes and call into some actions
 *
 * <foo att1="..." att2="...." att3="...." />
 */

public class FooTag extends ExampleTagBase {

    private static final long serialVersionUID = 1L;

    private String atts[] = new String[3];
    int i = 0;

    private final void setAtt(int index, String value) {
        atts[index] = value;
    }

    public void setAtt1(String value) {
        setAtt(0, value);
    }

    public void setAtt2(String value) {
        setAtt(1, value);
    }

    public void setAtt3(String value) {
        setAtt(2, value);
    }

    /**
     * Process start tag
     *
     * @return EVAL_BODY_INCLUDE
     */
    @Override
    public int doStartTag() throws JspException {
        i = 0;
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public void doInitBody() throws JspException {
        pageContext.setAttribute("member", atts[i]);
        i++;
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            if (i == 3) {
                bodyOut.writeOut(bodyOut.getEnclosingWriter());
                return SKIP_BODY;
            }

            pageContext.setAttribute("member", atts[i]);
            i++;
            return EVAL_BODY_BUFFERED;
        } catch (IOException ex) {
            throw new JspTagException(ex.toString());
        }
    }
}

