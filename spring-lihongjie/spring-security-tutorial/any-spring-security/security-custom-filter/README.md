本文解决问题
----
将自定义的 `Filter` 加入到 Spring Security 中的 `Filter` 链中的指定位置。


Spring Security 默认的过滤器链
----
官网位置：http://docs.spring.io/spring-security/site/docs/5.0.0.M1/reference/htmlsingle/#ns-custom-filters

别名|类名称|Namespace Element or Attribute
--|--|--|
CHANNEL_FILTER|ChannelProcessingFilter|http/intercept-url@requires-channel
SECURITY_CONTEXT_FILTER|SecurityContextPersistenceFilter|http
CONCURRENT_SESSION_FILTER|ConcurrentSessionFilter|session-management/concurrency-control
HEADERS_FILTER|HeaderWriterFilter|http/headers
CSRF_FILTER|CsrfFilter|http/csrf
LOGOUT_FILTER|LogoutFilter|http/logout
X509_FILTER|X509AuthenticationFilter|http/x509
PRE_AUTH_FILTER|AbstractPreAuthenticatedProcessingFilter( Subclasses)|N/A
CAS_FILTER|CasAuthenticationFilter|N/A
FORM_LOGIN_FILTER|UsernamePasswordAuthenticationFilter|http/form-login
BASIC_AUTH_FILTER|BasicAuthenticationFilter|http/http-basic
SERVLET_API_SUPPORT_FILTER|SecurityContextHolderAwareRequestFilter|http/@servlet-api-provision
JAAS_API_SUPPORT_FILTER|JaasApiIntegrationFilter|http/@jaas-api-provision
REMEMBER_ME_FILTER|RememberMeAuthenticationFilter|http/remember-me
ANONYMOUS_FILTER|AnonymousAuthenticationFilter|http/anonymous
SESSION_MANAGEMENT_FILTER|SessionManagementFilter|session-management
EXCEPTION_TRANSLATION_FILTER|ExceptionTranslationFilter|http
FILTER_SECURITY_INTERCEPTOR|FilterSecurityInterceptor|http
SWITCH_USER_FILTER|SwitchUserFilter|N/A

> 过滤器顺序从上到下

自定义 Filter
-------
自定义的 `Filter` 建议继承 `GenericFilterBean`，本文示例：
```
public class BeforeLoginFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("This is a filter before UsernamePasswordAuthenticationFilter.");
        // 继续调用 Filter 链
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
```

配置自定义 Filter 在 Spring Security 过滤器链中的位置
------
配置很简单，本文示例：
```
protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/user")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        // 在 UsernamePasswordAuthenticationFilter 前添加 BeforeLoginFilter
        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);

        // 在 CsrfFilter 后添加 AfterCsrfFilter
        http.addFilterAfter(new AfterCsrfFilter(), CsrfFilter.class);
    }
```
说明：
`HttpSecurity` 有三个常用方法来配置：
 - addFilterBefore(Filter filter, Class<? extends Filter> beforeFilter)
在 beforeFilter 之前添加 filter
 - addFilterAfter(Filter filter, Class<? extends Filter> afterFilter)
在 afterFilter 之后添加 filter
 - addFilterAt(Filter filter, Class<? extends Filter> atFilter)
在 atFilter 相同位置添加 filter， 此 filter 不覆盖 filter

> 通过在不同 `Filter` 的 `doFilter()` 方法中加断点调试，可以判断哪个 filter 先执行，从而判断 filter 的执行顺序 。