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


import java.io.PrintWriter;
/**
 * The interface that all SSI commands ( SSIEcho, SSIInclude, ...) must
 * implement.
 * 
 * @author Bip Thelin
 * @author Dan Sandberg
 * @author David Becker
 */
public interface SSICommand {
    /**
     * Write the output of the command to the writer.
     * 
     * @param ssiMediator
     *            the ssi mediator
     * @param commandName
     *            the name of the actual command ( ie. echo )
     * @param paramNames
     *            The parameter names
     * @param paramValues
     *            The parameter values
     * @param writer
     *            the writer to output to
     * @return the most current modified date resulting from any SSI commands
     * @throws SSIStopProcessingException
     *             if SSI processing should be aborted
     */
    public long process(SSIMediator ssiMediator, String commandName,
            String[] paramNames, String[] paramValues, PrintWriter writer)
            throws SSIStopProcessingException;
}