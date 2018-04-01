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
package org.apache.catalina.tribes;

import org.apache.catalina.tribes.group.interceptors.DomainFilterInterceptor;
import org.apache.catalina.tribes.util.UUIDGenerator;

/**
 * Utility methods for use by multiple tests.
 */
public class TesterUtil {

    private TesterUtil() {
        // Hide default constructor
    }


    /**
     * Configures a set of channels to use a random domain. Use to ensure that
     * multiple instance of the test suite do not interfere when running on the
     * same machine. This may happen in a CI system or when a developer is
     * running tests for multiple branches in parallel.
     */
    public static void addRandomDomain(ManagedChannel[] channels) {
        if (channels == null) {
            return;
        }

        byte[] domain = UUIDGenerator.randomUUID(false);

        for (ManagedChannel channel : channels) {
            channel.getMembershipService().setDomain(domain);
            DomainFilterInterceptor filter = new DomainFilterInterceptor();
            filter.setDomain(domain);
            channel.addInterceptor(filter);
        }
    }
}
