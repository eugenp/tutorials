package com.baeldung.test.soap.keycloak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	
	//Reference article:
	//https://www.baeldung.com/spring-security-create-new-custom-security-expression
	
    //Uncomment for implementing permission checking
	//'CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();'
	//'expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());'
	//'return expressionHandler;'
	
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new CustomMethodSecurityExpressionHandler();
    }
}
