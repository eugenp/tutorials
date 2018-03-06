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
package javax.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.DispatcherType;

/**
 * The annotation used to declare a Servlet {@link javax.servlet.Filter}. <br>
 * <br>
 * 
 * This annotation will be processed by the container during deployment, the
 * Filter class in which it is found will be created as per the configuration
 * and applied to the URL patterns, {@link javax.servlet.Servlet}s and
 * {@link javax.servlet.DispatcherType}s.<br>
 * <br>
 * 
 * If the name attribute is not defined, the fully qualified name of the class
 * is used.<br>
 * <br>
 * 
 * At least one URL pattern MUST be declared in either the {@code value} or
 * {@code urlPattern} attribute of the annotation, but not both.<br>
 * <br>
 * 
 * The {@code value} attribute is recommended for use when the URL pattern is
 * the only attribute being set, otherwise the {@code urlPattern} attribute
 * should be used.<br>
 * <br>
 * 
 * The annotated class MUST implement {@link javax.servlet.Filter}.
 * 
 * E.g.
 * 
 * <code>@WebFilter("/path/*")</code><br>
 * <code>public class AnExampleFilter implements Filter { ... </code><br>
 * 
 * @since Servlet 3.0 (Section 8.1.2)
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebFilter {

    /**
     * @return description of the Filter, if present
     */
    String description() default "";

    /**
     * @return display name of the Filter, if present
     */
    String displayName() default "";

    /**
     * @return array of initialization params for this Filter
     */
    WebInitParam[] initParams() default {};

    /**
     * @return name of the Filter, if present
     */
    String filterName() default "";

    /**
     * @return small icon for this Filter, if present
     */
    String smallIcon() default "";

    /**
     * @return the large icon for this Filter, if present
     */
    String largeIcon() default "";

    /**
     * @return array of Servlet names to which this Filter applies
     */
    String[] servletNames() default {};

    /**
     * A convenience method, to allow extremely simple annotation of a class.
     * 
     * @return array of URL patterns
     * @see #urlPatterns()
     */
    String[] value() default {};

    /**
     * @return array of URL patterns to which this Filter applies
     */
    String[] urlPatterns() default {};

    /**
     * @return array of DispatcherTypes to which this filter applies
     */
    DispatcherType[] dispatcherTypes() default {DispatcherType.REQUEST};

    /**
     * @return asynchronous operation supported by this Filter
     */
    boolean asyncSupported() default false;
}
