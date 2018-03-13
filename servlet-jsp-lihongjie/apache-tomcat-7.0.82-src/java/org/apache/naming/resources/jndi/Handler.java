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
package org.apache.naming.resources.jndi;

import org.apache.naming.resources.DirContextURLStreamHandler;

/**
 * Stream handler to a JNDI directory context. For use when
 * embedding Tomcat and the embedding application has already set its own
 * {@link java.net.URLStreamHandlerFactory} and the Tomcat jndi handler needs to
 * be registered via the java.protocol.handler.pkgs system property.
 */
public class Handler extends DirContextURLStreamHandler {
    
    public Handler() {
        // NOOP
    }
}
