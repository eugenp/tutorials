package cn.nonocast.security;

import cn.nonocast.service.AccessTokenService;
import cn.nonocast.service.UserService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import cn.nonocast.model.*;

@Service
public class ApiAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	private static final Logger logger = LoggerFactory.getLogger(ApiAuthenticationUserDetailsService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AccessTokenService accessTokenService;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		String principal = (String) token.getPrincipal();

		UserDetails result = null;
		if(!Strings.isNullOrEmpty(principal)) {
			logger.debug(principal);
			String[] slices = principal.split(":");
			String email = slices[0];
			String secret = slices[1];

			try {
				AccessToken p = accessTokenService.valid(email, secret);
				result = userService.findByEmail(p.getEmail());
			} catch(Exception ex) {
				throw new UsernameNotFoundException("");
			}
		}

		return result;
	}
}
