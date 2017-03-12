package org.baeldung.multipleentrypoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class MultipleEntryPointsSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("userPass").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("adminPass").roles("ADMIN").build());
        return manager;
    }

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App1ConfigurationAdapter() {
            super();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //@formatter:off
            http.antMatcher("/admin/**")
                .authorizeRequests().anyRequest().hasRole("ADMIN")
                .and().httpBasic()
                .and().exceptionHandling().accessDeniedPage("/403");
            //@formatter:on
        }
    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App2ConfigurationAdapter() {
            super();
        }

        protected void configure(HttpSecurity http) throws Exception {
            //@formatter:off
            http.antMatcher("/user/**")
                .authorizeRequests().anyRequest().hasRole("USER")              
                .and().formLogin().loginPage("/userLogin").loginProcessingUrl("/user/login")
                .failureUrl("/userLogin?error=loginError").defaultSuccessUrl("/user/myUserPage")
                .and().logout().logoutUrl("/user/logout").logoutSuccessUrl("/multipleHttpLinks")
                .deleteCookies("JSESSIONID")
                .and().exceptionHandling().accessDeniedPage("/403")
                .and().csrf().disable();
            //@formatter:on
        }
    }

    @Configuration
    @Order(3)
    public static class App3ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App3ConfigurationAdapter() {
            super();
        }

        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/guest/**").authorizeRequests().anyRequest().permitAll();
        }
    }

}
