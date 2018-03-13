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

package org.apache.catalina.manager.util;

import java.util.Comparator;

import org.apache.catalina.Session;

/**
 * Comparator which permits to compare on a session's content
 * @author C&eacute;drik LIME
 */
public abstract class BaseSessionComparator<T> implements Comparator<Session> {

    /**
     * 
     */
    public BaseSessionComparator() {
        super();
    }

    public abstract Comparable<T> getComparableObject(Session session);

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public final int compare(Session s1, Session s2) {
        Comparable<T> c1 = getComparableObject(s1);
        Comparable<T> c2 = getComparableObject(s2);
        return c1==null ? (c2==null ? 0 : -1) : (c2==null ? 1 : c1.compareTo((T)c2));
    }
}
