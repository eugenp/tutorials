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
package org.apache.tomcat.util.security;

import java.security.Permission;

/**
 * This interface is implemented by components to enable privileged code to
 * check whether the component has a given permission.
 * This is typically used when a privileged component (e.g. the container) is
 * performing an action on behalf of an untrusted component (e.g. a web
 * application) without the current thread having passed through a code source
 * provided by the untrusted component. Because the current thread has not
 * passed through a code source provided by the untrusted component the
 * SecurityManager assumes the code is trusted so the standard checking
 * mechanisms can't be used.
 */
public interface PermissionCheck {

    /**
     * Does this component have the given permission?
     *
     * @param permission The permission to test
     *
     * @return {@code false} if a SecurityManager is enabled and the component
     *         does not have the given permission, otherwise {@code true}
     */
    boolean check(Permission permission);
}
