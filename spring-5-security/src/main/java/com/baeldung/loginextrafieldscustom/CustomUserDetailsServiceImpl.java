package com.baeldung.loginextrafieldscustom;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baeldung.dao.JpaUserRepository;
import com.baeldung.domain.AuthoritiesEntity;
import com.baeldung.domain.UserEntity;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private JpaUserRepository userRepository;
    
    private PasswordEncoder passwordEncoder;
 
    public CustomUserDetailsServiceImpl(JpaUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsernameAndDomain(String username, String domain) throws UsernameNotFoundException {
        if (StringUtils.isAnyBlank(username, domain)) {
            throw new UsernameNotFoundException("Username and domain must be provided");
        }
        Optional<UserEntity> userEntityOptional = userRepository.findByUsernameAndDomain(username, domain);
        if(userEntityOptional.isPresent()) {
            final UserEntity userEntity = userEntityOptional.get();
            
            final Collection<GrantedAuthority> authorities = userEntity.getAuthorities().stream()
                .map(AuthoritiesEntity::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
            
            return new User(userEntity.getUsername(), userEntity.getDomain(), passwordEncoder.encode(userEntity.getPassword()), userEntity.isEnabled(), 
                userEntity.isAccountNonExpired(), userEntity.isCredentialsNonExpired(), userEntity.isAccountNonLocked(), authorities);
        } else {
            throw new UsernameNotFoundException(
                String.format("Username not found for domain, username=%s, domain=%s", 
                    username, domain));
        }
    }
}
