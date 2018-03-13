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
package javax.servlet.jsp;

import javax.el.ELContextListener;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;

/**
 * <p>
 * Stores <i>application</i>-scoped information for the JSP container.
 * </p>
 * 
 * @since 2.1
 */
public interface JspApplicationContext {

    /**
     * Registers an <code>ELContextListener</code> that will be notified
     * whenever a new <code>ELContext</code> is created.
     * <p>
     * At the very least, any <code>ELContext</code> instantiated will have
     * reference to the <code>JspContext</code> under
     * <code>JspContext.class</code>.
     * 
     * @param listener The listener to add
     */
    public void addELContextListener(ELContextListener listener);

    /**
     * <p>
     * Adds an <code>ELResolver</code> to the chain of EL variable and property
     * management within JSP pages and Tag files.
     * </p>
     * <p>
     * JSP has a default set of ELResolvers to chain for all EL evaluation:
     * </p>
     * <ul>
     * <li><code>ImplicitObjectELResolver</code></li>
     * <li><code>ELResolver</code> instances registered with this method</li>
     * <li><code>MapELResolver</code></li>
     * <li><code>ListELResolver</code></li>
     * <li><code>ArrayELResolver</code></li>
     * <li><code>BeanELResolver</code></li>
     * <li><code>ScopedAttributeELResolver</code></li>
     * </ul>
     * 
     * @param resolver
     *            an additional resolver
     * @throws IllegalStateException
     *             if called after the application's
     *             <code>ServletContextListeners</code> have been initialized.
     */
    public void addELResolver(ELResolver resolver) throws IllegalStateException;

    /**
     * <p>
     * Returns the JSP container's <code>ExpressionFactory</code> implementation
     * for EL use.
     * </p>
     * 
     * @return an <code>ExpressionFactory</code> implementation
     */
    public ExpressionFactory getExpressionFactory();

}
