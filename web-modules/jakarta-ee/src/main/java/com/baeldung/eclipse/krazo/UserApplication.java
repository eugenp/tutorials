package com.baeldung.eclipse.krazo;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Default JAX-RS application listening on /app
 */
@ApplicationPath("/app")
public class UserApplication extends Application {
}
