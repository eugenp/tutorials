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
package org.apache.catalina.realm;

import java.security.cert.X509Certificate;

/**
 * Provides an interface for retrieving a user name from an X509Certificate.
 */
public interface X509UsernameRetriever {
    /**
     * Gets a user name from an X509Certificate.
     *
     * @param cert The certificate containing the user name.
     * @return An appropriate user name obtained from one or more fields
     *         in the certificate.
     */
    public String getUsername(X509Certificate cert);
}
