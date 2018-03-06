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
package org.apache.catalina.startup;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

/**
 * Simple Realm that uses a configurable {@link Map} to link user names and
 * passwords.
 */
public final class TesterMapRealm extends RealmBase {
    private Map<String,String> users = new HashMap<String,String>();
    private Map<String,List<String>> roles = new HashMap<String,List<String>>();

    public void addUser(String username, String password) {
        users.put(username, password);
    }

    public void addUserRole(String username, String role) {
        List<String> userRoles = roles.get(username);
        if (userRoles == null) {
            userRoles = new ArrayList<String>();
            roles.put(username, userRoles);
        }
        userRoles.add(role);
    }

    @Override
    protected String getName() {
        return "MapRealm";
    }

    @Override
    protected String getPassword(String username) {
        return users.get(username);
    }

    @Override
    protected Principal getPrincipal(String username) {
        return new GenericPrincipal(username, getPassword(username),
                roles.get(username));
    }

}