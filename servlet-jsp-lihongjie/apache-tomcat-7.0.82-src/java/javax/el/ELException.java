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

package javax.el;

/**
 * Represents any of the exception conditions that can arise during expression
 * evaluation.
 * 
 * @since 2.1
 */
public class ELException extends RuntimeException {

    private static final long serialVersionUID = -6228042809457459161L;

    /**
     * Creates an ELException with no detail message
     */
    public ELException() {
        super();
    }

    /**
     * Creates an ELException with the provided detail message.
     * 
     * @param message
     *            the detail message
     */
    public ELException(String message) {
        super(message);
    }

    /**
     * Creates an ELException with the given detail message and root cause.
     * 
     * @param message
     *            the detail message
     * @param cause
     *            the originating cause of this exception
     */
    public ELException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an ELException with the given cause
     * 
     * @param cause
     *            the originating cause of this exception
     */
    public ELException(Throwable cause) {
        super(cause);
    }
}
