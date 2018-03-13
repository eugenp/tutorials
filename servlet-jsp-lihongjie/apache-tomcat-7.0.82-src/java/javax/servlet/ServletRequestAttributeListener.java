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
package javax.servlet;

import java.util.EventListener;

/**
 * A ServletRequestAttributeListener can be implemented by the
 * developer interested in being notified of request attribute
 * changes. Notifications will be generated while the request
 * is within the scope of the web application in which the listener
 * is registered. A request is defined as coming into scope when
 * it is about to enter the first servlet or filter in each web
 * application, as going out of scope when it exits the last servlet
 * or the first filter in the chain.
 *
 * @since Servlet 2.4
 */
public interface ServletRequestAttributeListener extends EventListener {
    /**
     * Notification that a new attribute was added to the
     * servlet request. Called after the attribute is added.
     * @param srae Information about the new request attribute
     */
    public void attributeAdded(ServletRequestAttributeEvent srae);

    /**
     * Notification that an existing attribute has been removed from the
     * servlet request. Called after the attribute is removed.
     * @param srae Information about the removed request attribute
     */
    public void attributeRemoved(ServletRequestAttributeEvent srae);

    /**
     * Notification that an attribute was replaced on the
     * servlet request. Called after the attribute is replaced.
     * @param srae Information about the replaced request attribute
     */
    public void attributeReplaced(ServletRequestAttributeEvent srae);
}

