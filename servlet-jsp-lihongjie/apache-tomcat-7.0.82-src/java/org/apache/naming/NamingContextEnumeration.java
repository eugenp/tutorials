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


package org.apache.naming;

import java.util.Iterator;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * Naming enumeration implementation.
 *
 * @author Remy Maucherat
 */
public class NamingContextEnumeration 
    implements NamingEnumeration<NameClassPair> {


    // ----------------------------------------------------------- Constructors


    public NamingContextEnumeration(Iterator<NamingEntry> entries) {
        iterator = entries;
    }


    // -------------------------------------------------------------- Variables


    /**
     * Underlying enumeration.
     */
    protected Iterator<NamingEntry> iterator;


    // --------------------------------------------------------- Public Methods


    /**
     * Retrieves the next element in the enumeration.
     */
    @Override
    public NameClassPair next()
        throws NamingException {
        return nextElement();
    }


    /**
     * Determines whether there are any more elements in the enumeration.
     */
    @Override
    public boolean hasMore()
        throws NamingException {
        return iterator.hasNext();
    }


    /**
     * Closes this enumeration.
     */
    @Override
    public void close()
        throws NamingException {
    }


    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }


    @Override
    public NameClassPair nextElement() {
        NamingEntry entry = iterator.next();
        return new NameClassPair(entry.name, entry.value.getClass().getName());
    }


}

