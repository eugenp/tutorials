package org.baeldung.testmethodsecurity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockSysUserSecurityContextFactory.class)
public @interface WithMockSysUser {
	String systemUserName();
}