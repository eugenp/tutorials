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


/**
 * Represents a binding in a NamingContext.
 *
 * @author Remy Maucherat
 */

public class NamingEntry {


    // -------------------------------------------------------------- Constants


    public static final int ENTRY = 0;
    public static final int LINK_REF = 1;
    public static final int REFERENCE = 2;
    
    public static final int CONTEXT = 10;


    // ----------------------------------------------------------- Constructors


    public NamingEntry(String name, Object value, int type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The type instance variable is used to avoid using RTTI when doing
     * lookups.
     */
    public int type;
    public String name;
    public Object value;


    // --------------------------------------------------------- Object Methods


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamingEntry) {
            return name.equals(((NamingEntry) obj).name);
        } else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }


}
