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
package javax.servlet;

/**
 * Defines a general exception a servlet can throw when it encounters
 * difficulty.
 * 
 * @author Various
 */
public class ServletException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new servlet exception.
     */
    public ServletException() {
        super();
    }

    /**
     * Constructs a new servlet exception with the specified message. The
     * message can be written to the server log and/or displayed for the user.
     * 
     * @param message
     *            a <code>String</code> specifying the text of the exception
     *            message
     */
    public ServletException(String message) {
        super(message);
    }

    /**
     * Constructs a new servlet exception when the servlet needs to throw an
     * exception and include a message about the "root cause" exception that
     * interfered with its normal operation, including a description message.
     * 
     * @param message
     *            a <code>String</code> containing the text of the exception
     *            message
     * @param rootCause
     *            the <code>Throwable</code> exception that interfered with the
     *            servlet's normal operation, making this servlet exception
     *            necessary
     */
    public ServletException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    /**
     * Constructs a new servlet exception when the servlet needs to throw an
     * exception and include a message about the "root cause" exception that
     * interfered with its normal operation. The exception's message is based on
     * the localized message of the underlying exception.
     * <p>
     * This method calls the <code>getLocalizedMessage</code> method on the
     * <code>Throwable</code> exception to get a localized exception message.
     * When subclassing <code>ServletException</code>, this method can be
     * overridden to create an exception message designed for a specific locale.
     * 
     * @param rootCause
     *            the <code>Throwable</code> exception that interfered with the
     *            servlet's normal operation, making the servlet exception
     *            necessary
     */
    public ServletException(Throwable rootCause) {
        super(rootCause);
    }

    /**
     * Returns the exception that caused this servlet exception.
     * 
     * @return the <code>Throwable</code> that caused this servlet exception
     */
    public Throwable getRootCause() {
        return getCause();
    }
}
