/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.tomcat.jni;

/** Sockaddr
 *
 * @author Mladen Turk
 */
public class Sockaddr {

   /** The pool to use... */
    public long pool;
    /** The hostname */
    public String hostname;
    /** Either a string of the port number or the service name for the port */
    public String servname;
    /** The numeric port */
    public int port;
    /** The family */
    public int family;
    /** If multiple addresses were found by apr_sockaddr_info_get(), this
     *  points to a representation of the next address. */
    public long next;

}
