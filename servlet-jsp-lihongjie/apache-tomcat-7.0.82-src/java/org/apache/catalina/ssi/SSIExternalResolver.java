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
package org.apache.catalina.ssi;


import java.io.IOException;
import java.util.Collection;
import java.util.Date;
/**
 * Interface used by SSIMediator to talk to the 'outside world' ( usually a
 * servlet )
 * 
 * @author Dan Sandberg
 */
public interface SSIExternalResolver {
    /**
     * Adds any external variables to the variableNames collection.
     * 
     * @param variableNames
     *            the collection to add to
     */
    public void addVariableNames(Collection<String> variableNames);


    public String getVariableValue(String name);


    /**
     * Set the named variable to the specified value. If value is null, then
     * the variable will be removed ( ie. a call to getVariableValue will
     * return null )
     * 
     * @param name
     *            of the variable
     * @param value
     *            of the variable
     */
    public void setVariableValue(String name, String value);


    /**
     * Returns the current date. This is useful for putting the SSI stuff in a
     * regression test. Since you can make the current date a constant, it
     * makes testing easier since the output won't change.
     * 
     * @return the data
     */
    public Date getCurrentDate();


    public long getFileSize(String path, boolean virtual) throws IOException;


    public long getFileLastModified(String path, boolean virtual)
            throws IOException;


    public String getFileText(String path, boolean virtual) throws IOException;


    public void log(String message, Throwable throwable);
}