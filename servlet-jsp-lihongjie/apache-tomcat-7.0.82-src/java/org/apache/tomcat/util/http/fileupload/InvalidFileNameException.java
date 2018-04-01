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
package org.apache.tomcat.util.http.fileupload;

/**
 * This exception is thrown in case of an invalid file name.
 * A file name is invalid, if it contains a NUL character.
 * Attackers might use this to circumvent security checks:
 * For example, a malicious user might upload a file with the name
 * "foo.exe\0.png". This file name might pass security checks (i.e.
 * checks for the extension ".png"), while, depending on the underlying
 * C library, it might create a file named "foo.exe", as the NUL
 * character is the string terminator in C.
 */
public class InvalidFileNameException extends RuntimeException {

    /**
     * Serial version UID, being used, if the exception
     * is serialized.
     */
    private static final long serialVersionUID = 7922042602454350470L;

    /**
     * The file name causing the exception.
     */
    private final String name;

    /**
     * Creates a new instance.
     *
     * @param pName The file name causing the exception.
     * @param pMessage A human readable error message.
     */
    public InvalidFileNameException(String pName, String pMessage) {
        super(pMessage);
        name = pName;
    }

    /**
     * Returns the invalid file name.
     *
     * @return the invalid file name.
     */
    public String getName() {
        return name;
    }

}
