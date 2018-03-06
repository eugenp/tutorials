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
 * Translation-time information associated with a taglib directive, and its
 * underlying TLD file. Most of the information is directly from the TLD, except
 * for the prefix and the uri values used in the taglib directive
 */
public abstract class TagLibraryInfo {

    /**
     * Constructor. This will invoke the constructors for TagInfo, and
     * TagAttributeInfo after parsing the TLD file.
     * 
     * @param prefix
     *            the prefix actually used by the taglib directive
     * @param uri
     *            the URI actually used by the taglib directive
     */
    protected TagLibraryInfo(String prefix, String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    // ==== methods accessing taglib information =======

    /**
     * The value of the uri attribute from the taglib directive for this
     * library.
     * 
     * @return the value of the uri attribute
     */
    public String getURI() {
        return uri;
    }

    /**
     * The prefix assigned to this taglib from the taglib directive
     * 
     * @return the prefix assigned to this taglib from the taglib directive
     */
    public String getPrefixString() {
        return prefix;
    }

    // ==== methods using the TLD data =======

    /**
     * The preferred short name (prefix) as indicated in the TLD. This may be
     * used by authoring tools as the preferred prefix to use when creating an
     * taglib directive for this library.
     * 
     * @return the preferred short name for the library
     */
    public String getShortName() {
        return shortname;
    }

    /**
     * The "reliable" URN indicated in the TLD (the uri element). This may be
     * used by authoring tools as a global identifier to use when creating a
     * taglib directive for this library.
     * 
     * @return a reliable URN to a TLD like this
     */
    public String getReliableURN() {
        return urn;
    }

    /**
     * Information (documentation) for this TLD.
     * 
     * @return the info string for this tag lib
     */
    public String getInfoString() {
        return info;
    }

    /**
     * A string describing the required version of the JSP container.
     * 
     * @return the (minimal) required version of the JSP container.
     * @see javax.servlet.jsp.JspEngineInfo
     */
    public String getRequiredVersion() {
        return jspversion;
    }

    /**
     * An array describing the tags that are defined in this tag library.
     * 
     * @return the TagInfo objects corresponding to the tags defined by this tag
     *         library, or a zero length array if this tag library defines no
     *         tags
     */
    public TagInfo[] getTags() {
        return tags;
    }

    /**
     * An array describing the tag files that are defined in this tag library.
     * 
     * @return the TagFileInfo objects corresponding to the tag files defined by
     *         this tag library, or a zero length array if this tag library
     *         defines no tags files
     * @since 2.0
     */
    public TagFileInfo[] getTagFiles() {
        return tagFiles;
    }

    /**
     * Get the TagInfo for a given tag name, looking through all the tags in
     * this tag library.
     * 
     * @param shortname
     *            The short name (no prefix) of the tag
     * @return the TagInfo for the tag with the specified short name, or null if
     *         no such tag is found
     */
    public TagInfo getTag(String shortname) {
        TagInfo tags[] = getTags();

        if (tags == null || tags.length == 0 || shortname == null) {
            return null;
        }

        for (int i = 0; i < tags.length; i++) {
            if (shortname.equals(tags[i].getTagName())) {
                return tags[i];
            }
        }
        return null;
    }

    /**
     * Get the TagFileInfo for a given tag name, looking through all the tag
     * files in this tag library.
     * 
     * @param shortname
     *            The short name (no prefix) of the tag
     * @return the TagFileInfo for the specified Tag file, or null if no Tag
     *         file is found
     * @since 2.0
     */
    public TagFileInfo getTagFile(String shortname) {
        TagFileInfo tagFiles[] = getTagFiles();

        if (tagFiles == null || tagFiles.length == 0) {
            return null;
        }

        for (int i = 0; i < tagFiles.length; i++) {
            if (tagFiles[i].getName().equals(shortname)) {
                return tagFiles[i];
            }
        }
        return null;
    }

    /**
     * An array describing the functions that are defined in this tag library.
     * 
     * @return the functions defined in this tag library, or a zero length array
     *         if the tag library defines no functions.
     * @since 2.0
     */
    public FunctionInfo[] getFunctions() {
        return functions;
    }

    /**
     * Get the FunctionInfo for a given function name, looking through all the
     * functions in this tag library.
     * 
     * @param name
     *            The name (no prefix) of the function
     * @return the FunctionInfo for the function with the given name, or null if
     *         no such function exists
     * @since 2.0
     */
    public FunctionInfo getFunction(String name) {

        if (functions == null || functions.length == 0) {
            return null;
        }

        for (int i = 0; i < functions.length; i++) {
            if (functions[i].getName().equals(name)) {
                return functions[i];
            }
        }
        return null;
    }

    /**
     * Returns an array of TagLibraryInfo objects representing the entire set of
     * tag libraries (including this TagLibraryInfo) imported by taglib
     * directives in the translation unit that references this TagLibraryInfo.
     * If a tag library is imported more than once and bound to different
     * prefixes, only the TagLibraryInfo bound to the first prefix must be
     * included in the returned array.
     * 
     * @return Array of TagLibraryInfo objects representing the entire set of
     *         tag libraries (including this TagLibraryInfo) imported by taglib
     *         directives in the translation unit that references this
     *         TagLibraryInfo.
     * @since 2.1
     */
    public abstract javax.servlet.jsp.tagext.TagLibraryInfo[] getTagLibraryInfos();

    // Protected fields

    /**
     * The prefix assigned to this taglib from the taglib directive.
     */
    protected String prefix;

    /**
     * The value of the uri attribute from the taglib directive for this
     * library.
     */
    protected String uri;

    /**
     * An array describing the tags that are defined in this tag library.
     */
    protected TagInfo[] tags;

    /**
     * An array describing the tag files that are defined in this tag library.
     * 
     * @since 2.0
     */
    protected TagFileInfo[] tagFiles;

    /**
     * An array describing the functions that are defined in this tag library.
     * 
     * @since 2.0
     */
    protected FunctionInfo[] functions;

    // Tag Library Data

    /**
     * The version of the tag library.
     */
    protected String tlibversion; // required

    /**
     * The version of the JSP specification this tag library is written to.
     */
    protected String jspversion; // required

    /**
     * The preferred short name (prefix) as indicated in the TLD.
     */
    protected String shortname; // required

    /**
     * The "reliable" URN indicated in the TLD.
     */
    protected String urn; // required

    /**
     * Information (documentation) for this TLD.
     */
    protected String info; // optional
}
