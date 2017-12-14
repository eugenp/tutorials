package org.baeldung.testmethodsecurity;

import org.baeldung.testmethodsecurity.entity.CustomUser;
import org.baeldung.testmethodsecurity.repository.UserRoleRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockSysUserSecurityContextFactory
	implements WithSecurityContextFactory<WithMockSysUser> {
    
	@Override
	public SecurityContext createSecurityContext(WithMockSysUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		UserRoleRepository userRoleRepo = new UserRoleRepository();
		
		CustomUser user = userRoleRepo.loadUserByUserName(customUser.systemUserName());
		
		Authentication auth =
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		
		context.setAuthentication(auth);
		return context;
	}
	
}