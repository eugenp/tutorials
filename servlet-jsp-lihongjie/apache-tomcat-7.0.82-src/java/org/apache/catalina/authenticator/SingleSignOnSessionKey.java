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
package org.apache.catalina.authenticator;

import java.io.Serializable;

import org.apache.catalina.Context;
import org.apache.catalina.Session;

/**
 * Key used by SSO to identify a session. This key is used rather than the
 * actual session to facilitate the replication of the SSO information
 * across a cluster where replicating the entire session would generate
 * significant, unnecessary overhead.
 *
 */
public class SingleSignOnSessionKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String sessionId;
    private final String contextName;
    private final String hostName;

    public SingleSignOnSessionKey(Session session) {
        this.sessionId = session.getId();
        Context context = (Context) session.getManager().getContainer();
        this.contextName = context.getName();
        this.hostName = context.getParent().getName();
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getContextName() {
        return contextName;
    }

    public String getHostName() {
        return hostName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                ((sessionId == null) ? 0 : sessionId.hashCode());
        result = prime * result +
                ((contextName == null) ? 0 : contextName.hashCode());
        result = prime * result +
                ((hostName == null) ? 0 : hostName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SingleSignOnSessionKey other = (SingleSignOnSessionKey) obj;
        if (sessionId == null) {
            if (other.sessionId != null) {
                return false;
            }
        } else if (!sessionId.equals(other.sessionId)) {
            return false;
        }
        if (contextName == null) {
            if (other.contextName != null) {
                return false;
            }
        } else if (!contextName.equals(other.contextName)) {
            return false;
        }
        if (hostName == null) {
            if (other.hostName != null) {
                return false;
            }
        } else if (!hostName.equals(other.hostName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        // Session ID is 32. Standard text is 36. Host could easily be 20+.
        // Context could be anything from 0 upwards. 128 seems like a reasonable
        // size to accommodate most cases without being too big.
        StringBuilder sb = new StringBuilder(128);
        sb.append("Host: [");
        sb.append(hostName);
        sb.append("], Context: [");
        sb.append(contextName);
        sb.append("], SessionID: [");
        sb.append(sessionId);
        sb.append("]");
        return sb.toString();
    }
}
