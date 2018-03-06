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
package org.apache.catalina.deploy;

/**
 * Enables additional attributes other than just the name to be recorded for
 * an application listener.
 */
public class ApplicationListener {

    private final String className;
    private final boolean pluggabilityBlocked;

    public ApplicationListener(String className,
            boolean pluggabilityBlocked) {
        this.className = className;
        this.pluggabilityBlocked = pluggabilityBlocked;
    }


    public String getClassName() {
        return className;
    }


    public boolean isPluggabilityBlocked() {
        return pluggabilityBlocked;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((className == null) ? 0 : className.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ApplicationListener)) {
            return false;
        }
        ApplicationListener other = (ApplicationListener) obj;
        if (className == null) {
            if (other.className != null) {
                return false;
            }
        } else if (!className.equals(other.className)) {
            return false;
        }
        return true;
    }
}