package com.baeldung.test.soap.keycloak.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;

import com.baeldung.test.soap.keycloak.exception.MethodHasAuthorityNotAllowedException;



public class MySecurityExpressionRoot implements MethodSecurityExpressionOperations {
	
	protected final Authentication authentication;
	private AuthenticationTrustResolver trustResolver;
	private RoleHierarchy roleHierarchy;
	private Set<String> roles;
	private String defaultRolePrefix = "ROLE_";

	public static final boolean PERMIT_ALL 	= true;
	public static final boolean DENY_ALL 	= false;
	private PermissionEvaluator permissionEvaluator;
	
	public static final String READ 	= "read";
	public static final String WRITE 	= "write";
	public static final String CREATE 	= "create";
	public static final String DELETE 	= "delete";
	public static final String ADMIN 	= "administration";

	private Object filterObject;
	private Object returnObject;

	public MySecurityExpressionRoot(Authentication authentication) {
		if (authentication == null) {
			throw new IllegalArgumentException("Authentication object cannot be null");
		}
		this.authentication = authentication;
	}

	@Override
	public final boolean hasAuthority(String authority) {
		throw new MethodHasAuthorityNotAllowedException("method hasAuthority() not allowed");
	}

	@Override
	public final boolean hasAnyAuthority(String... authorities) {
		return hasAnyAuthorityName(null, authorities);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final boolean hasRole(String role) {
		final Jwt jwt = (Jwt) this.authentication.getPrincipal();

		//This is used when roles are defined at client level
		//final Map<String, Object> map1 = jwt.getClaimAsMap("resource_access");
		//final Map<String, Object> map2 = (Map<String, Object>) map1.get("client-id");
		//final List<String> rolesList = (List<String>) map2.get("roles");
		
		final Map<String, Object> map1 = jwt.getClaimAsMap("realm_access");
		final List<String> rolesList = (List<String>) map1.get("roles");

		return rolesList.contains(role);
	}

	@Override
	public final boolean hasAnyRole(String... roles) {
		return hasAnyAuthorityName(defaultRolePrefix, roles);
	}

	private boolean hasAnyAuthorityName(String prefix, String... roles) {
		final Set<String> roleSet = getAuthoritySet();

		for (final String role : roles) {
			final String defaultedRole = getRoleWithDefaultPrefix(prefix, role);
			if (roleSet.contains(defaultedRole)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public final Authentication getAuthentication() {
		return authentication;
	}

	@Override
	public final boolean permitAll() {
		return true;
	}

	@Override
	public final boolean denyAll() {
		return false;
	}

	@Override
	public final boolean isAnonymous() {
		return trustResolver.isAnonymous(authentication);
	}

	@Override
	public final boolean isAuthenticated() {
		return !isAnonymous();
	}

	@Override
	public final boolean isRememberMe() {
		return trustResolver.isRememberMe(authentication);
	}

	@Override
	public final boolean isFullyAuthenticated() {
		return !trustResolver.isAnonymous(authentication) && !trustResolver.isRememberMe(authentication);
	}

	public Object getPrincipal() {
		return authentication.getPrincipal();
	}

	public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
		this.trustResolver = trustResolver;
	}

	public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}

	public void setDefaultRolePrefix(String defaultRolePrefix) {
		this.defaultRolePrefix = defaultRolePrefix;
	}

	private Set<String> getAuthoritySet() {
		if (roles == null) {
			roles = new HashSet<>();
			Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();

			if (roleHierarchy != null) {
				userAuthorities = roleHierarchy.getReachableGrantedAuthorities(userAuthorities);
			}

			roles = AuthorityUtils.authorityListToSet(userAuthorities);
		}

		return roles;
	}

	@Override
	public boolean hasPermission(Object target, Object permission) {
		return permissionEvaluator.hasPermission(authentication, target, permission);
	}

	@Override
	public boolean hasPermission(Object targetId, String targetType, Object permission) {
		return permissionEvaluator.hasPermission(authentication, (Serializable) targetId, targetType, permission);
	}

	public void setPermissionEvaluator(PermissionEvaluator permissionEvaluator) {
		this.permissionEvaluator = permissionEvaluator;
	}

	private static String getRoleWithDefaultPrefix(String defaultRolePrefix, String role) {
		if (role == null) {
			return role;
		}
		if ((defaultRolePrefix == null) || (defaultRolePrefix.length() == 0)) {
			return role;
		}
		if (role.startsWith(defaultRolePrefix)) {
			return role;
		}
		return defaultRolePrefix + role;
	}

	@Override
	public Object getFilterObject() {
		return this.filterObject;
	}

	@Override
	public Object getReturnObject() {
		return this.returnObject;
	}

	@Override
	public Object getThis() {
		return this;
	}

	@Override
	public void setFilterObject(Object obj) {
		this.filterObject = obj;
	}

	@Override
	public void setReturnObject(Object obj) {
		this.returnObject = obj;
	}
}