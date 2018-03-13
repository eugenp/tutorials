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
package org.apache.catalina.connector;

import java.io.Serializable;
import java.security.Principal;

/**
 * Generic implementation of <strong>java.security.Principal</strong> that
 * is used to represent principals authenticated at the protocol handler level.
 *
 * @author Remy Maucherat
 */
public class CoyotePrincipal implements Principal, Serializable {

    private static final long serialVersionUID = 1L;


    // ----------------------------------------------------------- Constructors

    public CoyotePrincipal(String name) {

        this.name = name;

    }


    // ------------------------------------------------------------- Properties


    /**
     * The username of the user represented by this Principal.
     */
    protected String name = null;

    @Override
    public String getName() {
        return (this.name);
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return a String representation of this object, which exposes only
     * information that should be public.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("CoyotePrincipal[");
        sb.append(this.name);
        sb.append("]");
        return (sb.toString());

    }


}
