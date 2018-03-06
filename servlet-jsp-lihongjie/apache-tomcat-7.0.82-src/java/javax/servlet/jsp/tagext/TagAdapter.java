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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Wraps any SimpleTag and exposes it using a Tag interface. This is used to
 * allow collaboration between classic Tag handlers and SimpleTag handlers.
 * <p>
 * Because SimpleTag does not extend Tag, and because Tag.setParent() only
 * accepts a Tag instance, a classic tag handler (one that implements Tag)
 * cannot have a SimpleTag as its parent. To remedy this, a TagAdapter is
 * created to wrap the SimpleTag parent, and the adapter is passed to
 * setParent() instead. A classic Tag Handler can call getAdaptee() to retrieve
 * the encapsulated SimpleTag instance.
 * 
 * @since 2.0
 */
public class TagAdapter implements Tag {
    /** The simple tag that's being adapted. */
    private SimpleTag simpleTagAdaptee;

    /** The parent, of this tag, converted (if necessary) to be of type Tag. */
    private Tag parent;

    // Flag indicating whether we have already determined the parent
    private boolean parentDetermined;

    /**
     * Creates a new TagAdapter that wraps the given SimpleTag and returns the
     * parent tag when getParent() is called.
     * 
     * @param adaptee
     *            The SimpleTag being adapted as a Tag.
     */
    public TagAdapter(SimpleTag adaptee) {
        if (adaptee == null) {
            // Cannot wrap a null adaptee.
            throw new IllegalArgumentException();
        }
        this.simpleTagAdaptee = adaptee;
    }

    /**
     * Must not be called.
     * 
     * @param pc
     *            ignored.
     * @throws UnsupportedOperationException
     *             Must not be called
     */
    @Override
    public void setPageContext(PageContext pc) {
        throw new UnsupportedOperationException(
                "Illegal to invoke setPageContext() on TagAdapter wrapper");
    }

    /**
     * Must not be called. The parent of this tag is always
     * getAdaptee().getParent().
     * 
     * @param parentTag
     *            ignored.
     * @throws UnsupportedOperationException
     *             Must not be called.
     */
    @Override
    public void setParent(Tag parentTag) {
        throw new UnsupportedOperationException(
                "Illegal to invoke setParent() on TagAdapter wrapper");
    }

    /**
     * Returns the parent of this tag, which is always getAdaptee().getParent().
     * This will either be the enclosing Tag (if getAdaptee().getParent()
     * implements Tag), or an adapter to the enclosing Tag (if
     * getAdaptee().getParent() does not implement Tag).
     * 
     * @return The parent of the tag being adapted.
     */
    @Override
    public Tag getParent() {
        if (!parentDetermined) {
            JspTag adapteeParent = simpleTagAdaptee.getParent();
            if (adapteeParent != null) {
                if (adapteeParent instanceof Tag) {
                    this.parent = (Tag) adapteeParent;
                } else {
                    // Must be SimpleTag - no other types defined.
                    this.parent = new TagAdapter((SimpleTag) adapteeParent);
                }
            }
            parentDetermined = true;
        }

        return this.parent;
    }

    /**
     * Gets the tag that is being adapted to the Tag interface. This should be
     * an instance of SimpleTag in JSP 2.0, but room is left for other kinds of
     * tags in future spec versions.
     * 
     * @return the tag that is being adapted
     */
    public JspTag getAdaptee() {
        return this.simpleTagAdaptee;
    }

    /**
     * Must not be called.
     * 
     * @return always throws UnsupportedOperationException
     * @throws UnsupportedOperationException
     *             Must not be called
     * @throws JspException
     *             never thrown
     */
    @Override
    public int doStartTag() throws JspException {
        throw new UnsupportedOperationException(
                "Illegal to invoke doStartTag() on TagAdapter wrapper");
    }

    /**
     * Must not be called.
     * 
     * @return always throws UnsupportedOperationException
     * @throws UnsupportedOperationException
     *             Must not be called
     * @throws JspException
     *             never thrown
     */
    @Override
    public int doEndTag() throws JspException {
        throw new UnsupportedOperationException(
                "Illegal to invoke doEndTag() on TagAdapter wrapper");
    }

    /**
     * Must not be called.
     * 
     * @throws UnsupportedOperationException
     *             Must not be called
     */
    @Override
    public void release() {
        throw new UnsupportedOperationException(
                "Illegal to invoke release() on TagAdapter wrapper");
    }
}
