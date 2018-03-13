前置文章
------
[Spring Security 入门：登录与退出](http://www.jianshu.com/p/a8e317e82425)

本文说明
------
本文是 spring security 与 mybatis 的整合，实现基于数据库的登录校验，使用 mariadb（ mysql 通用，不用改任何代码），希望对你有所帮助！

Github地址
-------
https://github.com/ChinaSilence/any-spring-security/tree/master/security-login-db

运行程序
------
1、clone 代码
```
git clone https://github.com/ChinaSilence/any-spring-security.git
cd security-login-db
```

2、建数据库,名称为 any
```
create database any;
use any;
```

3、执行 user.sql，会生成如下数据：

id|username|password|nickname|roles
--|--|--|--|--
1|anoy|pwd|anoy|ROLE_USER
2|admin|pwd|admin|ROLE_USER,ROLE_ADMIN

4、启动应用
```
mvn spring-boot:run
```

5、注册并登陆。

Spring Security 校验流程图
------
![Spring Security 登录流程.jpg](http://upload-images.jianshu.io/upload_images/3424642-7418a70abdfc7287.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

相关解释说明（认真看哦）
-----
#### AbstractAuthenticationProcessingFilter 抽象类

```
/**
	 * 调用 #requiresAuthentication(HttpServletRequest, HttpServletResponse) 决定是否需要进行验证操作。
	 * 如果需要验证，则会调用 #attemptAuthentication(HttpServletRequest, HttpServletResponse) 方法。
	 * 有三种结果：
	 * 1、返回一个 Authentication 对象。
     * 配置的 SessionAuthenticationStrategy` 将被调用，
     * 然后 然后调用 #successfulAuthentication(HttpServletRequest，HttpServletResponse，FilterChain，Authentication) 方法。
     * 2、验证时发生 AuthenticationException。
     * #unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException) 方法将被调用。
     * 3、返回Null，表示身份验证不完整。假设子类做了一些必要的工作（如重定向）来继续处理验证，方法将立即返回。
	 * 假设后一个请求将被这种方法接收，其中返回的Authentication对象不为空。
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}

		Authentication authResult;

		try {
			authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed
				// authentication
				return;
			}
			sessionStrategy.onAuthentication(authResult, request, response);
		}
		catch (InternalAuthenticationServiceException failed) {
			logger.error(
					"An internal error occurred while trying to authenticate the user.",
					failed);
			unsuccessfulAuthentication(request, response, failed);

			return;
		}
		catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);

			return;
		}

		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}

		successfulAuthentication(request, response, chain, authResult);
	}
```

#### UsernamePasswordAuthenticationFilter（AbstractAuthenticationProcessingFilter的子类）
```
public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
```

`#attemptAuthentication ()` 方法将 request 中的 username 和 password 生成 UsernamePasswordAuthenticationToken 对象，用于 `AuthenticationManager ` 的验证（即 this.getAuthenticationManager().authenticate(authRequest) ）。

默认情况下注入 Spring 容器的 `AuthenticationManager` 是 `ProviderManager`。

#### ProviderManager（AuthenticationManager的实现类）
```
/**
	 * 尝试验证 Authentication 对象
	 * AuthenticationProvider 列表将被连续尝试，直到 AuthenticationProvider 表示它能够认证传递的过来的Authentication 对象。然后将使用该 AuthenticationProvider 尝试身份验证。
	 * 如果有多个 AuthenticationProvider 支持验证传递过来的Authentication 对象，那么由第一个来确定结果，覆盖早期支持AuthenticationProviders 所引发的任何可能的AuthenticationException。 成功验证后，将不会尝试后续的AuthenticationProvider。
	 * 如果最后所有的 AuthenticationProviders 都没有成功验证 Authentication 对象，将抛出 AuthenticationException。
	 */
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Class<? extends Authentication> toTest = authentication.getClass();
		AuthenticationException lastException = null;
		Authentication result = null;
		boolean debug = logger.isDebugEnabled();

		for (AuthenticationProvider provider : getProviders()) {
			if (!provider.supports(toTest)) {
				continue;
			}

			if (debug) {
				logger.debug("Authentication attempt using "
						+ provider.getClass().getName());
			}

			try {
				result = provider.authenticate(authentication);

				if (result != null) {
					copyDetails(authentication, result);
					break;
				}
			}
			catch (AccountStatusException e) {
				prepareException(e, authentication);
				// SEC-546: Avoid polling additional providers if auth failure is due to
				// invalid account status
				throw e;
			}
			catch (InternalAuthenticationServiceException e) {
				prepareException(e, authentication);
				throw e;
			}
			catch (AuthenticationException e) {
				lastException = e;
			}
		}

		if (result == null && parent != null) {
			// Allow the parent to try.
			try {
				result = parent.authenticate(authentication);
			}
			catch (ProviderNotFoundException e) {
				// ignore as we will throw below if no other exception occurred prior to
				// calling parent and the parent
				// may throw ProviderNotFound even though a provider in the child already
				// handled the request
			}
			catch (AuthenticationException e) {
				lastException = e;
			}
		}

		if (result != null) {
			if (eraseCredentialsAfterAuthentication
					&& (result instanceof CredentialsContainer)) {
				// Authentication is complete. Remove credentials and other secret data
				// from authentication
				((CredentialsContainer) result).eraseCredentials();
			}

			eventPublisher.publishAuthenticationSuccess(result);
			return result;
		}

		// Parent was null, or didn't authenticate (or throw an exception).

		if (lastException == null) {
			lastException = new ProviderNotFoundException(messages.getMessage(
					"ProviderManager.providerNotFound",
					new Object[] { toTest.getName() },
					"No AuthenticationProvider found for {0}"));
		}

		prepareException(lastException, authentication);

		throw lastException;
	}
```
从代码中不难看出，由 provider 来验证 authentication， 核心点方法是：
```
Authentication result = provider.authenticate(authentication);
```
此处的 `provider` 是 `AbstractUserDetailsAuthenticationProvider`，
`AbstractUserDetailsAuthenticationProvider` 是AuthenticationProvider的实现，看看它的 `#authenticate(authentication)` 方法：
```
// 验证 authentication
public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.onlySupports",
						"Only UsernamePasswordAuthenticationToken is supported"));

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();

		boolean cacheWasUsed = true;
		UserDetails user = this.userCache.getUserFromCache(username);

		if (user == null) {
			cacheWasUsed = false;

			try {
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			catch (UsernameNotFoundException notFound) {
				logger.debug("User '" + username + "' not found");

				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials",
							"Bad credentials"));
				}
				else {
					throw notFound;
				}
			}

			Assert.notNull(user,
					"retrieveUser returned null - a violation of the interface contract");
		}

		try {
			preAuthenticationChecks.check(user);
			additionalAuthenticationChecks(user,
					(UsernamePasswordAuthenticationToken) authentication);
		}
		catch (AuthenticationException exception) {
			if (cacheWasUsed) {
				// There was a problem, so try again after checking
				// we're using latest data (i.e. not from the cache)
				cacheWasUsed = false;
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
				preAuthenticationChecks.check(user);
				additionalAuthenticationChecks(user,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			else {
				throw exception;
			}
		}

		postAuthenticationChecks.check(user);

		if (!cacheWasUsed) {
			this.userCache.putUserInCache(user);
		}

		Object principalToReturn = user;

		if (forcePrincipalAsString) {
			principalToReturn = user.getUsername();
		}

		return createSuccessAuthentication(principalToReturn, authentication, user);
	}
```
`AbstractUserDetailsAuthenticationProvider` 内置了缓存机制，从缓存中获取不到的 UserDetails 信息的话，就调用如下方法获取用户信息，然后和 用户传来的信息进行对比来判断是否验证成功。
```
// 获取用户信息
UserDetails user = retrieveUser(username,
 (UsernamePasswordAuthenticationToken) authentication);
```
`#retrieveUser()` 方法在 `DaoAuthenticationProvider` 中实现，`DaoAuthenticationProvider` 是 `AbstractUserDetailsAuthenticationProvider `的子类。具体实现如下：
```
protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser;

		try {
			loadedUser = this.getUserDetailsService().loadUserByUsername(username);
		}
		catch (UsernameNotFoundException notFound) {
			if (authentication.getCredentials() != null) {
				String presentedPassword = authentication.getCredentials().toString();
				passwordEncoder.isPasswordValid(userNotFoundEncodedPassword,
						presentedPassword, null);
			}
			throw notFound;
		}
		catch (Exception repositoryProblem) {
			throw new InternalAuthenticationServiceException(
					repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new InternalAuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}
```
可以看到此处的返回对象 `userDetails` 是由 `UserDetailsService` 的 `#loadUserByUsername(username)` 来获取的。

到了此处，就很清晰啦，Demo 中自定义了 `AnyUserDetailsService`。