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
package org.apache.catalina.realm;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.startup.TesterMapRealm;
import org.apache.tomcat.unittest.TesterContext;
import org.apache.tomcat.unittest.TesterRequest;
import org.apache.tomcat.unittest.TesterResponse;

public class TestRealmBase {

    private static final String USER1 = "user1";
    private static final String USER2 = "user2";
    private static final String USER99 = "user99";
    private static final String PWD = "password";
    public static final String ROLE1 = "role1";
    private static final String ROLE2 = "role2";
    private static final String ROLE3 = "role3";
    private static final String ROLE99 = "role99";
    
    // All digested passwords are the digested form of "password"
    private static final String PWD_MD5 = "5f4dcc3b5aa765d61d8327deb882cf99";
    private static final String PWD_SHA = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";
    private static final String PWD_MD5_PREFIX =
            "{MD5}X03MO1qnZdYdgyfeuILPmQ==";
    private static final String PWD_SHA_PREFIX =
            "{SHA}W6ph5Mm5Pz8GgiULbPgzG37mj9g=";
    // Salt added to "password" is "salttoprotectpassword"
    private static final String PWD_SSHA_PREFIX =
            "{SSHA}oFLhvfQVqFykEWu8v1pPE6nN0QRzYWx0dG9wcm90ZWN0cGFzc3dvcmQ=";

    private static final String ROLE_ALL_ROLES="*";


    @Test
    public void testDigestMD5() throws Exception {
        doTestDigestDigestPasswords(PWD, "MD5", PWD_MD5);
    }

    @Test
    public void testDigestSHA() throws Exception {
        doTestDigestDigestPasswords(PWD, "SHA", PWD_SHA);
    }

    @Test
    public void testDigestMD5Prefix() throws Exception {
        doTestDigestDigestPasswords(PWD, "MD5", PWD_MD5_PREFIX);
    }

    @Test
    public void testDigestSHAPrefix() throws Exception {
        doTestDigestDigestPasswords(PWD, "SHA", PWD_SHA_PREFIX);
    }

    @Test
    public void testDigestSSHAPrefix() throws Exception {
        doTestDigestDigestPasswords(PWD, "SHA", PWD_SSHA_PREFIX);
    }

    private void doTestDigestDigestPasswords(String password,
            String digest, String digestedPassword) throws Exception {
        Context context = new TesterContext();
        TesterMapRealm realm = new TesterMapRealm();
        realm.setContainer(context);
        realm.setDigest(digest);
        realm.start();

        realm.addUser(USER1, digestedPassword);

        Principal p = realm.authenticate(USER1, password);

        Assert.assertNotNull(p);
        Assert.assertEquals(USER1, p.getName());
    }

    @Test
    public void testUserWithSingleRole() throws IOException {
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        // Configure this test
        userRoles.add(ROLE1);
        constraintRoles.add(ROLE1);
        applicationRoles.add(ROLE1);

        doRoleTest(userRoles, constraintRoles, applicationRoles, true);
    }


    @Test
    public void testUserWithNoRoles() throws IOException {
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        // Configure this test
        constraintRoles.add(ROLE1);
        applicationRoles.add(ROLE1);

        doRoleTest(userRoles, constraintRoles, applicationRoles, false);
    }


    @Test
    public void testUserWithSingleRoleAndAllRoles() throws IOException {
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        // Configure this test
        userRoles.add(ROLE1);
        applicationRoles.add(ROLE1);
        constraintRoles.add(ROLE_ALL_ROLES);

        doRoleTest(userRoles, constraintRoles, applicationRoles, true);
    }


    @Test
    public void testUserWithoutNoRolesAndAllRoles() throws IOException {
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        // Configure this test
        constraintRoles.add(ROLE_ALL_ROLES);
        applicationRoles.add(ROLE1);

        doRoleTest(userRoles, constraintRoles, applicationRoles, false);
    }


    @Test
    public void testAllRolesWithNoAppRole() throws IOException {
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        // Configure this test
        userRoles.add(ROLE1);
        constraintRoles.add(ROLE_ALL_ROLES);

        doRoleTest(userRoles, constraintRoles, applicationRoles, false);
    }


    @Test
    public void testNoAuthConstraint() throws IOException {
        // No auth constraint == allow access for all
        List<String> applicationRoles = new ArrayList<String>();

        doRoleTest(null, null, applicationRoles, true);
    }


    /*
     * The combining constraints tests are based on the scenarios described in
     * section
     */

    @Test
    public void testCombineConstraints01() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // User role is in first constraint
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        userRoles.add(ROLE1);
        constraintOneRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE2);
        applicationRoles.add(ROLE1);
        applicationRoles.add(ROLE2);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, true);
    }


    @Test
    public void testCombineConstraints02() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // User role is in last constraint
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        userRoles.add(ROLE2);
        constraintOneRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE2);
        applicationRoles.add(ROLE1);
        applicationRoles.add(ROLE2);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, true);
    }


    @Test
    public void testCombineConstraints03() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // User role is not in any constraint
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        userRoles.add(ROLE3);
        constraintOneRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE2);
        applicationRoles.add(ROLE1);
        applicationRoles.add(ROLE2);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, false);
    }


    @Test
    public void testCombineConstraints04() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // * is any app role
        // User role is not in any constraint
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        userRoles.add(ROLE99);
        constraintOneRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE_ALL_ROLES);
        applicationRoles.add(ROLE2);
        applicationRoles.add(ROLE3);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, false);
    }


    @Test
    public void testCombineConstraints05() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // * is any app role
        // User role is a non-app constraint role
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        userRoles.add(ROLE1);
        constraintOneRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE_ALL_ROLES);
        applicationRoles.add(ROLE2);
        applicationRoles.add(ROLE3);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, true);
    }


    @Test
    public void testCombineConstraints06() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // * is any app role
        // User role is an app role
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        userRoles.add(ROLE2);
        constraintOneRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE_ALL_ROLES);
        applicationRoles.add(ROLE2);
        applicationRoles.add(ROLE3);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, true);
    }


    @Test
    public void testCombineConstraints07() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // * is any app role
        // User has no role
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        constraintOneRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE_ALL_ROLES);
        applicationRoles.add(ROLE2);
        applicationRoles.add(ROLE3);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, false);
    }


    @Test
    public void testCombineConstraints12() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // Constraint without role or implied role permits unauthenticated users
        // User is not authenticated
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        constraintTwoRoles.add(ROLE1);
        applicationRoles.add(ROLE1);

        doRoleTest(null, null, constraintTwoRoles,
                applicationRoles, true);
    }


    @Test
    public void testCombineConstraints13() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // Constraint without role or implied role permits unauthenticated users
        // User is not authenticated
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        constraintTwoRoles.add(ROLE_ALL_ROLES);
        applicationRoles.add(ROLE1);

        doRoleTest(null, null, constraintTwoRoles,
                applicationRoles, true);
    }


    @Test
    public void testCombineConstraints15() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // Constraint with empty auth section prevents all access
        // User has matching constraint role
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        userRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE1);
        applicationRoles.add(ROLE1);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, false);
    }


    @Test
    public void testCombineConstraints16() throws IOException {
        // Allowed roles should be the union of the roles in the constraints
        // Constraint with empty auth section prevents all access
        // User has matching role
        List<String> userRoles = new ArrayList<String>();
        List<String> constraintOneRoles = new ArrayList<String>();
        List<String> constraintTwoRoles = new ArrayList<String>();
        List<String> applicationRoles = new ArrayList<String>();

        userRoles.add(ROLE1);
        constraintTwoRoles.add(ROLE_ALL_ROLES);
        applicationRoles.add(ROLE1);

        doRoleTest(userRoles, constraintOneRoles, constraintTwoRoles,
                applicationRoles, false);
    }


    /**
     * @param userRoles         <code>null</code> tests unauthenticated access
     *                          otherwise access is tested with an authenticated
     *                          user with the listed roles
     * @param constraintRoles   <code>null</code> is equivalent to no auth
     *                          constraint whereas an empty list is equivalent
     *                          to an auth constraint that defines no roles.
     */
    private void doRoleTest(List<String> userRoles,
            List<String> constraintRoles, List<String> applicationRoles,
            boolean expected) throws IOException {

        List<String> constraintTwoRoles = new ArrayList<String>();
        constraintTwoRoles.add(ROLE99);
        doRoleTest(userRoles, constraintRoles, constraintTwoRoles,
                applicationRoles, expected);
    }


    private void doRoleTest(List<String> userRoles,
            List<String> constraintOneRoles, List<String> constraintTwoRoles,
            List<String> applicationRoles, boolean expected)
            throws IOException {

        TesterMapRealm mapRealm = new TesterMapRealm();

        // Configure the security constraints for the resource
        SecurityConstraint constraintOne = new SecurityConstraint();
        if (constraintOneRoles != null) {
            constraintOne.setAuthConstraint(true);
            for (String constraintRole : constraintOneRoles) {
                constraintOne.addAuthRole(constraintRole);
            }
        }
        SecurityConstraint constraintTwo = new SecurityConstraint();
        if (constraintTwoRoles != null) {
            constraintTwo.setAuthConstraint(true);
            for (String constraintRole : constraintTwoRoles) {
                constraintTwo.addAuthRole(constraintRole);
            }
        }
        SecurityConstraint[] constraints =
                new SecurityConstraint[] { constraintOne, constraintTwo };

        // Set up the mock request and response
        Request request = new Request();
        Response response = new TesterResponse();
        Context context = new TesterContext();
        for (String applicationRole : applicationRoles) {
            context.addSecurityRole(applicationRole);
        }
        request.setContext(context);

        // Set up an authenticated user
        // Configure the users in the Realm
        if (userRoles != null) {
            GenericPrincipal gp = new GenericPrincipal(USER1, PWD, userRoles);
            request.setUserPrincipal(gp);
        }

        // Check if user meets constraints
        boolean result = mapRealm.hasResourcePermission(
                request, response, constraints, null);

        Assert.assertEquals(Boolean.valueOf(expected), Boolean.valueOf(result));
    }


    /**
     * This test case covers the special case in section 13.4.1 of the Servlet
     * 3.1 specification for {@link javax.servlet.annotation.HttpConstraint}.
     */
    @Test
    public void testHttpConstraint() throws IOException {
        // Get the annotation from the test case
        Class<TesterServletSecurity01> clazz = TesterServletSecurity01.class;
        ServletSecurity servletSecurity =
                clazz.getAnnotation(ServletSecurity.class);

        // Convert the annotation into constraints
        ServletSecurityElement servletSecurityElement =
                new ServletSecurityElement(servletSecurity);
        SecurityConstraint[] constraints =
                SecurityConstraint.createConstraints(
                        servletSecurityElement, "/*");

        // Create a separate constraint that covers DELETE
        SecurityConstraint deleteConstraint = new SecurityConstraint();
        deleteConstraint.addAuthRole(ROLE1);
        SecurityCollection deleteCollection = new SecurityCollection();
        deleteCollection.addMethod("DELETE");
        deleteCollection.addPattern("/*");
        deleteConstraint.addCollection(deleteCollection);

        TesterMapRealm mapRealm = new TesterMapRealm();

        // Set up the mock request and response
        TesterRequest request = new TesterRequest();
        Response response = new TesterResponse();
        Context context = request.getContext();
        context.addSecurityRole(ROLE1);
        context.addSecurityRole(ROLE2);
        request.setContext(context);

        // Create the principals
        List<String> userRoles1 = new ArrayList<String>();
        userRoles1.add(ROLE1);
        GenericPrincipal gp1 = new GenericPrincipal(USER1, PWD, userRoles1);

        List<String> userRoles2 = new ArrayList<String>();
        userRoles2.add(ROLE2);
        GenericPrincipal gp2 = new GenericPrincipal(USER2, PWD, userRoles2);

        List<String> userRoles99 = new ArrayList<String>();
        GenericPrincipal gp99 = new GenericPrincipal(USER99, PWD, userRoles99);

        // Add the constraints to the context
        for (SecurityConstraint constraint : constraints) {
            context.addConstraint(constraint);
        }
        context.addConstraint(deleteConstraint);

        // All users should be able to perform a GET
        request.setMethod("GET");

        SecurityConstraint[] constraintsGet =
                mapRealm.findSecurityConstraints(request, context);

        request.setUserPrincipal(null);
        Assert.assertTrue(mapRealm.hasResourcePermission(
                request, response, constraintsGet, null));
        request.setUserPrincipal(gp1);
        Assert.assertTrue(mapRealm.hasResourcePermission(
                request, response, constraintsGet, null));
        request.setUserPrincipal(gp2);
        Assert.assertTrue(mapRealm.hasResourcePermission(
                request, response, constraintsGet, null));
        request.setUserPrincipal(gp99);
        Assert.assertTrue(mapRealm.hasResourcePermission(
                request, response, constraintsGet, null));

        // Only user1 should be able to perform a POST as only that user has
        // role1.
        request.setMethod("POST");

        SecurityConstraint[] constraintsPost =
                mapRealm.findSecurityConstraints(request, context);

        request.setUserPrincipal(null);
        Assert.assertFalse(mapRealm.hasResourcePermission(
                request, response, constraintsPost, null));
        request.setUserPrincipal(gp1);
        Assert.assertTrue(mapRealm.hasResourcePermission(
                request, response, constraintsPost, null));
        request.setUserPrincipal(gp2);
        Assert.assertFalse(mapRealm.hasResourcePermission(
                request, response, constraintsPost, null));
        request.setUserPrincipal(gp99);
        Assert.assertFalse(mapRealm.hasResourcePermission(
                request, response, constraintsPost, null));

        // Only users with application roles (role1 or role2 so user1 or user2)
        // should be able to perform a PUT.
        request.setMethod("PUT");

        SecurityConstraint[] constraintsPut =
                mapRealm.findSecurityConstraints(request, context);

        request.setUserPrincipal(null);
        Assert.assertFalse(mapRealm.hasResourcePermission(
                request, response, constraintsPut, null));
        request.setUserPrincipal(gp1);
        Assert.assertTrue(mapRealm.hasResourcePermission(
                request, response, constraintsPut, null));
        request.setUserPrincipal(gp2);
        Assert.assertTrue(mapRealm.hasResourcePermission(
                request, response, constraintsPut, null));
        request.setUserPrincipal(gp99);
        Assert.assertFalse(mapRealm.hasResourcePermission(
                request, response, constraintsPut, null));

        // Only user1 should be able to perform a DELETE as only that user has
        // role1.
        request.setMethod("DELETE");

        SecurityConstraint[] constraintsDelete =
                mapRealm.findSecurityConstraints(request, context);

        request.setUserPrincipal(null);
        Assert.assertFalse(mapRealm.hasResourcePermission(
                request, response, constraintsDelete, null));
        request.setUserPrincipal(gp1);
        Assert.assertTrue(mapRealm.hasResourcePermission(
                request, response, constraintsDelete, null));
        request.setUserPrincipal(gp2);
        Assert.assertFalse(mapRealm.hasResourcePermission(
                request, response, constraintsDelete, null));
        request.setUserPrincipal(gp99);
        Assert.assertFalse(mapRealm.hasResourcePermission(
                request, response, constraintsDelete, null));
    }
}
