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
package org.apache.tomcat.jni;

public class LibraryNotFoundError extends UnsatisfiedLinkError {

    private static final long serialVersionUID = 1L;

    private final String libraryNames;

    /**
     *
     * @param libraryNames A list of the file names of the native libraries that
     *                     failed to load
     * @param errors A list of the error messages received when trying to load
     *               each of the libraries
     */
    public LibraryNotFoundError(String libraryNames, String errors){
        super(errors);
        this.libraryNames = libraryNames;
    }

    public String getLibraryNames(){
        return libraryNames;
    }
}
