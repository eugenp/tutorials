package org.baeldung.config;


//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.requestMatchers()
//            .antMatchers("/user/me")
//            .and()
//            .authorizeRequests()
//            .anyRequest()
//            .permitAll()
//            .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        return converter;
//    }
// }