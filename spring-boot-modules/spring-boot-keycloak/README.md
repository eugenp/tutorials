## Spring Boot Keycloak

This module contains articles about Keycloak in Spring Boot projects.

## Relevant articles:
- [A Quick Guide to OAuth2 With Spring Boot And Keycloak](https://www.baeldung.com/spring-boot-keycloak)
- [Customizing the Login Page for Keycloak](https://www.baeldung.com/keycloak-custom-login-page)
- [Keycloak User Self-Registration](https://www.baeldung.com/keycloak-user-registration)
- [Customizing Themes for Keycloak](https://www.baeldung.com/spring-keycloak-custom-themes)
- [Securing SOAP Web Services With Keycloak](https://www.baeldung.com/soap-keycloak)

<h2 id="overview"><strong>1. Overview</strong></h2>
In this tutorial, we'll discuss the basics of setting up a Keycloak server and connecting a Spring Boot application using Spring Security OAuth2.0.

[fr-box]
<h2 id="whatiskeycloak"><strong>2. What Is Keycloak?</strong></h2>
<strong><a href="http://www.keycloak.org/">Keycloak</a> is an open-source OpenID Provider. It is a widely used <a href="https://www.baeldung.com/cs/iam-security">Identity and Access Management</a> solution for modern applications and services.</strong>

Keycloak offers features such as:
<ul>
 	<li>Single-Sign-On (SSO)</li>
 	<li>Identity Brokering and Social Login</li>
 	<li>User Federation</li>
 	<li>Admin Console UI</li>
 	<li>Account Management Console UI</li>
 	<li>Admin REST API</li>
</ul>
In our tutorial, we'll use the Admin Console of Keycloak for setting up and connecting to Spring Boot using the Spring Security OAuth2.0.
<h2 id="keycloakserver"><strong>3. Setting Up a Keycloak Server</strong></h2>
In this section, we will set up and configure the Keycloak server.
<h3 id="downloading-keycloak"><strong>3.1. Downloading and Installing Keycloak</strong></h3>
There are several distributions to choose from. The easiest two options on a developer desktop are probably the standalone and Docker image distributions.

We may download the standalone distribution <a href="https://github.com/keycloak/keycloak/releases/download/25.0.1/keycloak-25.0.1.zip">from the official website</a>. After that, all we need is to unzip the downloaded archive and run the <em>bin/kc.sh</em> script with the <em>start-dev</em> argument. Within a shell terminal (on Windows, we could use Git Bash for instance), this would be:
<pre><code class="language-shell">unzip keycloak-25.0.1.zip
export KEYCLOAK_ADMIN=admin
export KEYCLOAK_ADMIN_PASSWORD=admin
sh ./keycloak-25.0.1/bin/kc.sh start-dev --import-realm
</code></pre>
When preferring to run a Docker container, we may use a compose file similar to the one in the companion project:
<pre><code class="language-yaml">services:
  keycloak:
    container_name: baeldung-keycloak.openid-provider
    image: quay.io/keycloak/keycloak:25.0.1
    command:
    - start-dev
    - --import-realm
    ports:
    - 8080:8080
    volumes:
      - ./keycloak/:/opt/keycloak/data/import/
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: ${KEYCLOAK_ADMIN_PASSWORD}
      KC_HTTP_PORT: 8080
      KC_HOSTNAME_URL: http://localhost:8080
      KC_HOSTNAME_ADMIN_URL: http://localhost:8080
      KC_HOSTNAME_STRICT_BACKCHANNEL: true
      #KC_HOSTNAME_DEBUG: true
      KC_HTTP_RELATIVE_PATH: /
      KC_HTTP_ENABLED: true
      KC_HEALTH_ENABLED: true
      KC_METRICS_ENABLED: true
      #KC_LOG_LEVEL: DEBUG
    extra_hosts:
    - "host.docker.internal:host-gateway"
    healthcheck:
      test: ['CMD-SHELL', '[ -f /tmp/HealthCheck.java ] || echo "public class HealthCheck { public static void main(String[] args) throws java.lang.Throwable { System.exit(java.net.HttpURLConnection.HTTP_OK == ((java.net.HttpURLConnection)new java.net.URL(args[0]).openConnection()).getResponseCode() ? 0 : 1); } }" &gt; /tmp/HealthCheck.java &amp;&amp; java /tmp/HealthCheck.java http://localhost:8080/auth/health/live']
      interval: 5s
      timeout: 5s
      retries: 20</code></pre>
We would then start the container with the following shell commands:
<pre><code class="language-shell">export KEYCLOAK_ADMIN_PASSWORD=admin
docker compose up</code></pre>
After running either of these commands, Keycloak will start its services. Once we see a line containing <em>Keycloak 25.0.1 [...] started</em>, we'll know its start-up is complete.

Now let's open a browser and visit <em><a href="http://localhost:8080">http://localhost:8080</a>. </em><strong>Because we set the <em>KEYCLOAK_ADMIN</em> and <em>KEYCLOAK_ADMIN_PASSWORD</em> environment variables, the admin account is already configured.</strong>
<h3 id="create-realm"><strong>3.2. Using the Companion Project Realm</strong></h3>
The companion project contains a <em>keycloak/baeldung-keycloak-realm.json</em> file which is an export of a realm with the confidential client, realm role, and users that we'll configure in the remaining of this section. By importing this file in Keycloak, we could move directly to section 4.

If running the standalone distribution, we'd copy the JSON file to <em>keycloak-25.0.1/data/import/</em> and restart Keycloak with the command we already used.

If running a Docker container with the compose file above, we'd copy the JSON file to ./<em>keycloak/</em> relative to the compose file, delete the container, and run the compose file again.

Alternatively, we may follow the remaining of this section 3. to create the realm, confidential client, realm role, and users.
<h3 id="create-realm"><strong>3.3. Creating a Realm</strong></h3>
Let's <strong>navigate to the upper left corner to click the<em> Create Realm</em> button:</strong>

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/create-realm.png"><img class="alignnone size-medium wp-image-153614" src="https://www.baeldung.com/wp-content/uploads/2017/11/create-realm-300x221.png" alt="create realm" /></a>

On the next screen, <strong>let's add a new realm called <em>baeldung-keycloak</em>:</strong>

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/create-realm-name.png"><img class="alignnone wp-image-153615 size-full" src="https://www.baeldung.com/wp-content/uploads/2017/11/create-realm-name.png" alt="create realm name" width="1881" height="700" /></a>

After clicking the <em>Create</em> button, a new realm will be created and we'll be redirected to it. All the operations in the next sections will be performed in this new <em>baeldung-keycloak</em> realm.
<h3 id="create-client"><strong>3.3. Creating a Confidential Client to Authenticate Users</strong></h3>
Now we'll navigate to the Clients page. As we can see in the image below, Keycloak comes with Clients that are already built-in:

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/keycloak-clients-1.png"><img class="alignnone size-full wp-image-153616" src="https://www.baeldung.com/wp-content/uploads/2017/11/keycloak-clients-1.png" alt="keycloak clients" /></a>

We still need to add a new client to our application, so we'll click <em>Create</em>. <strong>We'll call the new Client <em>baeldung-keycloak-confidential:</em></strong>

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/create-client.png"><img class="alignnone size-full wp-image-153617" src="https://www.baeldung.com/wp-content/uploads/2017/11/create-client.png" alt="create client" /></a>

In the next screen, we'll ensure that <em>Client authentication</em> is enabled (this makes the client "confidential") and that only <em>Standard flow</em> is checked (authorization code &amp; refresh token flows):

Last, we need to allow redirection to the endpoint where our Spring client will be waiting for authorization codes (by default: <em>{scheme}://{host}:{port}/login/oauth2/code/{registration-id}</em>). We also set post logout redirection URI and allowed origins:

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/keycloak-redirect-uri.png"><img class="alignnone wp-image-153618" src="https://www.baeldung.com/wp-content/uploads/2017/11/keycloak-redirect-uri.png" alt="keycloak redirect uri" width="761" height="243" /></a>

Later on, we'll create a Spring Boot client application running on port 8081 with a <em>keycloak</em> registration. Hence we've used a redirect URL of <em>http://localhost:8081/login/oauth2/code/keycloak</em> above. The post-logout URL is set to the client index. Last, with * as allowed origins, we ask to allow requests from the services hosting one of <em>Valid redirect URIs</em>.

The last point to (optionally) configure is Back-Channel Logout. For that, after the client creation is validated, we should:
<ul>
 	<li>Ensure that <em>Front channel logout</em> is disabled.</li>
 	<li>Input, as <em>Backchannel logout URL</em>, the callback URL on the client. In the case of a Spring application with <em>oauth2Login</em>, it is <em>{scheme}://{host}:{port}/logout/connect/back-channel/{registration-id}</em>. In the case of the Tymeleaf app we''l build in section 5., that's <em>http://localhost:8081/logout/connect/back-channel/keycloak</em>. As Back-Channel Logout is server to server communication, we should be careful with the host when running Keycloak in a Docker container: we can't use <em>localhost</em> like we did for Valid redirect URIs or Post logout URIs (those two are interpreted by the user agent for who <em>localhost</em> is the host machine). This time, we should use <em>host.docker.internal</em> as defined in the <em>extra_hosts</em> section of the compose file, for the Keycloak container to reach the Spring application running on the host machine.</li>
 	<li>Ensure that both <em>Backchannel logout session required</em> and <em>Backchannel logout revoke offline sessions</em> are enabled.</li>
</ul>
<h3 id="create-userrole"><strong>3.4. Creating a Realm Role</strong></h3>
<strong>Within Keycloak, we may define roles, and assign it to users, for the all realm or on a per client basis. </strong>In this tutorial, we'll focus only on realm roles.

Let's navigate to the <em>Realm roles</em> page:

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/realm-roles.png"><img class="alignnone size-full wp-image-153619" src="https://www.baeldung.com/wp-content/uploads/2017/11/realm-roles.png" alt="realm roles" /></a>

Then we'll add the <em>NICE</em> role:

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/create-role.png"><img class="alignnone wp-image-153620" src="https://www.baeldung.com/wp-content/uploads/2017/11/create-role.png" alt="create role" width="439" height="310" /></a>
<h3 id="create-userrole"><strong>3.5. Creating Users and Assigning them Realm Roles</strong></h3>
Let's go to the <em>Users</em> page to add two users (one named <em>brice</em> and granted with the <em>NICE</em> role, and a second one named <em>igor</em> and granted with no realm role):

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/create-user.png"><img class="alignnone size-full wp-image-153621" src="https://www.baeldung.com/wp-content/uploads/2017/11/create-user.png" alt="create user" /></a>

We'll add a user named <em>brice:</em>

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/create-user-2.png"><img class="alignnone wp-image-153622" src="https://www.baeldung.com/wp-content/uploads/2017/11/create-user-2.png" alt="create user" width="804" height="285" /></a>

Once the user is created, a page with its details will be displayed:

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/user-details.png"><img class="alignnone size-full wp-image-153623" src="https://www.baeldung.com/wp-content/uploads/2017/11/user-details.png" alt="user details" /></a>

We can now go to the <em>Credentials</em> tab. We'll be setting the initial password to <em>secret:</em>

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/user-pass.png"><img class="alignnone wp-image-153624" src="https://www.baeldung.com/wp-content/uploads/2017/11/user-pass.png" alt="user pass" width="647" height="191" /></a>

Finally, we'll navigate to the <em>Role Mappings</em> tab. We'll be assigning the <em>NICE</em> role (mind the <em>Filter by realm roles</em> drop-down):

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/assign-role.png"><img class="alignnone wp-image-153625" src="https://www.baeldung.com/wp-content/uploads/2017/11/assign-role.png" alt="assign role" width="716" height="411" /></a>

&nbsp;
<h2 id="custom-loginpage">4. OAuth2 Reminders</h2>
Let's first remember that there are 3 kinds of software actors defined in the OAuth2 standard:
<ul>
 	<li>Authorization server: <strong>responsible for authorizing users (and clients), and issuing tokens</strong>. In this tutorial, this is Keycloak.</li>
 	<li>Client: <strong>responsible for driving a flow to get tokens from the authorization server, storing tokens, and authorizing requests to resource servers with valid tokens</strong>. When the request is sent on behalf of a user, <em>authorization code</em> and <em>refresh token</em> flows are most often used (<em>device</em> flow might be used too for devices with limited user interface). When the request is sent by a program in its own name (without the context of a user), <em>client credential</em> flow is used. For this introductory tutorial, we'll focus on the <em>authorization code</em> and <em>refresh token</em> flows.</li>
 	<li>Resource server: <strong>responsible for providing a secured access to resources</strong>. For that, it expects requests to be authorized with an access token, and checks the validity of this token (issuer, expiration, audience, etc.)</li>
</ul>
<h3>4.1. Spring OAuth2 Client with <em>oauth2Login</em></h3>
Spring Security <em>oauth2Login</em> configures authorization code and refreshes token flows, as well as an authorized client repository to store tokens.

To know how to communicate with the authorization server, a client needs configuration for a minimum of a <em>provider</em> (represents the authorization server itself) and a <em>registration</em> (describes a declared client on this authorization server).

Requests to a Spring client with <em>oauth2Login</em> are authorized with a session cookie. As a consequence, protection against CSRF attacks should always be enabled in the <em>Security(Web)FilterChain</em> bean with <em>oauth2Login</em>.
<h3>4.2. Spring OAuth2 Resource Server</h3>
Spring Security <em>oauth2ResouceServer</em> configures <em>Bearer</em> token security. It offers a choice between introspection (aka <em>opaque token</em>) and JWT decoding.

In the case of resource servers, the state is held by the token claims and sessions can be disabled. This brings two great benefits:
<ul>
 	<li>Sessions and protection against CSRF can be disabled.</li>
 	<li>Resource servers are super easy to scale (no matter to what instance a request is routed, resource owner state comes with the request)</li>
</ul>
<h3>4.3. Choosing between <em>oauth2Login</em> and <em>oauth2ResourceServer</em></h3>
As a Security(Web)FilterChain can hardly be stateful and stateless at the same time, <strong><em>oauth2Login</em> and <em>oauth2ResourceServer </em>should not stand in the same filter-chain</strong>. When configuring a filter-chain with OAuth2, we  should choose between session-based and Bearer-based requests authorization, if protection against CSRF should be enabled or not, or if anonymous access attempts to protected resources should be answered <em>302 Redirect to login</em> or <em>401 Unauthorized</em>.

As mentioned earlier, because of the great scalability, <strong>we should configure REST endpoints with a stateless resource server filter-chain.</strong>

But this requires that what sends (or routes) requests to REST APIs can fetch tokens from an authorization server, store this tokens in some sort of state, refresh it when it expire, and last authorize requests with an access token. Most frequent OAuth2 clients are:
<ul>
 	<li><strong>Server-side rendered UIs (Thymeleaf, JSF, etc.) are a perfect fit for oauth2Login</strong>: authenticates users and can be configured as a confidential OAuth2 client.</li>
 	<li><a href="https://www.baeldung.com/spring-cloud-gateway-bff-oauth2"><strong>Spring Cloud Gateway used as an OAuth2 Backend For Frontend</strong></a> in front of single-page or mobile applications: tokens are handled by the BFF, and the <em>TokenRelay=</em> filter is used on routes to resource servers to replace the session cookie with a the access token in session.</li>
 	<li><strong>Single-page and mobile applications configured as public OAuth2 clients</strong>. <a href="https://github.com/spring-projects/spring-authorization-server/issues/297#issue-896744390">This is now discouraged in favor of the OAuth2 BFF pattern for security reasons</a>, but many frontends are still configured that way.</li>
 	<li><strong>REST clients with a UI</strong> like Postman, which includes features to get tokens and authorize requests (<em>Authorization</em> tab in the case of Postman)</li>
 	<li><strong>Programmatic REST clients</strong> like <em>WebClient</em>, <em>RestClient</em>, <em>RestTemplate</em>, and <em>@FeignClient</em>.</li>
</ul>
<h2>5. Spring MVC Application with <em>oauth2Login</em></h2>
For this introductory tutorial, we'll configure a Thymeleaf application to authenticate users on Keycloak. To configure an OAuth2 BFF in systems with a single-page application (Angular, React, Vue, etc.), we might refer to <a href="https://www.baeldung.com/spring-cloud-gateway-bff-oauth2">this other article</a>.

The application we're going to build is quite simple, but yet, will demo <em>Role Based Access Control</em> (RBAC) with Keycloak. We'll expose two templates:
<ul>
 	<li>An index page displaying a <em>login</em> or <em>logout</em> button depending on the user's status. For users to log in from this index page, it must be accessible to anonymous requests.</li>
 	<li>A <em>nice</em> page accessible only to users granted with the <em>NICE</em> authority (Keycloak realm role). For a decent user experience, the link to the <em>nice</em> page will be displayed on the <em>index</em> page only if the user can access it.</li>
</ul>
<h3 id="dependencies"><strong>5.1. Dependencies</strong></h3>
<strong>Our most important dependency is Spring Boot starter for OAuth2.0 clients:</strong> <em><a href="https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-oauth2-client">spring-boot-starter-oauth2-client</a></em>. Of course, as we are creating a servlet application rendering Tymeleaf templates, we'll need <a href="https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web"><em>spring-boot-starter-web</em></a> and <a href="https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf"><em>spring-boot-starter-thymeleaf</em></a> too.

Let's add it to the <em>pom.xml</em>:
<pre><code class="language-xml">&lt;dependency&gt;
    &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
    &lt;artifactId&gt;spring-boot-starter-oauth2-client&lt;/artifactId&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
    &lt;artifactId&gt;spring-boot-starter-thymeleaf&lt;/artifactId&gt;
&lt;/dependency&gt;
&lt;dependency&gt;
    &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
    &lt;artifactId&gt;spring-boot-starter-web&lt;/artifactId&gt;
&lt;/dependency&gt;</code></pre>
Thanks to this Spring Boot starter transitive dependencies, this is enough for security at runtime. But if we want to mock identities during tests, we also need <a href="https://mvnrepository.com/artifact/org.springframework.security/spring-security-test">spring-security-test</a> with <em>test</em> scope. Testing access control in a Spring OAuth2 application is detailed in <a href="https://www.baeldung.com/spring-oauth-testing-access-control">this other article</a>.
<h3 id="keycloak-config"><strong>5.2. <em>Provider</em> &amp; <em>Registration</em> Configuration</strong></h3>
Thanks to <em>spring-boot-starter-oauth2-client</em>, we need only a few application properties to:
<ul>
 	<li>Declare Keycloak as an OpenID Provider for our Spring application.</li>
 	<li>Configure a client <em>registration</em> for the app to use authorization code flow, with the parameters we set earlier in Keycloak admin console.</li>
</ul>
As Keycloak complies with OIDC, defining an issuer URI is enough to use it as authorization server for our app:
<pre><code class="language-plaintext">spring.security.oauth2.client.provider.baeldung-keycloak.issuer-uri=http://localhost:8080/realms/baeldung-keycloak</code></pre>
Let's now configure a client registration using the <em>baeldung-keycloak</em> provider declared above:
<pre><code class="language-plaintext">spring.security.oauth2.client.registration.keycloak.provider=baeldung-keycloak
spring.security.oauth2.client.registration.keycloak.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.keycloak.client-id=baeldung-keycloak-confidential
spring.security.oauth2.client.registration.keycloak.client-secret=secret
spring.security.oauth2.client.registration.keycloak.scope=openid</code></pre>
The value we specify in <em>client-id</em> matches the client declared in the admin console.

The value we set as <em>client-secret</em> (in this configuration file, or better as an environment variable or command-line argument) is exposed in the Credentials tab of client details in Keycloak admin console.
<h3 id="securityconfig"><strong>5.3. Mapping Keycloak Realm Roles to Spring Security Authorities</strong></h3>
There are several ways to map authorities in a filter-chain with <em>oauth2Login</em>, but the easiest is probably to expose a <em>GrantedAuthoritiesMapper</em> bean, and that's what we'll do here.

Let's start by defining a bean responsible for extracting authorities from a Keycloak claim set (this claim set could be extracted from an ID token, a user-info endpoint, a JWT payload, or an introspection response):
<pre><code class="language-java">static interface AuthoritiesConverter extends Converter&lt;Map&lt;String, Object&gt;, Collection&lt;GrantedAuthority&gt;&gt; {
}

@Bean
AuthoritiesConverter realmRolesAuthoritiesConverter() {
    return claims -&gt; {
        final var realmAccess = Optional.ofNullable((Map&lt;String, Object&gt;) claims.get("realm_access"));
        final var roles = realmAccess.flatMap(map -&gt; Optional.ofNullable((List&lt;String&gt;) map.get("roles")));
        return roles.map(List::stream).orElse(Stream.empty())
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    };
}</code></pre>
The <em>AuthoritiesConverter</em> interface is a tip for the bean factory because there might be many <em>Converter</em> beans with different inputs and outputs in an application context.

As we configured Keycloak as an OpenID Provider by providing just its <em>issuer-uri</em>, what we get as input in the <em>GrantedAuthoritiesMapper</em> are <em>OidcUserAuthority</em> instances:
<pre><code class="language-java">@Bean
GrantedAuthoritiesMapper authenticationConverter(
        Converter&lt;Map&lt;String, Object&gt;, Collection&lt;GrantedAuthority&gt;&gt; realmRolesAuthoritiesConverter) {
    return (authorities) -&gt; authorities.stream().filter(authority -&gt; authority instanceof OidcUserAuthority)
            .map(OidcUserAuthority.class::cast).map(OidcUserAuthority::getIdToken).map(OidcIdToken::getClaims)
            .map(realmRolesAuthoritiesConverter::convert)
            .flatMap(roles -&gt; roles.stream())
            .collect(Collectors.toSet());
}</code></pre>
It's worth noting how we injected and used the authorities converter bean defined above.
<h3>5.4. Putting the <em>SecurityFilterChain</em> Bean Together</h3>
Here is the complete <em>SecurityFilterChain</em> we'll use:
<pre><code class="language-java">@Bean
SecurityFilterChain clientSecurityFilterChain(HttpSecurity http,
        ClientRegistrationRepository clientRegistrationRepository) throws Exception {
    http.oauth2Login(Customizer.withDefaults());
    http.logout((logout) -&gt; {
        final var logoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        logoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
        logout.logoutSuccessHandler(logoutSuccessHandler);
    });
    http.oidcLogout((logout) -&gt; {
        logout.backChannel(Customizer.withDefaults());
    });

    http.authorizeHttpRequests(requests -&gt; {
        requests.requestMatchers("/", "/login/**", "/oauth2/**").permitAll();
        requests.requestMatchers("/nice.html").hasAuthority("NICE");
        requests.anyRequest().denyAll();
    });

    return http.build();
}</code></pre>
Let's break it down to understand the key parts.

<strong>The <em>oauth2Login()</em> method adds <a href="https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/web/OAuth2LoginAuthenticationFilter.html"><em>OAuth2LoginAuthenticationFilter</em></a> to the filter chain.</strong> This filter intercepts requests and applies the needed logic to handle authorization code &amp; refresh token flows, and to store tokens in session.

To understand how OAuth2 logouts work, we should remember that a user has a minimum of two independent sessions: one on each client with <em>oauth2Login</em>, plus one on the authorization server (Keycloak in our case). For a complete user logout, all sessions must be terminated. The OpenID standard defines different ways to achieve that, and we'll configure two.

<strong>By providing the <em>logoutSuccessHandler</em> with an <em>OidcClientInitiatedLogoutSuccessHandler</em>, we configure the <a href="https://openid.net/specs/openid-connect-rpinitiated-1_0.html">RP-Initiated Logout</a></strong>. In this flow, the user agent (user's browser or mobile app HTTP client) sends a POST request to the <em>Relying Party</em> (our Spring application with <em>oaut2Login</em>) to close its session. The RP then redirects the user agent to the <em>OpenID Provider</em> (OP) with the ID token linked to the session to close, and a post-logout URL. Once the OP has closed its session, it redirects the user agent to the provided URL.

<strong>The other mechanism defined in the OpenID standard is <a href="https://openid.net/specs/openid-connect-backchannel-1_0.html">Back-Channel Logout</a></strong>. This one does not involve the user agent. It is of interest in <em>Single Sign-On</em> (SSO) configurations, where a user has opened sessions with several RPs: each RP can register a callback to be notified when the OP closes a session for a user. That way, if a user logs out from an app using RP-Initiated Logout, after he was redirected to the OP, a notification can be sent to any other app having registered a Back-Channel Logout callback, so that it closes its own session for the logged out user. The Back-Channel Logout callback for our <em>baeldung-keycloak-confidential</em> client was defined in Keycloak admin console in section 3. We can try this flow using the <a href="http://localhost:8080/realms/baeldung-keycloak/account">Keycloak account UI</a>: after we logged in our Spring application, when clicking the logout button in Keycloak's UI and browsing back our Spring app, we should observe that we were logged out from the Spring application too.

The last piece in our web security configuration is requests authorization. Here, we chose to define access in Java configuration. An alternative would be using method security and annotations on <em>@Controller</em> methods.
<h3 id="thymeleaf"><strong>5.4. Thymeleaf Web Pages</strong></h3>
We're using Thymeleaf for our web pages.

We've got two pages:
<ul>
 	<li><em>index.html - </em>a landing page for the public</li>
 	<li><em>nice.html </em>- a page restricted to only authenticated users with the <em>NICE</em> authority</li>
</ul>
The code for the Thymeleaf templates is <a href="https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-keycloak/spring-boot-mvc-client/src/main/resources/templates">available on Github</a>.
<h3 id="controller"><strong>5.5. Controller</strong></h3>
The web controller maps the internal and external URLs to the appropriate Thymeleaf templates:
<pre><code class="language-java">@GetMapping(path = "/")
public String index() {
    return "external";
}
    
@GetMapping(path = "/customers")
public String customers(Principal principal, Model model) {
    addCustomers();
    model.addAttribute("customers", customerDAO.findAll());
    model.addAttribute("username", principal.getName());
    return "customers";
}</code></pre>
For the path <em>/customers</em>, we're retrieving all customers from a repository and adding the result as an attribute to the <em>Model</em>. Later on, we iterate through the results in Thymeleaf.

To be able to display a username, we're injecting the <em>Principal</em> as well.

We should note that we're using customers here just as raw data to display, and nothing more.
<h2 id="demo"><strong>6. Demonstration</strong></h2>
Now we're ready to test our application. To run a Spring Boot application, we can start it easily through an IDE, like Spring Tool Suite (STS), or run this command in the terminal:
<pre><code class="language-shell">mvn clean spring-boot:run</code></pre>
On visiting <em><a href="http://localhost:8081">http://localhost:8081</a> </em>we see:

<a href="/wp-content/uploads/2017/11/externalFacingKeycloakPage.png"><img class="alignnone wp-image-26293 size-full" src="https://www.baeldung.com/wp-content/uploads/2017/11/externalFacingKeycloakPage-e1551248465138.png" alt="external Facing Keycloak Page" /></a>

Now we click <em>customers</em> to enter the intranet, which is the location of sensitive information.

<strong>Note that we've been redirected to authenticate through Keycloak to see if we're authorized to view this content:</strong>

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/6_keycloak_userlogin.png"><img class="alignnone wp-image-104336 size-large" src="https://www.baeldung.com/wp-content/uploads/2017/11/6_keycloak_userlogin-1024x769.png" alt="keycloak userlogin" width="580" height="436" /></a>

Once we log in as <em>user1</em>, Keycloak will verify our authorization that we have the <em>user</em> role, and we'll be redirected to the restricted <em>customers</em> page:

<a href="https://www.baeldung.com/wp-content/uploads/2017/11/customers-page.png"><img class="alignnone wp-image-83726" src="https://www.baeldung.com/wp-content/uploads/2017/11/customers-page.png" alt="customers page" width="1154" height="346" /></a>

Now we've finished the setup of connecting Spring Boot with Keycloak and demonstrating how it works.

As we can see, <strong>Spring Boot seamlessly handled </strong><strong>the entire process of calling the Keycloak Authorization Server</strong>. We did not have to call the Keycloak API to generate the Access Token ourselves, or even send the Authorization header explicitly in our request for protected resources.
<h2 id="conclusion"><strong>7. Conclusion</strong></h2>
In this article, we configured a Keycloak server and used it with a Spring Boot Application.

We also learned how to set up Spring Security and use it in conjunction with Keycloak. A working version of the code shown in this article is available <a href="https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-keycloak">over on Github</a>.
