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
 * This listener interface can be implemented in order to get notifications of
 * changes to the attribute lists of sessions within this web application.
 * 
 * @since v 2.3
 */
public interface HttpSessionAttributeListener extends EventListener {

    /**
     * Notification that an attribute has been added to a session. Called after
     * the attribute is added.
     */
    public void attributeAdded(HttpSessionBindingEvent se);

    /**
     * Notification that an attribute has been removed from a session. Called
     * after the attribute is removed.
     */
    public void attributeRemoved(HttpSessionBindingEvent se);

    /**
     * Notification that an attribute has been replaced in a session. Called
     * after the attribute is replaced.
     */
    public void attributeReplaced(HttpSessionBindingEvent se);
}
