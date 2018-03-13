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

import org.apache.catalina.Authenticator;
import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;

public class SingleSignOnListener implements SessionListener, Serializable {

    private static final long serialVersionUID = 1L;

    private final String ssoId;

    public SingleSignOnListener(String ssoId) {
        this.ssoId = ssoId;
    }


    @Override
    public void sessionEvent(SessionEvent event) {
        if (!Session.SESSION_DESTROYED_EVENT.equals(event.getType())) {
            return;
        }

        Session session = event.getSession();
        Manager manager = session.getManager();
        if (manager == null) {
            return;
        }
        Context context = (Context) manager.getContainer();
        Authenticator authenticator = context.getAuthenticator();
        if (!(authenticator instanceof AuthenticatorBase)) {
            return;
        }
        SingleSignOn sso = ((AuthenticatorBase) authenticator).sso;
        if (sso == null) {
            return;
        }
        sso.sessionDestroyed(ssoId, session);
    }
}
