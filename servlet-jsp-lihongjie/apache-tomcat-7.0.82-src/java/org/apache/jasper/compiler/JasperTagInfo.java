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

package org.apache.jasper.compiler;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.TagInfo;
import javax.servlet.jsp.tagext.TagLibraryInfo;
import javax.servlet.jsp.tagext.TagVariableInfo;

/**
 * TagInfo extension used by tag handlers that are implemented via tag files.
 * This class provides access to the name of the Map used to store the
 * dynamic attribute names and values passed to the custom action invocation.
 * This information is used by the code generator.
 */
class JasperTagInfo extends TagInfo {

    private String dynamicAttrsMapName;

    public JasperTagInfo(String tagName,
            String tagClassName,
            String bodyContent,
            String infoString,
            TagLibraryInfo taglib,
            TagExtraInfo tagExtraInfo,
            TagAttributeInfo[] attributeInfo,
            String displayName,
            String smallIcon,
            String largeIcon,
            TagVariableInfo[] tvi,
            String mapName) {

        super(tagName, tagClassName, bodyContent, infoString, taglib,
                tagExtraInfo, attributeInfo, displayName, smallIcon, largeIcon,
                tvi);
        
        this.dynamicAttrsMapName = mapName;
    }

    public String getDynamicAttributesMapName() {
        return dynamicAttrsMapName;
    }

    @Override
    public boolean hasDynamicAttributes() {
        return dynamicAttrsMapName != null;
    }
}
