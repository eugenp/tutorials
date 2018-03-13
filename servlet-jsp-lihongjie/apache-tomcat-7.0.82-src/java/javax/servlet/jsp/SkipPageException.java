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
package javax.servlet.jsp;

/**
 * Exception to indicate the calling page must cease evaluation. Thrown by a
 * simple tag handler to indicate that the remainder of the page must not be
 * evaluated. The result is propagated back to the page in the case where one
 * tag invokes another (as can be the case with tag files). The effect is
 * similar to that of a Classic Tag Handler returning Tag.SKIP_PAGE from
 * doEndTag(). Jsp Fragments may also throw this exception. This exception
 * should not be thrown manually in a JSP page or tag file - the behavior is
 * undefined. The exception is intended to be thrown inside SimpleTag handlers
 * and in JSP fragments.
 * 
 * @see javax.servlet.jsp.tagext.SimpleTag#doTag
 * @see javax.servlet.jsp.tagext.JspFragment#invoke
 * @see javax.servlet.jsp.tagext.Tag#doEndTag
 * @since 2.0
 */
public class SkipPageException extends JspException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a SkipPageException with no message.
     */
    public SkipPageException() {
        super();
    }

    /**
     * Creates a SkipPageException with the provided message.
     * 
     * @param message
     *            the detail message
     */
    public SkipPageException(String message) {
        super(message);
    }

    /**
     * Creates a SkipPageException with the provided message and root cause.
     * 
     * @param message
     *            the detail message
     * @param rootCause
     *            the originating cause of this exception
     */
    public SkipPageException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Creates a SkipPageException with the provided root cause.
     * 
     * @param rootCause
     *            the originating cause of this exception
     */
    public SkipPageException(Throwable rootCause) {
        super(rootCause);
    }
}
