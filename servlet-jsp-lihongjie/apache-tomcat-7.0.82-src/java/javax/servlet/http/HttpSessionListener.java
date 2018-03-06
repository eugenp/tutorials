/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.servlet.http;

import java.util.EventListener;

/**
 * Implementations of this interface are notified of changes to the list of
 * active sessions in a web application. To receive notification events, the
 * implementation class must be configured in the deployment descriptor for the
 * web application.
 * 
 * @see HttpSessionEvent
 * @since v 2.3
 */
public interface HttpSessionListener extends EventListener {

    /**
     * Notification that a session was created.
     * 
     * @param se
     *            the notification event
     */
    public void sessionCreated(HttpSessionEvent se);

    /**
     * Notification that a session is about to be invalidated.
     * 
     * @param se
     *            the notification event
     */
    public void sessionDestroyed(HttpSessionEvent se);

}
