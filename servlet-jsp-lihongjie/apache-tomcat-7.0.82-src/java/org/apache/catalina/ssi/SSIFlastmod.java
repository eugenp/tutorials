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
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;

import org.apache.catalina.util.Strftime;

/**
 * Implements the Server-side #flastmod command
 * 
 * @author Bip Thelin
 * @author Paul Speed
 * @author Dan Sandberg
 * @author David Becker
 */
public final class SSIFlastmod implements SSICommand {
    /**
     * @see SSICommand
     */
    @Override
    public long process(SSIMediator ssiMediator, String commandName,
            String[] paramNames, String[] paramValues, PrintWriter writer) {
        long lastModified = 0;
        String configErrMsg = ssiMediator.getConfigErrMsg();
        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            String paramValue = paramValues[i];
            String substitutedValue = ssiMediator
                    .substituteVariables(paramValue);
            try {
                if (paramName.equalsIgnoreCase("file")
                        || paramName.equalsIgnoreCase("virtual")) {
                    boolean virtual = paramName.equalsIgnoreCase("virtual");
                    lastModified = ssiMediator.getFileLastModified(
                            substitutedValue, virtual);
                    Date date = new Date(lastModified);
                    String configTimeFmt = ssiMediator.getConfigTimeFmt();
                    writer.write(formatDate(date, configTimeFmt));
                } else {
                    ssiMediator.log("#flastmod--Invalid attribute: "
                            + paramName);
                    writer.write(configErrMsg);
                }
            } catch (IOException e) {
                ssiMediator.log(
                        "#flastmod--Couldn't get last modified for file: "
                                + substitutedValue, e);
                writer.write(configErrMsg);
            }
        }
        return lastModified;
    }


    protected String formatDate(Date date, String configTimeFmt) {
        Strftime strftime = new Strftime(configTimeFmt, Locale.US);
        return strftime.format(date);
    }
}