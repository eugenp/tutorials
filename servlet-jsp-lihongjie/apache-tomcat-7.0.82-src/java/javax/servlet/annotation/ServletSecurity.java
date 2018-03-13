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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declare this annotation on a {@link javax.servlet.Servlet} implementation
 * class to enforce security constraints on HTTP protocol requests.<br>
 * The container applies constraints to the URL patterns mapped to each Servlet
 * which declares this annotation.<br>
 * <br>
 * 
 * @since Servlet 3.0
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServletSecurity {

    /**
     * Represents the two possible values of the empty role semantic, active
     * when a list of role names is empty.
     */
    enum EmptyRoleSemantic {

        /**
         * Access MUST be permitted, regardless of authentication state or
         * identity
         */
        PERMIT,

        /**
         * Access MUST be denied, regardless of authentication state or identity
         */
        DENY
    }

    /**
     * Represents the two possible values of data transport, encrypted or not.
     */
    enum TransportGuarantee {

        /**
         * User data must not be encrypted by the container during transport
         */
        NONE,

        /**
         * The container MUST encrypt user data during transport
         */
        CONFIDENTIAL
    }

    /**
     * The default constraint to apply to requests not handled by specific
     * method constraints
     * 
     * @return http constraint
     */
    HttpConstraint value() default @HttpConstraint;

    /**
     * An array of HttpMethodConstraint objects to which the security constraint
     * will be applied
     * 
     * @return array of http method constraint
     */
    HttpMethodConstraint[] httpMethodConstraints() default {};
}
