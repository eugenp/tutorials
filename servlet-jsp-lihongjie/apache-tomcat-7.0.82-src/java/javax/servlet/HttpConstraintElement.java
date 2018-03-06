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
package javax.servlet;

import java.util.ResourceBundle;

import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

/**
 * @since Servlet 3.0
 * TODO SERVLET3 - Add comments
 */
public class HttpConstraintElement {
    
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static final ResourceBundle lStrings =
        ResourceBundle.getBundle(LSTRING_FILE);

    private final EmptyRoleSemantic emptyRoleSemantic;// = EmptyRoleSemantic.PERMIT;
    private final TransportGuarantee transportGuarantee;// = TransportGuarantee.NONE;
    private final String[] rolesAllowed;// = new String[0];
    
    /**
     * Default constraint is permit with no transport guarantee.
     */
    public HttpConstraintElement() {
        // Default constructor
        this.emptyRoleSemantic = EmptyRoleSemantic.PERMIT;
        this.transportGuarantee = TransportGuarantee.NONE;
        this.rolesAllowed = new String[0];
    }
    
    /**
     * Convenience constructor for {@link EmptyRoleSemantic#DENY}.
     * 
     */
    public HttpConstraintElement(EmptyRoleSemantic emptyRoleSemantic) {
        this.emptyRoleSemantic = emptyRoleSemantic;
        this.transportGuarantee = TransportGuarantee.NONE;
        this.rolesAllowed = new String[0];
    }
    
    /**
     * Convenience constructor to specify transport guarantee and/or roles.
     */
    public HttpConstraintElement(TransportGuarantee transportGuarantee,
            String... rolesAllowed) {
        this.emptyRoleSemantic = EmptyRoleSemantic.PERMIT;
        this.transportGuarantee = transportGuarantee;
        this.rolesAllowed = rolesAllowed;
    }

    /**
     * 
     * @param emptyRoleSemantic
     * @param transportGuarantee
     * @param rolesAllowed
     * @throws IllegalArgumentException if roles are specified when DENY is used
     */
    public HttpConstraintElement(EmptyRoleSemantic emptyRoleSemantic,
            TransportGuarantee transportGuarantee, String... rolesAllowed) {
        if (rolesAllowed != null && rolesAllowed.length > 0 &&
                EmptyRoleSemantic.DENY.equals(emptyRoleSemantic)) {
            throw new IllegalArgumentException(lStrings.getString(
                    "httpConstraintElement.invalidRolesDeny"));
        }
        this.emptyRoleSemantic = emptyRoleSemantic;
        this.transportGuarantee = transportGuarantee;
        this.rolesAllowed = rolesAllowed;
    }
    
    public EmptyRoleSemantic getEmptyRoleSemantic() {
        return emptyRoleSemantic;
    }
    
    public TransportGuarantee getTransportGuarantee() {
        return transportGuarantee;
    }
    
    public String[] getRolesAllowed() {
        return rolesAllowed;
    }
}