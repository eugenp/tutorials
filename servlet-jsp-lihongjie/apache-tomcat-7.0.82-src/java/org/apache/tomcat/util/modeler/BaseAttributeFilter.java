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

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;


/**
 * <p>Implementation of <code>NotificationFilter</code> for attribute change
 * notifications.  This class is used by <code>BaseModelMBean</code> to
 * construct attribute change notification event filters when a filter is not
 * supplied by the application.</p>
 *
 * @author Craig R. McClanahan
 */
public class BaseAttributeFilter implements NotificationFilter {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------- Constructors

    /**
     * Construct a new filter that accepts only the specified attribute
     * name.
     *
     * @param name Name of the attribute to be accepted by this filter, or
     *  <code>null</code> to accept all attribute names
     */
    public BaseAttributeFilter(String name) {

        super();
        if (name != null)
            addAttribute(name);

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The set of attribute names that are accepted by this filter.  If this
     * list is empty, all attribute names are accepted.
     */
    private HashSet<String> names = new HashSet<String>();


    // --------------------------------------------------------- Public Methods


    /**
     * Add a new attribute name to the set of names accepted by this filter.
     *
     * @param name Name of the attribute to be accepted
     */
    public void addAttribute(String name) {

        synchronized (names) {
            names.add(name);
        }

    }


    /**
     * Clear all accepted names from this filter, so that it will accept
     * all attribute names.
     */
    public void clear() {

        synchronized (names) {
            names.clear();
        }

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
     * <li>This is an attribute change notification</li>
     * <li>Either the set of accepted names is empty (implying that all
     *     attribute names are of interest) or the set of accepted names
     *     includes the name of the attribute in this notification</li>
     * </ul>
     */
    @Override
    public boolean isNotificationEnabled(Notification notification) {

        if (notification == null)
            return (false);
        if (!(notification instanceof AttributeChangeNotification))
            return (false);
        AttributeChangeNotification acn =
            (AttributeChangeNotification) notification;
        if (!AttributeChangeNotification.ATTRIBUTE_CHANGE.equals(acn.getType()))
            return (false);
        synchronized (names) {
            if (names.size() < 1)
                return (true);
            else
                return (names.contains(acn.getAttributeName()));
        }

    }


    /**
     * Remove an attribute name from the set of names accepted by this
     * filter.
     *
     * @param name Name of the attribute to be removed
     */
    public void removeAttribute(String name) {

        synchronized (names) {
            names.remove(name);
        }

    }


}
