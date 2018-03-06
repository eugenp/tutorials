前言
-----
Spring Security 比较复杂，愿与各位一起研究使用，有歧义的地方，欢迎指出，谢谢！

Github 地址
-----
https://github.com/ChinaSilence/any-spring-security.git

启动应用
-----

1、clone 代码之后，进入  目录，启动应用
```
mvn spring-boot:run
```

2、demo 演示，地址：[http://localhost:8080](http://localhost:8080) 账号 anoy 密码 pwd


![登录](http://upload-images.jianshu.io/upload_images/3424642-43dcb43f0ba50ce5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![用户](http://upload-images.jianshu.io/upload_images/3424642-c38bb99d76a0bd83.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



相关解释说明
-----
#### 注解 @EnableWebSecurity
在 *Spring boot* 应用中使用 *Spring Security*，用到了 `@EnableWebSecurity`注解，官方说明如下：
```
/**
 * Add this annotation to an {@code @Configuration} class to have the Spring Security
 * configuration defined in any {@link WebSecurityConfigurer} or more likely by extending
 * the {@link WebSecurityConfigurerAdapter} base class and overriding individual methods:
 */
```

意思是说， 该注解和 `@Configuration` 注解一起使用, 注解 `WebSecurityConfigurer` 类型的类，或者利用`@EnableWebSecurity` 注解继承 `WebSecurityConfigurerAdapter`的类，这样就构成了 *Spring Security* 的配置。

#### 抽象类 WebSecurityConfigurerAdapter
一般情况，会选择继承 `WebSecurityConfigurerAdapter` 类，其官方说明如下：
```
/**
 * Provides a convenient base class for creating a {@link WebSecurityConfigurer}
 * instance. The implementation allows customization by overriding methods.
 *
 * <p>
 * Will automatically apply the result of looking up
 * {@link AbstractHttpConfigurer} from {@link SpringFactoriesLoader} to allow
 * developers to extend the defaults.
 * To do this, you must create a class that extends AbstractHttpConfigurer and then create a file in the classpath at "META-INF/spring.factories" that looks something like:
 * </p>
 * <pre>
 * org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer = sample.MyClassThatExtendsAbstractHttpConfigurer
 * </pre>
 * If you have multiple classes that should be added you can use "," to separate the values. For example:
 *
 * <pre>
 * org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer = sample.MyClassThatExtendsAbstractHttpConfigurer, sample.OtherThatExtendsAbstractHttpConfigurer
 * </pre>
 *
 */
```
意思是说 `WebSecurityConfigurerAdapter` 提供了一种便利的方式去创建 `WebSecurityConfigurer`的实例，只需要重写 `WebSecurityConfigurerAdapter` 的方法，即可配置拦截什么URL、设置什么权限等安全控制。

#### 方法 configure(AuthenticationManagerBuilder auth) 和 configure(HttpSecurity http)
Demo 中重写了 `WebSecurityConfigurerAdapter` 的两个方法：

```
   /**
	 * 通过 {@link #authenticationManager()} 方法的默认实现尝试获取一个 {@link AuthenticationManager}.
	 * 如果被复写, 应该使用{@link AuthenticationManagerBuilder} 来指定 {@link AuthenticationManager}.
	 *
	 * 例如, 可以使用以下配置在内存中进行注册公开内存的身份验证{@link UserDetailsService}:
	 *
	 * // 在内存中添加 user 和 admin 用户
	 * @Override
	 * protected void configure(AuthenticationManagerBuilder auth) {
	 * 	auth
	 *   	.inMemoryAuthentication().withUser("user").password("password").roles("USER").and()
	 * 		.withUser("admin").password("password").roles("USER", "ADMIN");
	 * }
	 *
	 * // 将 UserDetailsService 显示为 Bean
	 * @Bean
	 * @Override
	 * public UserDetailsService userDetailsServiceBean() throws Exception {
	 * 	return super.userDetailsServiceBean();
	 * }
	 *
	 */
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		this.disableLocalConfigureAuthenticationBldr = true;
	}
```

```
	/**
	 * 复写这个方法来配置 {@link HttpSecurity}. 
	 * 通常，子类不能通过调用 super 来调用此方法，因为它可能会覆盖其配置。 默认配置为：
	 * 
	 * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	 *
	 */
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin().and()
			.httpBasic();
	}
```

#### final 类 HttpSecurity
表1 HttpSecurity 常用方法及说明

| 方法 | 说明 |
| ---- | ---- |
| `openidLogin()` | 用于基于 OpenId 的验证 |
| `headers()`| 将安全标头添加到响应 |
| `cors()` | 配置跨域资源共享（ CORS ） |
| `sessionManagement()` | 允许配置会话管理 |
| `portMapper()` | 允许配置一个`PortMapper`(`HttpSecurity#(getSharedObject(class))`)，其他提供`SecurityConfigurer`的对象使用 `PortMapper` 从 HTTP 重定向到 HTTPS 或者从 HTTPS 重定向到 HTTP。默认情况下，Spring Security使用一个`PortMapperImpl`映射 HTTP 端口8080到 HTTPS 端口8443，HTTP 端口80到 HTTPS 端口443 |
| `jee()` | 配置基于容器的预认证。 在这种情况下，认证由Servlet容器管理 |
| `x509()` | 配置基于x509的认证 |
| `rememberMe` | 允许配置“记住我”的验证 |
| `authorizeRequests()` | 允许基于使用`HttpServletRequest`限制访问 | 
| `requestCache()` | 允许配置请求缓存 |
| `exceptionHandling()` | 允许配置错误处理 |
| `securityContext()` |  在`HttpServletRequests`之间的`SecurityContextHolder`上设置`SecurityContext`的管理。 当使用`WebSecurityConfigurerAdapter`时，这将自动应用 | 
| `servletApi()` | 将`HttpServletRequest`方法与在其上找到的值集成到`SecurityContext`中。 当使用`WebSecurityConfigurerAdapter`时，这将自动应用 |
| `csrf()` | 添加 CSRF 支持，使用`WebSecurityConfigurerAdapter`时，默认启用 |
| `logout()` | 添加退出登录支持。当使用`WebSecurityConfigurerAdapter`时，这将自动应用。默认情况是，访问URL"/ logout"，使HTTP Session无效来清除用户，清除已配置的任何`#rememberMe()`身份验证，清除`SecurityContextHolder`，然后重定向到"/login?success" |
| `anonymous()` | 允许配置匿名用户的表示方法。 当与`WebSecurityConfigurerAdapter`结合使用时，这将自动应用。 默认情况下，匿名用户将使用`org.springframework.security.authentication.AnonymousAuthenticationToken`表示，并包含角色 "ROLE_ANONYMOUS" |
| `formLogin()` | 指定支持基于表单的身份验证。如果未指定`FormLoginConfigurer#loginPage(String)`，则将生成默认登录页面 |
| `oauth2Login()` | 根据外部OAuth 2.0或OpenID Connect 1.0提供程序配置身份验证 |
| `requiresChannel()` | 配置通道安全。为了使该配置有用，必须提供至少一个到所需信道的映射 |
| `httpBasic()` | 配置 Http Basic 验证 |
| `addFilterAt()`  | 在指定的Filter类的位置添加过滤器 |

#### 类 AuthenticationManagerBuilder

```
/**
 * {@link SecurityBuilder} used to create an {@link AuthenticationManager}. Allows for
 * easily building in memory authentication, LDAP authentication, JDBC based
 * authentication, adding {@link UserDetailsService}, and adding
 * {@link AuthenticationProvider}'s.
 */
```
意思是，`AuthenticationManagerBuilder` 用于创建一个 `AuthenticationManager`，让我能够轻松的实现内存验证、LADP验证、基于JDBC的验证、添加`UserDetailsService`、添加`AuthenticationProvider`。

其他
-----
    如需转载，请联系作者，邮箱 545544032@qq.com
    开源社区 http://spring4all.com 欢迎你