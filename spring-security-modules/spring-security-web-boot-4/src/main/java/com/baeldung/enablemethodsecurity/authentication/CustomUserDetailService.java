package com.baeldung.enablemethodsecurity.authentication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.baeldung.enablemethodsecurity.user.SecurityUser;

public class CustomUserDetailService implements UserDetailsService {
    private final Map<String, SecurityUser> userMap = new HashMap<>();

    public CustomUserDetailService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        userMap.put("user", createUser("user", bCryptPasswordEncoder.encode("userPass"), false, "USER"));
        userMap.put("admin", createUser("admin", bCryptPasswordEncoder.encode("adminPass"), true, "ADMIN", "USER"));
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userMap.get(username))
          .orElseThrow(() -> new UsernameNotFoundException("User " + username + " does not exists"));
    }

    private SecurityUser createUser(String userName, String password, boolean withRestrictedPolicy, String... role) {
        return SecurityUser.builder()
          .withUserName(userName)
          .withPassword(password)
          .withGrantedAuthorityList(Arrays.stream(role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()))
          .withAccessToRestrictedPolicy(withRestrictedPolicy);
    }
}