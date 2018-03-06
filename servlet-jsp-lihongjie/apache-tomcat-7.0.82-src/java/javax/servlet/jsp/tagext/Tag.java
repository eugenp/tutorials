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
 * The interface of a classic tag handler that does not want to manipulate 
 * its body.  The Tag interface defines the basic protocol between a Tag 
 * handler and JSP page implementation class.  It defines the life cycle 
 * and the methods to be invoked at start and end tag.
 *
 * <p><B>Properties</B></p>
 *
 * <p>The Tag interface specifies the setter and getter methods for the core
 * pageContext and parent properties.</p>
 *
 * <p>The JSP page implementation object invokes setPageContext and
 * setParent, in that order, before invoking doStartTag() or doEndTag().</p>
 *
 * <p><B>Methods</B></p>
 *
 * <p>There are two main actions: doStartTag and doEndTag.  Once all
 * appropriate properties have been initialized, the doStartTag and
 * doEndTag methods can be invoked on the tag handler.  Between these
 * invocations, the tag handler is assumed to hold a state that must
 * be preserved.  After the doEndTag invocation, the tag handler is
 * available for further invocations (and it is expected to have
 * retained its properties).</p>
 *
 * <p><B>Lifecycle</B></p>
 *
 * <p>Lifecycle details are described by the transition diagram below,
 * with the following comments:</p>
 * <ul>
 * <li> [1] This transition is intended to be for releasing long-term data.
 * no guarantees are assumed on whether any properties have been retained
 * or not.
 * <li> [2] This transition happens if and only if the tag ends normally
 * without raising an exception
 * <li> [3] Some setters may be called again before a tag handler is 
 * reused.  For instance, <code>setParent()</code> is called if it's 
 * reused within the same page but at a different level, 
 * <code>setPageContext()</code> is called if it's used in another page, 
 * and attribute setters are called if the values differ or are expressed 
 * as request-time attribute values.
 * <li> Check the TryCatchFinally interface for additional details related
 * to exception handling and resource management.
 * </ul>
 *
 * <IMG src="doc-files/TagProtocol.gif"
 *      alt="Lifecycle Details Transition Diagram for Tag">
 * 
 * <p>Once all invocations on the tag handler
 * are completed, the release method is invoked on it.  Once a release
 * method is invoked <em>all</em> properties, including parent and
 * pageContext, are assumed to have been reset to an unspecified value.
 * The page compiler guarantees that release() will be invoked on the Tag
 * handler before the handler is released to the GC.</p>
 *
 * <p><B>Empty and Non-Empty Action</B></p>
 * <p>If the TagLibraryDescriptor file indicates that the action must
 * always have an empty action, by an &lt;body-content&gt; entry of "empty",
 * then the doStartTag() method must return SKIP_BODY.</p>
 *
 * <p>Otherwise, the doStartTag() method may return SKIP_BODY or
 * EVAL_BODY_INCLUDE.</p>
 *
 * <p>If SKIP_BODY is returned the body, if present, is not evaluated.</p>
 * 
 * <p>If EVAL_BODY_INCLUDE is returned, the body is evaluated and
 * "passed through" to the current out.</p>
*/

public interface Tag extends JspTag {

    /**
     * Skip body evaluation.
     * Valid return value for doStartTag and doAfterBody.
     */
 
    public static final int SKIP_BODY = 0;
 
    /**
     * Evaluate body into existing out stream.
     * Valid return value for doStartTag.
     */
 
    public static final int EVAL_BODY_INCLUDE = 1;

    /**
     * Skip the rest of the page.
     * Valid return value for doEndTag.
     */

    public static final int SKIP_PAGE = 5;

    /**
     * Continue evaluating the page.
     * Valid return value for doEndTag().
     */

    public static final int EVAL_PAGE = 6;

    // Setters for Tag handler data


    /**
     * Set the current page context.
     * This method is invoked by the JSP page implementation object
     * prior to doStartTag().
     * <p>
     * This value is *not* reset by doEndTag() and must be explicitly reset
     * by a page implementation if it changes between calls to doStartTag().
     *
     * @param pc The page context for this tag handler.
     */

    void setPageContext(PageContext pc);


    /**
     * Set the parent (closest enclosing tag handler) of this tag handler.
     * Invoked by the JSP page implementation object prior to doStartTag().
     * <p>
     * This value is *not* reset by doEndTag() and must be explicitly reset
     * by a page implementation.
     *
     * @param t The parent tag, or null.
     */


    void setParent(Tag t);


    /**
     * Get the parent (closest enclosing tag handler) for this tag handler.
     *
     * <p>
     * The getParent() method can be used to navigate the nested tag
     * handler structure at runtime for cooperation among custom actions;
     * for example, the findAncestorWithClass() method in TagSupport
     * provides a convenient way of doing this.
     *
     * <p>
     * The current version of the specification only provides one formal
     * way of indicating the observable type of a tag handler: its
     * tag handler implementation class, described in the tag-class
     * sub-element of the tag element.  This is extended in an
     * informal manner by allowing the tag library author to
     * indicate in the description sub-element an observable type.
     * The type should be a sub-type of the tag handler implementation
     * class or void.
     * This additional constraint can be exploited by a
     * specialized container that knows about that specific tag library,
     * as in the case of the JSP standard tag library.
     *
     * @return the current parent, or null if none.
     * @see TagSupport#findAncestorWithClass
     */

    Tag getParent();


    // Actions for basic start/end processing.


    /**
     * Process the start tag for this instance.
     * This method is invoked by the JSP page implementation object.
     *
     * <p>
     * The doStartTag method assumes that the properties pageContext and
     * parent have been set. It also assumes that any properties exposed as
     * attributes have been set too.  When this method is invoked, the body
     * has not yet been evaluated.
     *
     * <p>
     * This method returns Tag.EVAL_BODY_INCLUDE or
     * BodyTag.EVAL_BODY_BUFFERED to indicate
     * that the body of the action should be evaluated or SKIP_BODY to
     * indicate otherwise.
     *
     * <p>
     * When a Tag returns EVAL_BODY_INCLUDE the result of evaluating
     * the body (if any) is included into the current "out" JspWriter as it
     * happens and then doEndTag() is invoked.
     *
     * <p>
     * BodyTag.EVAL_BODY_BUFFERED is only valid  if the tag handler
     * implements BodyTag.
     *
     * <p>
     * The JSP container will resynchronize the values of any AT_BEGIN and
     * NESTED variables (defined by the associated TagExtraInfo or TLD)
     * after the invocation of doStartTag(), except for a tag handler
     * implementing BodyTag whose doStartTag() method returns
     * BodyTag.EVAL_BODY_BUFFERED.
     *
     * @return EVAL_BODY_INCLUDE if the tag wants to process body, SKIP_BODY 
     *     if it does not want to process it.
     * @throws JspException if an error occurred while processing this tag
     * @see BodyTag
     */
 
    int doStartTag() throws JspException;
 

    /**
     * Process the end tag for this instance.
     * This method is invoked by the JSP page implementation object
     * on all Tag handlers.
     *
     * <p>
     * This method will be called after returning from doStartTag. The
     * body of the action may or may not have been evaluated, depending on
     * the return value of doStartTag.
     *
     * <p>
     * If this method returns EVAL_PAGE, the rest of the page continues
     * to be evaluated.  If this method returns SKIP_PAGE, the rest of
     * the page is not evaluated, the request is completed, and 
     * the doEndTag() methods of enclosing tags are not invoked.  If this
     * request was forwarded or included from another page (or Servlet),
     * only the current page evaluation is stopped.
     *
     * <p>
     * The JSP container will resynchronize the values of any AT_BEGIN and
     * AT_END variables (defined by the associated TagExtraInfo or TLD)
     * after the invocation of doEndTag().
     *
     * @return indication of whether to continue evaluating the JSP page.
     * @throws JspException if an error occurred while processing this tag
     */

    int doEndTag() throws JspException;

    /**
     * Called on a Tag handler to release state.
     * The page compiler guarantees that JSP page implementation
     * objects will invoke this method on all tag handlers,
     * but there may be multiple invocations on doStartTag and doEndTag in between.
     */

    void release();

}
