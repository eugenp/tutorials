package com.baeldung.si.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.security.channel.ChannelSecurityInterceptor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AccessDecisionManager customAccessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new UsernameAccessDecisionVoter());
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
        return accessDecisionManager;
    }

    @Autowired
    @Bean
    public ChannelSecurityInterceptor channelSecurityInterceptor(AuthenticationManager authenticationManager, AccessDecisionManager customAccessDecisionManager) {
        ChannelSecurityInterceptor channelSecurityInterceptor = new ChannelSecurityInterceptor();
        channelSecurityInterceptor.setAuthenticationManager(authenticationManager);
        channelSecurityInterceptor.setAccessDecisionManager(customAccessDecisionManager);
        return channelSecurityInterceptor;
    }

}
