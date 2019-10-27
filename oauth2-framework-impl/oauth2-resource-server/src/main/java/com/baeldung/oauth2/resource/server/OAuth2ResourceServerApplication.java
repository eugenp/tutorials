package com.baeldung.oauth2.resource.server;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
@DeclareRoles({"resource.read", "resource.write"})
@LoginConfig(authMethod = "MP-JWT")
public class OAuth2ResourceServerApplication extends Application {
}
