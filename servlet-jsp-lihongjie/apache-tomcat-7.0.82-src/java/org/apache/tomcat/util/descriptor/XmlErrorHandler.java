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
package org.apache.tomcat.util.descriptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.juli.logging.Log;
import org.apache.tomcat.util.res.StringManager;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlErrorHandler implements ErrorHandler {

    private static final StringManager sm =
        StringManager.getManager(Constants.PACKAGE_NAME);

    private final List<SAXParseException> errors = new ArrayList<SAXParseException>();

    private final List<SAXParseException> warnings = new ArrayList<SAXParseException>();

    @Override
    public void error(SAXParseException exception) throws SAXException {
        // Collect non-fatal errors
        errors.add(exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        // Re-throw fatal errors
        throw exception;
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        // Collect warnings
        warnings.add(exception);
    }

    public List<SAXParseException> getErrors() {
        // Internal use only - don't worry about immutability
        return errors;
    }

    public List<SAXParseException> getWarnings() {
        // Internal use only - don't worry about immutability
        return warnings;
    }

    public void logFindings(Log log, String source) {
        for (SAXParseException e : getWarnings()) {
            log.warn(sm.getString(
                    "xmlErrorHandler.warning", e.getMessage(), source));
        }
        for (SAXParseException e : getErrors()) {
            log.warn(sm.getString(
                    "xmlErrorHandler.error", e.getMessage(), source));
        }
    }
}
