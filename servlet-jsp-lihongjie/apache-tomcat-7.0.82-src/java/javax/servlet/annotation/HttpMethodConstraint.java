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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

/**
 * Specific security constraints can be applied to different types of request,
 * differentiated by the HTTP protocol method type by using this annotation
 * inside the {@link javax.servlet.annotation.ServletSecurity} annotation.
 * 
 * @since Servlet 3.0
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpMethodConstraint {

    /**
     * HTTP Protocol method name (e.g. POST, PUT)
     * 
     * @return method name
     */
    String value();

    /**
     * The EmptyRoleSemantic determines the behaviour when the rolesAllowed list
     * is empty.
     * 
     * @return empty role semantic
     */
    EmptyRoleSemantic emptyRoleSemantic() default EmptyRoleSemantic.PERMIT;

    /**
     * Determines whether SSL/TLS is required to process the current request.
     * 
     * @return transport guarantee
     */
    TransportGuarantee transportGuarantee() default TransportGuarantee.NONE;

    /**
     * The authorized roles' names. The container may discard duplicate role
     * names during processing of the annotation. N.B. The String "*" does not
     * have a special meaning if it occurs as a role name.
     * 
     * @return array of names. The array may be of zero length, in which case
     *         the EmptyRoleSemantic applies; the returned value determines
     *         whether access is to be permitted or denied regardless of the
     *         identity and authentication state in either case, PERMIT or DENY.<br>
     *         Otherwise, when the array contains one or more role names access
     *         is permitted if the user a member of at least one of the named
     *         roles. The EmptyRoleSemantic is not applied in this case.
     */
    String[] rolesAllowed() default {};
}
