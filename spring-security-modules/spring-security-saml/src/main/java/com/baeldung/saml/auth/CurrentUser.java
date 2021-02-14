package com.baeldung.saml.auth;

import java.lang.annotation.*;

/**
 * Represents the current authenticated user during HTTP requests
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
