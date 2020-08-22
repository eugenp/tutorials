package com.baeldung.loginredirect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
class LoginRedirectSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password(encoder().encode("user")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
          .addFilterAfter(new LoginPageFilter(), UsernamePasswordAuthenticationFilter.class)

          .authorizeRequests()
          .antMatchers("/loginUser").permitAll()
          .antMatchers("/user*").hasRole("USER")

          .and().formLogin().loginPage("/loginUser").loginProcessingUrl("/user_login")
          .failureUrl("/loginUser?error=loginError").defaultSuccessUrl("/userMainPage").permitAll()

          .and().logout().logoutUrl("/user_logout").logoutSuccessUrl("/loginUser").deleteCookies("JSESSIONID")
          .and().csrf().disable();
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
