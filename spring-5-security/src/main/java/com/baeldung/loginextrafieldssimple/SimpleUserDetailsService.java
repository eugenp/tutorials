package com.baeldung.loginextrafieldssimple;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baeldung.dao.JpaUserRepository;
import com.baeldung.domain.AuthoritiesEntity;
import com.baeldung.domain.UserEntity;

@Service("userDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {

    private JpaUserRepository userRepository;
 
    public SimpleUserDetailsService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] usernameAndDomain = StringUtils.split(username, String.valueOf(Character.LINE_SEPARATOR));
        if (usernameAndDomain == null || usernameAndDomain.length != 2) {
            throw new UsernameNotFoundException("Username and domain must be provided");
        }
        Optional<UserEntity> userEntityOptional = userRepository.findByUsernameAndDomain(usernameAndDomain[0], usernameAndDomain[1]);
        if (userEntityOptional.isPresent()) {
            final UserEntity userEntity = userEntityOptional.get();

            final Collection<GrantedAuthority> authorities = userEntity.getAuthorities()
                .stream()
                .map(AuthoritiesEntity::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

            return new User(userEntity.getUsername(), userEntity.getDomain(), userEntity.getPassword(), userEntity.isEnabled(), 
                userEntity.isAccountNonExpired(), userEntity.isCredentialsNonExpired(), userEntity.isAccountNonLocked(), authorities);
        } else {
            throw new UsernameNotFoundException(
                String.format("Username not found for domain, username=%s, domain=%s", 
                    usernameAndDomain[0], usernameAndDomain[1]));
        }
    }
}
