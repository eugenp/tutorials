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

/**
 * This annotation is used to declare the configuration of an
 * {@link javax.servlet.Servlet}. <br>
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
 * The class on which this annotation is declared MUST extend
 * {@link javax.servlet.http.HttpServlet}. <br>
 * <br>
 * 
 * E.g. <code>@WebServlet("/path")}<br>
 * public class TestServlet extends HttpServlet ... {</code><br>
 * 
 * E.g.
 * <code>@WebServlet(name="TestServlet", urlPatterns={"/path", "/alt"}) <br>
 * public class TestServlet extends HttpServlet ... {</code><br>
 * 
 * @since Servlet 3.0 (Section 8.1.1)
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServlet {

    /**
     * @return name of the Servlet
     */
    String name() default "";

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
     * @return load on startup ordering hint
     */
    int loadOnStartup() default -1;

    /**
     * @return array of initialization params for this Servlet
     */
    WebInitParam[] initParams() default {};

    /**
     * @return asynchronous operation supported by this Servlet
     */
    boolean asyncSupported() default false;

    /**
     * @return small icon for this Servlet, if present
     */
    String smallIcon() default "";

    /**
     * @return large icon for this Servlet, if present
     */
    String largeIcon() default "";

    /**
     * @return description of this Servlet, if present
     */
    String description() default "";

    /**
     * @return display name of this Servlet, if present
     */
    String displayName() default "";
}
