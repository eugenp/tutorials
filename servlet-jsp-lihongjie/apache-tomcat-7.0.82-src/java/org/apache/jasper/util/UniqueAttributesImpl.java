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
package org.apache.jasper.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.jasper.compiler.Localizer;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Wraps the default attributes implementation and ensures that each attribute
 * has a unique qname as required by the JSP specification.
 */
public class UniqueAttributesImpl extends AttributesImpl {

    private static final String IMPORT = "import";
    private static final String PAGE_ENCODING = "pageEncoding";
    
    private final boolean pageDirective;
    private final Set<String> qNames = new HashSet<String>();

    public UniqueAttributesImpl() {
        this.pageDirective = false;
    }

    public UniqueAttributesImpl(boolean pageDirective) {
        this.pageDirective = pageDirective;
    }

    @Override
    public void clear() {
        qNames.clear();
        super.clear();
    }

    @Override
    public void setAttributes(Attributes atts) {
        for (int i = 0; i < atts.getLength(); i++) {
            if (!qNames.add(atts.getQName(i))) {
                handleDuplicate(atts.getQName(i), atts.getValue(i));
            }
        }
        super.setAttributes(atts);
    }

    @Override
    public void addAttribute(String uri, String localName, String qName,
            String type, String value) {
        if (qNames.add(qName)) {
            super.addAttribute(uri, localName, qName, type, value);
        } else {
            handleDuplicate(qName, value);
        }
    }

    @Override
    public void setAttribute(int index, String uri, String localName,
            String qName, String type, String value) {
        qNames.remove(super.getQName(index));
        if (qNames.add(qName)) {
            super.setAttribute(index, uri, localName, qName, type, value);
        } else {
            handleDuplicate(qName, value);
        }
    }

    @Override
    public void removeAttribute(int index) {
        qNames.remove(super.getQName(index));
        super.removeAttribute(index);
    }

    @Override
    public void setQName(int index, String qName) {
        qNames.remove(super.getQName(index));
        super.setQName(index, qName);
    }

    private void handleDuplicate(String qName, String value) {
        if (pageDirective) {
            if (IMPORT.equalsIgnoreCase(qName)) {
                // Always merge imports
                int i = super.getIndex(IMPORT);
                String v = super.getValue(i);
                super.setValue(i, v + "," + value);
                return;
            } else if (PAGE_ENCODING.equalsIgnoreCase(qName)) {
                // Page encoding can only occur once per file so a second
                // attribute - even one with a duplicate value - is an error
            } else {
                // Other attributes can be repeated if and only if the values
                // are identical
                String v = super.getValue(qName);
                if (v.equals(value)) {
                    return;
                }
            }
        }

        // Ordinary tag attributes can't be repeated, even with identical values
        throw new IllegalArgumentException(
                    Localizer.getMessage("jsp.error.duplicateqname", qName));
    }
}
