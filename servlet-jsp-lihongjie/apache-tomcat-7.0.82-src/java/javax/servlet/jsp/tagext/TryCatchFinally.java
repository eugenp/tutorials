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
 * The auxiliary interface of a Tag, IterationTag or BodyTag tag
 * handler that wants additional hooks for managing resources.
 *
 * <p>This interface provides two new methods: doCatch(Throwable)
 * and doFinally().  The prototypical invocation is as follows:
 *
 * <pre>
 * h = get a Tag();  // get a tag handler, perhaps from pool
 *
 * h.setPageContext(pc);  // initialize as desired
 * h.setParent(null);
 * h.setFoo("foo");
 * 
 * // tag invocation protocol; see Tag.java
 * try {
 *   doStartTag()...
 *   ....
 *   doEndTag()...
 * } catch (Throwable t) {
 *   // react to exceptional condition
 *   h.doCatch(t);
 * } finally {
 *   // restore data invariants and release per-invocation resources
 *   h.doFinally();
 * }
 * 
 * ... other invocations perhaps with some new setters
 * ...
 * h.release();  // release long-term resources
 * </pre>
 */

public interface TryCatchFinally {

    /**
     * Invoked if a Throwable occurs while evaluating the BODY
     * inside a tag or in any of the following methods:
     * Tag.doStartTag(), Tag.doEndTag(),
     * IterationTag.doAfterBody() and BodyTag.doInitBody().
     *
     * <p>This method is not invoked if the Throwable occurs during
     * one of the setter methods.
     *
     * <p>This method may throw an exception (the same or a new one)
     * that will be propagated further up the nest chain.  If an exception
     * is thrown, doFinally() will be invoked.
     *
     * <p>This method is intended to be used to respond to an exceptional
     * condition.
     *
     * @param t The throwable exception navigating through this tag.
     * @throws Throwable if the exception is to be rethrown further up 
     *     the nest chain.
     */
 
    void doCatch(Throwable t) throws Throwable;

    /**
     * Invoked in all cases after doEndTag() for any class implementing
     * Tag, IterationTag or BodyTag.  This method is invoked even if
     * an exception has occurred in the BODY of the tag,
     * or in any of the following methods:
     * Tag.doStartTag(), Tag.doEndTag(),
     * IterationTag.doAfterBody() and BodyTag.doInitBody().
     *
     * <p>This method is not invoked if the Throwable occurs during
     * one of the setter methods.
     *
     * <p>This method should not throw an Exception.
     *
     * <p>This method is intended to maintain per-invocation data
     * integrity and resource management actions.
     */

    void doFinally();
}
