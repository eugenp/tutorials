/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

 
package javax.servlet.jsp.tagext;

/**
 * Tag information for a tag file in a Tag Library;
 * This class is instantiated from the Tag Library Descriptor file (TLD)
 * and is available only at translation time.
 *
 * @since 2.0
 */
public class TagFileInfo {

    /**
     * Constructor for TagFileInfo from data in the JSP 2.0 format for TLD.
     * This class is to be instantiated only from the TagLibrary code
     * under request from some JSP code that is parsing a
     * TLD (Tag Library Descriptor).
     *
     * Note that, since TagLibraryInfo reflects both TLD information
     * and taglib directive information, a TagFileInfo instance is
     * dependent on a taglib directive.  This is probably a
     * design error, which may be fixed in the future.
     *
     * @param name The unique action name of this tag
     * @param path Where to find the .tag file implementing this 
     *     action, relative to the location of the TLD file.
     * @param tagInfo The detailed information about this tag, as parsed
     *     from the directives in the tag file.
     */
    public TagFileInfo( String name, String path, TagInfo tagInfo ) {
        this.name = name;
        this.path = path;
        this.tagInfo = tagInfo;
    }

    /**
     * The unique action name of this tag.
     *
     * @return The (short) name of the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Where to find the .tag file implementing this action.
     *
     * @return The path of the tag file, relative to the TLD, or "." if 
     *     the tag file was defined in an implicit tag file.
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns information about this tag, parsed from the directives 
     * in the tag file.
     *
     * @return a TagInfo object containing information about this tag
     */
    public TagInfo getTagInfo() {
        return tagInfo;
    }

    // private fields for 2.0 info
    private final String name;
    private final String path;
    private final TagInfo tagInfo;
}
