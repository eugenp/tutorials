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

import java.security.Principal;

/**
 * Minimal Realm implementation that always returns null when an attempt is made
 * to validate a user name and password. It is intended to be used as a default
 * Realm implementation when no other Realm is specified.
 */
public class NullRealm extends RealmBase {

    private static final String NAME = "NullRealm";

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    protected String getPassword(String username) {
        // Always return null
        return null;
    }

    @Override
    protected Principal getPrincipal(String username) {
        // Always return null
        return null;
    }
}
