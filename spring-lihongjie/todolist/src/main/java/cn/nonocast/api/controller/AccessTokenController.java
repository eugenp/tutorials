package cn.nonocast.api.controller;

import cn.nonocast.model.User;
import cn.nonocast.model.AccessToken;
import cn.nonocast.service.AccessTokenService;
import cn.nonocast.service.UserService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController("apiTokenController")
@RequestMapping("/api")
public class AccessTokenController {
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AccessTokenService accessTokenService;

	@RequestMapping("token")
	public ResponseEntity<?> token(
		@RequestHeader(value="Authorization", defaultValue="") String authorization,
		HttpServletResponse response) {

		if(Strings.isNullOrEmpty(authorization)) {
			response.setHeader("WWW-authenticate", "basic");
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		AccessToken token = null;

		Pattern p = Pattern.compile("basic\\s+(.*)$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(authorization);
		if(matcher.matches()) {
			String auth = new String(Base64.getDecoder().decode(matcher.group(1))).trim();
			String[] slices = auth.split(":");
			String email = slices[0];
			String password = slices[1];

			User user = userService.findByEmail(email);
			if(passwordEncoder.matches(password, user.getPassword())) {
				token = accessTokenService.get(user);
			}
		}

		if(token == null) {
			response.setHeader("WWW-authenticate", "basic");
			return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(token.getSecret(), HttpStatus.OK);
	}
}
