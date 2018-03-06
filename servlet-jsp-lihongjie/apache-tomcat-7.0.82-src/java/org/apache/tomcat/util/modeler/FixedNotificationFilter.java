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


package org.apache.tomcat.util.modeler;


import java.util.HashSet;

import javax.management.Notification;
import javax.management.NotificationFilter;


/**
 * Special NotificationFilter that allows modeler to optimize its notifications.
 *
 * This class is immutable - after you construct it it'll filter based on
 * a fixed set of notification names.
 *
 * The JMX specification requires the filters to be called before the
 * notifications are sent. We can call this filter well in advance, when
 * the listener is added. Based on the result we can maintain separate
 * channels for each notification - and reduce the overhead.
 *
 * @author Costin Manolache
 */
public class FixedNotificationFilter implements NotificationFilter {

    private static final long serialVersionUID = 1L;
    /**
     * The set of attribute names that are accepted by this filter.  If this
     * list is empty, all attribute names are accepted.
     */
    private HashSet<String> names = new HashSet<String>();
    String namesA[]=null;

    /**
     * Construct a new filter that accepts only the specified notification
     * names.
     *
     * @param names Names of the notification types
     */
    public FixedNotificationFilter(String names[]) {
        super();
    }

    /**
     * Return the set of names that are accepted by this filter.  If this
     * filter accepts all attribute names, a zero length array will be
     * returned.
     */
    public String[] getNames() {
        synchronized (names) {
            return names.toArray(new String[names.size()]);
        }
    }


    /**
     * <p>Test whether notification enabled for this event.
     * Return true if:</p>
     * <ul>
     * <li>Either the set of accepted names is empty (implying that all
     *     attribute names are of interest) or the set of accepted names
     *     includes the name of the attribute in this notification</li>
     * </ul>
     */
    @Override
    public boolean isNotificationEnabled(Notification notification) {

        if (notification == null)
            return (false);
        synchronized (names) {
            if (names.size() < 1)
                return (true);
            else
                return (names.contains(notification.getType()));
        }

    }


}
