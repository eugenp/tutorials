package com.baeldung.oauth2.authorization.server.api;

import com.baeldung.oauth2.authorization.server.handler.AuthorizationGrantTypeHandler;
import com.baeldung.oauth2.authorization.server.model.AppDataRepository;
import com.baeldung.oauth2.authorization.server.model.AuthorizationCode;
import com.baeldung.oauth2.authorization.server.model.Client;
import com.baeldung.oauth2.authorization.server.model.User;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.literal.NamedLiteral;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(loginPage = "/login.jsp", errorPage = "/login.jsp")
)
@RolesAllowed("USER")
@RequestScoped
@Path("authorize")
public class AuthorizationEndpoint {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AppDataRepository appDataRepository;

    @Inject
    Instance<AuthorizationGrantTypeHandler> authorizationGrantTypeHandlers;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response doGet(@Context HttpServletRequest request,
                          @Context HttpServletResponse response,
                          @Context UriInfo uriInfo) throws ServletException, IOException {
        MultivaluedMap<String, String> params = uriInfo.getQueryParameters();
        Principal principal = securityContext.getCallerPrincipal();

        //error about redirect_uri && client_id ==> forward user, thus to error.jsp.
        //otherwise ==> sendRedirect redirect_uri?error=error&error_description=error_description
        //1. client_id
        String clientId = params.getFirst("client_id");
        if (clientId == null || clientId.isEmpty()) {
            return informUserAboutError(request, response, "Invalid client_id :" + clientId);
        }
        Client client = appDataRepository.getClient(clientId);
        if (client == null) {
            return informUserAboutError(request, response, "Invalid client_id :" + clientId);
        }
        //2. Client Authorized Grant Type
        String clientError = "";
        if (client.getAuthorizedGrantTypes() != null && !client.getAuthorizedGrantTypes().contains("authorization_code")) {
            return informUserAboutError(request, response, "Authorization Grant type, authorization_code, is not allowed for this client :" + clientId);
        }

        //3. redirectUri
        String redirectUri = params.getFirst("redirect_uri");
        if (client.getRedirectUri() != null && !client.getRedirectUri().isEmpty()) {
            if (redirectUri != null && !redirectUri.isEmpty() && !client.getRedirectUri().equals(redirectUri)) {
                //sould be in the client.redirectUri
                return informUserAboutError(request, response, "redirect_uri is pre-registred and should match");
            }
            redirectUri = client.getRedirectUri();
            params.putSingle("resolved_redirect_uri", redirectUri);
        } else {
            if (redirectUri == null || redirectUri.isEmpty()) {
                return informUserAboutError(request, response, "redirect_uri is not pre-registred and should be provided");
            }
            params.putSingle("resolved_redirect_uri", redirectUri);
        }
        request.setAttribute("client", client);

        //4. response_type
        String responseType = params.getFirst("response_type");
        if (!"code".equals(responseType) && !"token".equals(responseType)) {
            //error = "invalid_grant :" + responseType + ", response_type params should be code or token:";
            //return informUserAboutError(error);
        }

        //Save params in session
        request.getSession().setAttribute("ORIGINAL_PARAMS", params);

        //4.scope: Optional
        String requestedScope = request.getParameter("scope");
        if (requestedScope == null || requestedScope.isEmpty()) {
            requestedScope = client.getScope();
        }
        User user = appDataRepository.getUser(principal.getName());
        String allowedScopes = checkUserScopes(user.getScopes(), requestedScope);
        request.setAttribute("scopes", allowedScopes);

        request.getRequestDispatcher("/authorize.jsp").forward(request, response);
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response doPost(@Context HttpServletRequest request,
                           @Context HttpServletResponse response,
                           MultivaluedMap<String, String> params) throws Exception {
        MultivaluedMap<String, String> originalParams = (MultivaluedMap<String, String>) request.getSession().getAttribute("ORIGINAL_PARAMS");
        if (originalParams == null) {
            return informUserAboutError(request, response, "No pending authorization request.");
        }
        String redirectUri = originalParams.getFirst("resolved_redirect_uri");
        StringBuilder sb = new StringBuilder(redirectUri);

        String approvalStatus = params.getFirst("approval_status");
        if ("NO".equals(approvalStatus)) {
            URI location = UriBuilder.fromUri(sb.toString())
                    .queryParam("error", "User doesn't approved the request.")
                    .queryParam("error_description", "User doesn't approved the request.")
                    .build();
            return Response.seeOther(location).build();
        }
        //==> YES
        List<String> approvedScopes = params.get("scope");
        if (approvedScopes == null || approvedScopes.isEmpty()) {
            URI location = UriBuilder.fromUri(sb.toString())
                    .queryParam("error", "User doesn't approved the request.")
                    .queryParam("error_description", "User doesn't approved the request.")
                    .build();
            return Response.seeOther(location).build();
        }

        String responseType = originalParams.getFirst("response_type");
        String clientId = originalParams.getFirst("client_id");
        if ("code".equals(responseType)) {
            String userId = securityContext.getCallerPrincipal().getName();
            AuthorizationCode authorizationCode = new AuthorizationCode();
            authorizationCode.setClientId(clientId);
            authorizationCode.setUserId(userId);
            authorizationCode.setApprovedScopes(String.join(" ", approvedScopes));
            authorizationCode.setExpirationDate(LocalDateTime.now().plusMinutes(10));
            authorizationCode.setRedirectUri(redirectUri);
            appDataRepository.save(authorizationCode);
            String code = authorizationCode.getCode();
            sb.append("?code=").append(code);
        } else {
            //Implicit: responseType=token
            AuthorizationGrantTypeHandler authorizationGrantTypeHandler = authorizationGrantTypeHandlers.select(NamedLiteral.of("implicit")).get();
            JsonObject tokenResponse = authorizationGrantTypeHandler.createAccessToken(clientId, params);
            sb.append("#access_token=").append(tokenResponse.getString("access_token"))
                    .append("&token_type=").append(tokenResponse.getString("token_type"))
                    .append("&scope=").append(tokenResponse.getString("scope"));
        }
        String state = originalParams.getFirst("state");
        if (state != null) {
            sb.append("&state=").append(state);
        }
        return Response.seeOther(UriBuilder.fromUri(sb.toString()).build()).build();
    }

    private String checkUserScopes(String userScopes, String requestedScope) {
        Set<String> allowedScopes = new LinkedHashSet<>();
        Set<String> rScopes = new HashSet(Arrays.asList(requestedScope.split(" ")));
        Set<String> uScopes = new HashSet(Arrays.asList(userScopes.split(" ")));
        for (String scope : uScopes) {
            if (rScopes.contains(scope)) allowedScopes.add(scope);
        }
        return String.join(" ", allowedScopes);
    }

    private Response informUserAboutError(HttpServletRequest request, HttpServletResponse response, String error) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
        return null;
    }
}
