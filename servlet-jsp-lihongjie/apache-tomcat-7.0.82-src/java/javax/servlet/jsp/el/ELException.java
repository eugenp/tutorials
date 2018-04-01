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

package javax.servlet.jsp.el;

/**
 * Represents any of the exception conditions that arise during the operation
 * evaluation of the evaluator.
 * 
 * @since 2.0
 * @deprecated As of JSP 2.1, replaced by javax.el.ELException
 */
@SuppressWarnings("dep-ann") // TCK signature test fails with annotation
public class ELException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an ELException with no detail message.
     **/
    public ELException() {
        super();
    }

    /**
     * Creates an ELException with the provided detail message.
     * 
     * @param pMessage
     *            the detail message
     **/
    public ELException(String pMessage) {
        super(pMessage);
    }

    /**
     * Creates an ELException with the given root cause.
     * 
     * @param pRootCause
     *            the originating cause of this exception
     **/
    public ELException(Throwable pRootCause) {
        super(pRootCause);
    }

    // -------------------------------------
    /**
     * Creates an ELException with the given detail message and root cause.
     * 
     * @param pMessage
     *            the detail message
     * @param pRootCause
     *            the originating cause of this exception
     **/
    public ELException(String pMessage, Throwable pRootCause) {
        super(pMessage, pRootCause);
    }

    // -------------------------------------
    /**
     * Returns the root cause.
     * 
     * @return the root cause of this exception
     */
    public Throwable getRootCause() {
        return getCause();
    }
}
