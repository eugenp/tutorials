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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Accept and display a value.
 */
public class ValuesTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    // Using "-1" as the default value,
    // in the assumption that it won't be used as the value.
    // Cannot use null here, because null is an important case
    // that should be present in the tests.
    private Object objectValue = "-1";
    private String stringValue = "-1";
    private long longValue = -1;
    private double doubleValue = -1;

    public void setObject(Object objectValue) {
        this.objectValue = objectValue;
    }

    public void setString(String stringValue) {
        this.stringValue = stringValue;
    }

    public void setLong(long longValue) {
        this.longValue = longValue;
    }

    public void setDouble(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            if (!"-1".equals(objectValue)) {
                out.print(objectValue);
            } else if (!"-1".equals(stringValue)) {
                out.print(stringValue);
            } else if (longValue != -1) {
                out.print(longValue);
            } else if (doubleValue != -1) {
                out.print(doubleValue);
            } else {
                out.print("-1");
            }
        } catch (IOException ex) {
            throw new JspTagException("IOException: " + ex.toString(), ex);
        }
        return super.doEndTag();
    }
}
