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
 * Causes an object to be notified when it is bound to or unbound from a
 * session. The object is notified by an {@link HttpSessionBindingEvent} object.
 * This may be as a result of a servlet programmer explicitly unbinding an
 * attribute from a session, due to a session being invalidated, or due to a
 * session timing out.
 * 
 * @author Various
 * @see HttpSession
 * @see HttpSessionBindingEvent
 */
public interface HttpSessionBindingListener extends EventListener {

    /**
     * Notifies the object that it is being bound to a session and identifies
     * the session.
     * 
     * @param event
     *            the event that identifies the session
     * @see #valueUnbound
     */
    public void valueBound(HttpSessionBindingEvent event);

    /**
     * Notifies the object that it is being unbound from a session and
     * identifies the session.
     * 
     * @param event
     *            the event that identifies the session
     * @see #valueBound
     */
    public void valueUnbound(HttpSessionBindingEvent event);
}
