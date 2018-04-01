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
package org.apache.catalina.ha.session;

import org.apache.catalina.ha.ClusterMessageBase;

/**
 * Session id change cluster message
 *
 * @author Peter Rossbach
 *
 * @deprecated Will be removed in Tomcat 8.0.x
 */
@Deprecated
public class SessionIDMessage extends ClusterMessageBase {

    private static final long serialVersionUID = 1L;

    private int messageNumber;

    private String orignalSessionID;

    private String backupSessionID;

    private String host ;
    private String contextName;

    @Override
    public String getUniqueId() {
        StringBuilder result = new StringBuilder(getOrignalSessionID());
        result.append("#-#");
        result.append(getHost());
                result.append("#-#");
                result.append(getContextName());
        result.append("#-#");
        result.append(getMessageNumber());
        result.append("#-#");
        result.append(System.currentTimeMillis());
        return result.toString();
    }

    /**
     * @return Returns the host.
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host The host to set.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return Returns the context name.
     */
    public String getContextName() {
        return contextName;
    }
    /**
     * @param contextName The context name to set.
     */
    public void setContextName(String contextName) {
        this.contextName = contextName;
    }
    /**
     * @return Returns the messageNumber.
     */
    public int getMessageNumber() {
        return messageNumber;
    }

    /**
     * @param messageNumber
     *            The messageNumber to set.
     */
    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }


    /**
     * @return Returns the backupSessionID.
     */
    public String getBackupSessionID() {
        return backupSessionID;
    }

    /**
     * @param backupSessionID
     *            The backupSessionID to set.
     */
    public void setBackupSessionID(String backupSessionID) {
        this.backupSessionID = backupSessionID;
    }

    /**
     * @return Returns the orignalSessionID.
     */
    public String getOrignalSessionID() {
        return orignalSessionID;
    }

    /**
     * @param orignalSessionID
     *            The orignalSessionID to set.
     */
    public void setOrignalSessionID(String orignalSessionID) {
        this.orignalSessionID = orignalSessionID;
    }

    @Override
    public String toString() {
        return "SESSIONID-UPDATE#" + getHost() + "." + getContextName() + "#" + getOrignalSessionID() + ":" + getBackupSessionID();
    }

}

