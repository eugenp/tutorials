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

import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;

/**
 * Parses names.
 *
 * @author Remy Maucherat
 */
public class NameParserImpl 
    implements NameParser {


    // ----------------------------------------------------- Instance Variables


    // ----------------------------------------------------- NameParser Methods


    /**
     * Parses a name into its components.
     * 
     * @param name The non-null string name to parse
     * @return A non-null parsed form of the name using the naming convention 
     * of this parser.
     */
    @Override
    public Name parse(String name)
        throws NamingException {
        return new CompositeName(name);
    }


}

