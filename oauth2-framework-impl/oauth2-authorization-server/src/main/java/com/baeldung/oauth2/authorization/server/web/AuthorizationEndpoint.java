//package com.baeldung.security.oauth2.server.web;
//
//import AuthorizationCode;
//import Client;
//import User;
//import com.baeldung.security.oauth2.server.service.AuthCodeService;
//
//import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.security.enterprise.SecurityContext;
//import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
//import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.HttpConstraint;
//import javax.servlet.annotation.ServletSecurity;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.security.Principal;
//import java.util.*;
//
///**
// * 1. GET http://localhost:8080/app/ (302)
// * 2. GET http://localhost:8080/uaa/authorize?client_id=app&redirect_uri=http://localhost:8080/app/&response_type=code&state=A123 (302)
// * 3. GET http://localhost:8080/uaa/login (200) with initial request as hidden input
// * 4. POST http://localhost:8080/uaa/login (username, password, initial client request) (302)
// * 5. GET http://localhost:8080/uaa/authorize?client_id=app&redirect_uri=http://localhost:8080/app/&response_type=code&state=A123 (200)
// * 7. POST http://localhost:8080/uaa/authorize?client_id=app&redirect_uri=http://localhost:8080/app/&response_type=code&state=A123 (302)
// * 8. GET http://localhost:8080/app/?code=rkWijq06mL&state=A123 (200)
// */
///*
//
//Query Params:
//        client_id: app
//        redirect_uri: http://localhost:8080/app/
//        response_type: code
//        state: A123
//
//        ==> GET user login WITH client request as hidden input:
//                    <input name="form_redirect_uri"
//                   type="hidden"
//                   value="http://localhost:8080/uaa/oauth/authorize?client_id=app&amp;redirect_uri=http://localhost:8080/app/&amp;response_type=code&amp;state=A123"/>
//
//        ==> After user login ==> Initial client request
//        ==> gen code
//        == redirect to redirect uri + params code & state : 302, location : http://localhost:8080/app/?code=w6A0YQFzzg&state=A123
//*/
//
////authorize?client_id=app&redirect_uri=http://localhost:8080/app/&response_type=code&state=A123
////http://localhost:9080/authorize?response_type=code&client_id=client_id_1&redirect_uri=http://localhost:9080/app&state=A123
//
////@RequestScoped
//@FormAuthenticationMechanismDefinition(
//        loginToContinue = @LoginToContinue(
//                loginPage = "/login-servlet",
//                errorPage = "/login-error-servlet"
//        )
//)
//@WebServlet({"/authorize"})
//@ServletSecurity(@HttpConstraint(rolesAllowed = "user"))
////@Stateless
//@RequestScoped
//public class AuthorizationEndpoint extends HttpServlet {
//
//    private static final List<String> authorizedResponseTypes = Arrays.asList("code", "token");
//
//    @Inject
//    private SecurityContext securityContext;
//
//    @PersistenceContext(name = "jpa-oauth2-pu")
//    private EntityManager entityManager;
//
//    @EJB
//    private AuthCodeService authCodeService;
//
//    //HTTP GET IS A MUST, POST IS OPTIONAL
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        String error = "";
//
//        //1. User Authentication
//        Principal principal = securityContext.getCallerPrincipal();
//
//        //2. Check for a valid client_id
//        String clientId = request.getParameter("client_id");
//        if (clientId == null) {
//            request.setAttribute("error", "The client " + clientId + " doesn't exist.");
//        }
//        request.setAttribute("clientId", clientId);
//        Client client = entityManager.find(Client.class, clientId);
//        if (client == null) {
//            request.setAttribute("error", "The client " + clientId + " doesn't exist.");
//        }
//
//        //3. check for a valid response_type
//        String responseType = request.getParameter("response_type");
//        if (!authorizedResponseTypes.contains(responseType)) {
//            error = "invalid_grant :" + responseType + ", response_type params should be one of :" + authorizedResponseTypes;
//            request.setAttribute("error", error);
//            request.getRequestDispatcher("/error.jsp")
//                    .forward(request, response);
//        }
//
//        //4. Optional redirect_uri, if provided should match
//        String redirectUri = request.getParameter("redirect_uri");
//        checkRedirectUri(client, redirectUri);
//
//        //save params
//        String currentUri = request.getRequestURI();
//        request.setAttribute("post_redirect_uri", currentUri);
//
//        String state = request.getParameter("state");
//        Map<String, String> requestMap = new HashMap<>();
//        requestMap.put("response_type", responseType);
//        requestMap.put("client_id", clientId);
//        requestMap.put("redirect_uri", redirectUri);
//        requestMap.put("state", state);
//        request.setAttribute("requestMap", requestMap);
//
//        //5.scope: Optional
//        String requestedScope = request.getParameter("scope");
//        if (requestedScope.isEmpty()) {
//            requestedScope = client.getScope();
//        }
//        //requestedScope should be a subset of the client scope: clientScopes.containsAll(requestedScopes)
//        //checkRequestedScope(requestedScope, client.getScope());
//
//        //sub set of user scope
//        //allowed scope by the user
//
//        User user = entityManager.find(User.class, principal.getName());
//        request.setAttribute("scopes", requestedScope);
//
//
//        forward("/authorize.jsp", request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String clientId = request.getParameter("client_id");
//
//        String responseType = request.getParameter("response_type");
//        if (!authorizedResponseTypes.contains(responseType)) {
//            String error = "invalid_grant :" + responseType + ", response_type params should be one of :" + authorizedResponseTypes;
//            request.setAttribute("error", error);
//            forward("/error.jsp", request, response);
//        }
//
//        Client client = entityManager.find(Client.class, clientId);
//        Objects.requireNonNull(client);
//
//        String userId = securityContext.getCallerPrincipal().getName();
//        AuthorizationCode authorizationCode = new AuthorizationCode();
//        authorizationCode.setClientId(clientId);
//        authorizationCode.setUserId(userId);
//        String redirectUri = request.getParameter("redirect_uri");
//        authorizationCode.setRedirectUri(redirectUri);
//
//        redirectUri = checkRedirectUri(client, redirectUri);
//
//        String[] scope = request.getParameterValues("scope");
//        if (scope == null) {
//            request.setAttribute("error", "User doesn't approved any scope");
//            forward("/error.jsp", request, response);
//        }
//
//        String approvedScopes = String.join(" ", scope);
//        authorizationCode.setApprovedScopes(approvedScopes);
//
//        //entityManager.persist(authorizationCode);
//        authCodeService.save(authorizationCode);
//        String code = authorizationCode.getCode();
//
//        StringBuilder sb = new StringBuilder(redirectUri);
//        sb.append("?code=").append(code);
//
//        //If the client send a state, Send it back
//        String state = request.getParameter("state");
//        if (state != null) {
//            sb.append("&state=").append(state);
//        }
//        response.sendRedirect(sb.toString());
//    }
//
//    private String checkRedirectUri(Client client, String redirectUri) {
//        //redirect uri
//        if (redirectUri == null) {
//            //erreur: param redirect_uri && client redirect_uri don't match.
//            redirectUri = client.getRedirectUri();
//            if (redirectUri == null) {
//                throw new IllegalStateException("redirectUri shloud be not null, unless a registred client have a redirect_uri.");
//            }
//        } else if (!redirectUri.equals(client.getRedirectUri())) {
//            throw new IllegalStateException("request redirectUri and client registred redirect_uri should match.");
//        }
//        return redirectUri;
//    }
//
//    private void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher(path)
//                .forward(request, response);
//    }
//}
