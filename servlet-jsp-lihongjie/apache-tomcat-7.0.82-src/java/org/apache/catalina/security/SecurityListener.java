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
package org.apache.catalina.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;

public class SecurityListener implements LifecycleListener {

    private static final Log log = LogFactory.getLog(SecurityListener.class);

    private static final StringManager sm =
        StringManager.getManager(Constants.PACKAGE);

    private static final String UMASK_PROPERTY_NAME =
        Constants.PACKAGE + ".SecurityListener.UMASK";

    private static final String UMASK_FORMAT = "%04o";

    /**
     * The list of operating system users not permitted to run Tomcat.
     */
    private Set<String> checkedOsUsers = new HashSet<String>();

    /**
     * The minimum umask that must be configured for the operating system user
     * running Tomcat. The umask is handled as an octal.
     */
    private Integer minimumUmask = Integer.valueOf(7);


    public SecurityListener() {
        checkedOsUsers.add("root");
    }


    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        // This is the earliest event in Lifecycle
        if (event.getType().equals(Lifecycle.BEFORE_INIT_EVENT)) {
            doChecks();
        }
    }


    /**
     * Set the list of operating system users not permitted to run Tomcat. By
     * default, only root is prevented from running Tomcat. Calling this method
     * with null or the empty string will clear the list of users and
     * effectively disables this check. User names will always be checked in a
     * case insensitive manner using the system default Locale.
     *
     * @param userNameList  A comma separated list of operating system users not
     *                      permitted to run Tomcat
     */
    public void setCheckedOsUsers(String userNameList) {
        if (userNameList == null || userNameList.length() == 0) {
            checkedOsUsers.clear();
        } else {
            String[] userNames = userNameList.split(",");
            for (String userName : userNames) {
                if (userName.length() > 0) {
                    checkedOsUsers.add(userName.toLowerCase(Locale.getDefault()));
                }
            }
        }
    }


    /**
     * Returns the current list of operating system users not permitted to run
     * Tomcat.
     *
     * @return  A comma separated list of operating system user names.
     */
    public String getCheckedOsUsers() {
        if (checkedOsUsers.size() == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        Iterator<String> iter = checkedOsUsers.iterator();
        result.append(iter.next());
        while (iter.hasNext()) {
            result.append(',');
            result.append(iter.next());
        }
        return result.toString();
    }


    /**
     * Set the minimum umask that must be configured before Tomcat will start.
     *
     * @param umask The 4-digit umask as returned by the OS command <i>umask</i>
     */
    public void setMinimumUmask(String umask) {
        if (umask == null || umask.length() == 0) {
            minimumUmask = Integer.valueOf(0);
        } else {
            minimumUmask = Integer.valueOf(umask, 8);
        }
    }


    /**
     * Get the minimum umask that must be configured before Tomcat will start.
     *
     * @return  The 4-digit umask as used by the OS command <i>umask</i>
     */
    public String getMinimumUmask() {
        return String.format(UMASK_FORMAT, minimumUmask);
    }


    /**
     * Execute the security checks. Each check should be in a separate method.
     */
    protected void doChecks() {
        checkOsUser();
        checkUmask();
    }


    protected void checkOsUser() {
        String userName = System.getProperty("user.name");
        if (userName != null) {
            String userNameLC = userName.toLowerCase(Locale.getDefault());

            if (checkedOsUsers.contains(userNameLC)) {
                // Have to throw Error to force start process to be aborted
                throw new Error(sm.getString(
                        "SecurityListener.checkUserWarning", userName));
            }
        }
    }


    protected void checkUmask() {
        String prop = System.getProperty(UMASK_PROPERTY_NAME);
        Integer umask = null;
        if (prop != null) {
            try {
                 umask = Integer.valueOf(prop, 8);
            } catch (NumberFormatException nfe) {
                log.warn(sm.getString("SecurityListener.checkUmaskParseFail",
                        prop));
            }
        }
        if (umask == null) {
            if (Constants.CRLF.equals(Constants.LINE_SEP)) {
                // Probably running on Windows so no umask
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("SecurityListener.checkUmaskSkip"));
                }
                return;
            } else {
                if (minimumUmask.intValue() > 0) {
                    log.warn(sm.getString(
                            "SecurityListener.checkUmaskNone",
                            UMASK_PROPERTY_NAME, getMinimumUmask()));
                }
                return;
            }
        }

        if ((umask.intValue() & minimumUmask.intValue()) !=
                minimumUmask.intValue()) {
            throw new Error(sm.getString("SecurityListener.checkUmaskFail",
                    String.format(UMASK_FORMAT, umask), getMinimumUmask()));
        }
    }
}
