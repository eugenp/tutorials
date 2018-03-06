<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="gr__docs_spring_io">
<head>
<!-- <script type="text/javascript" async="" src="http://bserv.cfapps.io/gb/docsearchbarspring"></script>
 -->
 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security Reference</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public-resources/css/manual-multipage.css">
<meta name="generator" content="DocBook XSL Stylesheets V1.78.1">
<link rel="home" href="index.html" title="Spring Security Reference">
<link rel="next" href="pr01.html" title="">
<script src="${pageContext.request.contextPath}/public-resources/lib/jquery-1.4.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public-resources/css/searchtool.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public-resources/css/searchtool_spring.css" type="text/css">
</head>
<body class="firstpage" bgcolor="white" text="black" link="#0000FF"
	vlink="#840084" alink="#0000FF" data-gr-c-s-loaded="true">
	<div class="navheader">
		<table width="100%" summary="Navigation header">
			<tbody>
				<tr>
					<th colspan="3" align="center">Spring Security Reference</th>
				</tr>
				<tr>
					<td width="20%" align="left">&nbsp;</td>
					<th width="60%" align="center">&nbsp;</th>
					<td width="20%" align="right">&nbsp;<a accesskey="n"
						href="pr01.html">Next</a></td>
				</tr>
			</tbody>
		</table>
		<hr>
	</div>
	<div lang="en" class="book">
		<div class="titlepage">
			<div>
				<div>
					<h1 class="title">
						<a name="d5e1"></a>Spring Security Reference
					</h1>
				</div>
				<div>
					<div class="authorgroup">
						<h2>Authors</h2>
						<span class="author"><span class="firstname">Ben</span> <span
							class="surname">Alex</span></span> , <span class="author"><span
							class="firstname">Luke</span> <span class="surname">Taylor</span></span>
						, <span class="author"><span class="firstname">Rob</span> <span
							class="surname">Winch</span></span> , <span class="author"><span
							class="firstname">Gunnar</span> <span class="surname">Hillert</span></span>
					</div>
				</div>
				<div>
					<p class="releaseinfo">4.1.2.RELEASE</p>
				</div>
				<div>
					<p class="copyright">Copyright © 2004-2015</p>
				</div>
				<div>
					<div class="legalnotice">
						<a name="d5e26" href="#d5e26"></a>
						<p>Copies of this document may be made for your own use and
							for distribution to others, provided that you do not charge any
							fee for such copies and further provided that each copy contains
							this Copyright Notice, whether distributed in print or
							electronically.</p>
					</div>
				</div>
			</div>
			<hr>
		</div>
		<div class="toc">
			<p>
				<b>Table of Contents</b>
			</p>
			<dl class="toc">
				<dt>
					<span class="preface"><a href="pr01.html"></a></span>
				</dt>
				<dt>
					<span class="part"><a href="preface.html">I. Preface</a></span>
				</dt>
				<dd>
					<dl>
						<dt>
							<span class="chapter"><a href="getting-started.html">1.
									Getting Started</a></span>
						</dt>
						<dt>
							<span class="chapter"><a href="introduction.html">2.
									Introduction</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="introduction.html#what-is-acegi-security">2.1. What
											is Spring Security?</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="introduction.html#history">2.2. History</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="introduction.html#release-numbering">2.3. Release
											Numbering</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="introduction.html#get-spring-security">2.4. Getting
											Spring Security</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="introduction.html#maven">2.4.1. Usage with Maven</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="introduction.html#maven-repositories">Maven
															Repositories</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#maven-bom">Spring Framework
															Bom</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="introduction.html#gradle">2.4.2. Gradle</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="introduction.html#gradle-repositories">Gradle
															Repositories</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#gradle-resolutionStrategy">Using
															Spring 4.0.x and Gradle</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="introduction.html#modules">2.4.3. Project Modules</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-core">Core -
															spring-security-core.jar</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-remoting">Remoting
															- spring-security-remoting.jar</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-web">Web -
															spring-security-web.jar</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-config">Config
															- spring-security-config.jar</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-ldap">LDAP -
															spring-security-ldap.jar</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-acl">ACL -
															spring-security-acl.jar</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-cas">CAS -
															spring-security-cas.jar</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-openid">OpenID
															- spring-security-openid.jar</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="introduction.html#spring-security-test">Test -
															spring-security-test.jar</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="introduction.html#get-source">2.4.4. Checking out
													the Source</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="new.html">3. What’s New
									in Spring Security 4.1</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="new.html#java-configuration-improvements">3.1. Java
											Configuration Improvements</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="new.html#web-application-security-improvements">3.2.
											Web Application Security Improvements</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="new.html#authorization-improvements">3.3.
											Authorization Improvements</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="new.html#crypto-module-improvements">3.4. Crypto
											Module Improvements</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="new.html#testing-improvements">3.5. Testing
											Improvements</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="new.html#general-improvements">3.6. General
											Improvements</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="samples.html">4.
									Samples and Guides (Start Here)</a></span>
						</dt>
						<dt>
							<span class="chapter"><a href="jc.html">5. Java
									Configuration</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="jc.html#hello-web-security-java-configuration">5.1.
											Hello Web Security Java Configuration</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="jc.html#abstractsecuritywebapplicationinitializer">5.1.1.
													AbstractSecurityWebApplicationInitializer</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#abstractsecuritywebapplicationinitializer-without-existing-spring">5.1.2.
													AbstractSecurityWebApplicationInitializer without Existing
													Spring</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#abstractsecuritywebapplicationinitializer-with-spring-mvc">5.1.3.
													AbstractSecurityWebApplicationInitializer with Spring MVC</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a href="jc.html#jc-httpsecurity">5.2.
											HttpSecurity</a></span>
								</dt>
								<dt>
									<span class="section"><a href="jc.html#jc-form">5.3.
											Java Configuration and Form Login</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="jc.html#authorize-requests">5.4. Authorize Requests</a></span>
								</dt>
								<dt>
									<span class="section"><a href="jc.html#jc-logout">5.5.
											Handling Logouts</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="jc.html#jc-logout-handler">5.5.1. LogoutHandler</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#jc-logout-success-handler">5.5.2.
													LogoutSuccessHandler</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#jc-logout-references">5.5.3. Further
													Logout-Related References</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="jc.html#jc-authentication">5.6. Authentication</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="jc.html#jc-authentication-inmememory">5.6.1. In
													Memory Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#jc-authentication-jdbc">5.6.2. JDBC
													Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#ldap-authentication">5.6.3. LDAP
													Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#jc-authentication-authenticationprovider">5.6.4.
													AuthenticationProvider</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#jc-authentication-userdetailsservice">5.6.5.
													UserDetailsService</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#ldap-authentication-2">5.6.6. LDAP
													Authentication</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="jc.html#multiple-httpsecurity">5.7. Multiple
											HttpSecurity</a></span>
								</dt>
								<dt>
									<span class="section"><a href="jc.html#jc-method">5.8.
											Method Security</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="jc.html#enableglobalmethodsecurity">5.8.1.
													EnableGlobalMethodSecurity</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jc.html#globalmethodsecurityconfiguration">5.8.2.
													GlobalMethodSecurityConfiguration</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="jc.html#post-processing-configured-objects">5.9.
											Post Processing Configured Objects</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="ns-config.html">6.
									Security Namespace Configuration</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="ns-config.html#introduction-2">6.1. Introduction</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="ns-config.html#design-of-the-namespace">6.1.1.
													Design of the Namespace</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="ns-config.html#ns-getting-started">6.2. Getting
											Started with Security Namespace Configuration</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-web-xml">6.2.1. web.xml
													Configuration</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-minimal">6.2.2. A Minimal
													&lt;http&gt; Configuration</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-form-and-basic">6.2.3. Form and
													Basic Login Options</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ns-config.html#ns-form-target">Setting a Default
															Post-Login Destination</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-logout">6.2.4. Logout Handling</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-auth-providers">6.2.5. Using
													other Authentication Providers</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ns-config.html#ns-password-encoder">Adding a
															Password Encoder</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="ns-config.html#ns-web-advanced">6.3. Advanced Web
											Features</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-remember-me">6.3.1. Remember-Me
													Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-requires-channel">6.3.2. Adding
													HTTP/HTTPS Channel Security</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-session-mgmt">6.3.3. Session
													Management</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ns-config.html#detecting-timeouts">Detecting
															Timeouts</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="ns-config.html#ns-concurrent-sessions">Concurrent
															Session Control</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="ns-config.html#ns-session-fixation">Session
															Fixation Attack Protection</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-openid">6.3.4. OpenID Support</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ns-config.html#attribute-exchange">Attribute
															Exchange</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-headers">6.3.5. Response Headers</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-custom-filters">6.3.6. Adding in
													Your Own Filters</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ns-config.html#ns-entry-point-ref">Setting a
															Custom AuthenticationEntryPoint</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="ns-config.html#ns-method-security">6.4. Method
											Security</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-global-method">6.4.1. The
													&lt;global-method-security&gt; Element</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ns-config.html#ns-protect-pointcut">Adding
															Security Pointcuts using protect-pointcut</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="ns-config.html#ns-access-manager">6.5. The Default
											AccessDecisionManager</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="ns-config.html#ns-custom-access-mgr">6.5.1.
													Customizing the AccessDecisionManager</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="ns-config.html#ns-auth-manager">6.6. The
											Authentication Manager and the Namespace</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="sample-apps.html">7.
									Sample Applications</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="sample-apps.html#tutorial-sample">7.1. Tutorial
											Sample</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="sample-apps.html#contacts-sample">7.2. Contacts</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="sample-apps.html#ldap-sample">7.3. LDAP Sample</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="sample-apps.html#openid-sample">7.4. OpenID Sample</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="sample-apps.html#cas-sample">7.5. CAS Sample</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="sample-apps.html#jaas-sample">7.6. JAAS Sample</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="sample-apps.html#preauth-sample">7.7.
											Pre-Authentication Sample</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="community.html">8.
									Spring Security Community</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a href="community.html#jira">8.1.
											Issue Tracking</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="community.html#becoming-involved">8.2. Becoming
											Involved</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="community.html#further-info">8.3. Further
											Information</a></span>
								</dt>
							</dl>
						</dd>
					</dl>
				</dd>
				<dt>
					<span class="part"><a href="overall-architecture.html">II.
							Architecture and Implementation</a></span>
				</dt>
				<dd>
					<dl>
						<dt>
							<span class="chapter"><a href="technical-overview.html">9.
									Technical Overview</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="technical-overview.html#runtime-environment">9.1.
											Runtime Environment</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="technical-overview.html#core-components">9.2. Core
											Components</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="technical-overview.html#securitycontextholder-securitycontext-and-authentication-objects">9.2.1.
													SecurityContextHolder, SecurityContext and Authentication
													Objects</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="technical-overview.html#obtaining-information-about-the-current-user">Obtaining
															information about the current user</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="technical-overview.html#tech-userdetailsservice">9.2.2.
													The UserDetailsService</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="technical-overview.html#tech-granted-authority">9.2.3.
													GrantedAuthority</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="technical-overview.html#summary">9.2.4. Summary</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="technical-overview.html#tech-intro-authentication">9.3.
											Authentication</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="technical-overview.html#what-is-authentication-in-spring-security">9.3.1.
													What is authentication in Spring Security?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="technical-overview.html#setting-the-securitycontextholder-contents-directly">9.3.2.
													Setting the SecurityContextHolder Contents Directly</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="technical-overview.html#tech-intro-web-authentication">9.4.
											Authentication in a Web Application</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="technical-overview.html#exceptiontranslationfilter">9.4.1.
													ExceptionTranslationFilter</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="technical-overview.html#tech-intro-auth-entry-point">9.4.2.
													AuthenticationEntryPoint</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="technical-overview.html#authentication-mechanism">9.4.3.
													Authentication Mechanism</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="technical-overview.html#tech-intro-sec-context-persistence">9.4.4.
													Storing the SecurityContext between requests</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="technical-overview.html#tech-intro-access-control">9.5.
											Access-Control (Authorization) in Spring Security</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="technical-overview.html#security-and-aop-advice">9.5.1.
													Security and AOP Advice</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="technical-overview.html#secure-objects">9.5.2.
													Secure Objects and the AbstractSecurityInterceptor</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="technical-overview.html#tech-intro-config-attributes">What
															are Configuration Attributes?</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="technical-overview.html#runasmanager">RunAsManager</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="technical-overview.html#afterinvocationmanager">AfterInvocationManager</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="technical-overview.html#extending-the-secure-object-model">Extending
															the Secure Object Model</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="technical-overview.html#localization">9.6.
											Localization</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="core-services.html">10.
									Core Services</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="core-services.html#core-services-authentication-manager">10.1.
											The AuthenticationManager, ProviderManager and
											AuthenticationProvider</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="core-services.html#core-services-erasing-credentials">10.1.1.
													Erasing Credentials on Successful Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="core-services.html#core-services-dao-provider">10.1.2.
													DaoAuthenticationProvider</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="core-services.html#userdetailsservice-implementations">10.2.
											UserDetailsService Implementations</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="core-services.html#core-services-in-memory-service">10.2.1.
													In-Memory Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="core-services.html#core-services-jdbc-user-service">10.2.2.
													JdbcDaoImpl</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="core-services.html#authority-groups">Authority
															Groups</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="core-services.html#core-services-password-encoding">10.3.
											Password Encoding</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="core-services.html#what-is-a-hash">10.3.1. What is
													a hash?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="core-services.html#adding-salt-to-a-hash">10.3.2.
													Adding Salt to a Hash</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="core-services.html#hashing-and-authentication">10.3.3.
													Hashing and Authentication</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
					</dl>
				</dd>
				<dt>
					<span class="part"><a href="test.html">III. Testing</a></span>
				</dt>
				<dd>
					<dl>
						<dt>
							<span class="chapter"><a href="test-method.html">11.
									Testing Method Security</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="test-method.html#test-method-setup">11.1. Security
											Test Setup</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="test-method.html#test-method-withmockuser">11.2.
											@WithMockUser</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="test-method.html#test-method-withanonymoususer">11.3.
											@WithAnonymousUser</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="test-method.html#test-method-withuserdetails">11.4.
											@WithUserDetails</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="test-method.html#test-method-withsecuritycontext">11.5.
											@WithSecurityContext</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="test-method.html#test-method-meta-annotations">11.6.
											Test Meta Annotations</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="test-mockmvc.html">12.
									Spring MVC Test Integration</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="test-mockmvc.html#test-mockmvc-setup">12.1. Setting
											Up MockMvc and Spring Security</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="test-mockmvc.html#test-mockmvc-smmrpp">12.2.
											SecurityMockMvcRequestPostProcessors</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="test-mockmvc.html#test-mockmvc-csrf">12.2.1.
													Testing with CSRF Protection</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="test-mockmvc.html#test-mockmvc-securitycontextholder">12.2.2.
													Running a Test as a User in Spring MVC Test</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="test-mockmvc.html#test-mockmvc-securitycontextholder-rpp">12.2.3.
													Running as a User in Spring MVC Test with
													RequestPostProcessor</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="test-mockmvc.html#running-as-a-user-in-spring-mvc-test-with-annotations">Running
															as a User in Spring MVC Test with Annotations</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="test-mockmvc.html#testing-http-basic-authentication">12.2.4.
													Testing HTTP Basic Authentication</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="test-mockmvc.html#securitymockmvcrequestbuilders">12.3.
											SecurityMockMvcRequestBuilders</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="test-mockmvc.html#testing-form-based-authentication">12.3.1.
													Testing Form Based Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="test-mockmvc.html#test-logout">12.3.2. Testing
													Logout</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="test-mockmvc.html#securitymockmvcresultmatchers">12.4.
											SecurityMockMvcResultMatchers</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="test-mockmvc.html#unauthenticated-assertion">12.4.1.
													Unauthenticated Assertion</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="test-mockmvc.html#authenticated-assertion">12.4.2.
													Authenticated Assertion</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
					</dl>
				</dd>
				<dt>
					<span class="part"><a href="web-app-security.html">IV.
							Web Application Security</a></span>
				</dt>
				<dd>
					<dl>
						<dt>
							<span class="chapter"><a href="security-filter-chain.html">13.
									The Security Filter Chain</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="security-filter-chain.html#delegating-filter-proxy">13.1.
											DelegatingFilterProxy</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="security-filter-chain.html#filter-chain-proxy">13.2.
											FilterChainProxy</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="security-filter-chain.html#bypassing-the-filter-chain">13.2.1.
													Bypassing the Filter Chain</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="security-filter-chain.html#filter-ordering">13.3.
											Filter Ordering</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="security-filter-chain.html#request-matching">13.4.
											Request Matching and HttpFirewall</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="security-filter-chain.html#use-with-other-filter-based-frameworks">13.5.
											Use with other Filter-Based Frameworks</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="security-filter-chain.html#filter-chains-with-ns">13.6.
											Advanced Namespace Configuration</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="core-web-filters.html">14.
									Core Security Filters</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="core-web-filters.html#filter-security-interceptor">14.1.
											FilterSecurityInterceptor</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="core-web-filters.html#exception-translation-filter">14.2.
											ExceptionTranslationFilter</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="core-web-filters.html#auth-entry-point">14.2.1.
													AuthenticationEntryPoint</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="core-web-filters.html#access-denied-handler">14.2.2.
													AccessDeniedHandler</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="core-web-filters.html#request-caching">14.2.3.
													SavedRequest s and the RequestCache Interface</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="core-web-filters.html#security-context-persistence-filter">14.3.
											SecurityContextPersistenceFilter</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="core-web-filters.html#security-context-repository">14.3.1.
													SecurityContextRepository</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="core-web-filters.html#form-login-filter">14.4.
											UsernamePasswordAuthenticationFilter</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="core-web-filters.html#form-login-flow-handling">14.4.1.
													Application Flow on Authentication Success and Failure</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="servletapi.html">15.
									Servlet API integration</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="servletapi.html#servletapi-25">15.1. Servlet 2.5+
											Integration</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-remote-user">15.1.1.
													HttpServletRequest.getRemoteUser()</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-user-principal">15.1.2.
													HttpServletRequest.getUserPrincipal()</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-user-in-role">15.1.3.
													HttpServletRequest.isUserInRole(String)</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="servletapi.html#servletapi-3">15.2. Servlet 3+
											Integration</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-authenticate">15.2.1.
													HttpServletRequest.authenticate(HttpServletRequest,HttpServletResponse)</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-login">15.2.2.
													HttpServletRequest.login(String,String)</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-logout">15.2.3.
													HttpServletRequest.logout()</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-start-runnable">15.2.4.
													AsyncContext.start(Runnable)</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-async">15.2.5. Async
													Servlet Support</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="servletapi.html#servletapi-31">15.3. Servlet 3.1+
											Integration</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="servletapi.html#servletapi-change-session-id">15.3.1.
													HttpServletRequest#changeSessionId()</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="basic.html">16. Basic
									and Digest Authentication</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="basic.html#basic-processing-filter">16.1.
											BasicAuthenticationFilter</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="basic.html#basic-config">16.1.1. Configuration</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="basic.html#digest-processing-filter">16.2.
											DigestAuthenticationFilter</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="basic.html#digest-config">16.2.1. Configuration</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="remember-me.html">17.
									Remember-Me Authentication</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="remember-me.html#remember-me-overview">17.1.
											Overview</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="remember-me.html#remember-me-hash-token">17.2.
											Simple Hash-Based Token Approach</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="remember-me.html#remember-me-persistent-token">17.3.
											Persistent Token Approach</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="remember-me.html#remember-me-impls">17.4.
											Remember-Me Interfaces and Implementations</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="remember-me.html#tokenbasedremembermeservices">17.4.1.
													TokenBasedRememberMeServices</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="remember-me.html#persistenttokenbasedremembermeservices">17.4.2.
													PersistentTokenBasedRememberMeServices</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="csrf.html">18. Cross
									Site Request Forgery (CSRF)</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a href="csrf.html#csrf-attacks">18.1.
											CSRF Attacks</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="csrf.html#synchronizer-token-pattern">18.2.
											Synchronizer Token Pattern</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="csrf.html#when-to-use-csrf-protection">18.3. When to
											use CSRF protection</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="csrf.html#csrf-protection-and-json">18.3.1. CSRF
													protection and JSON</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="csrf.html#csrf-and-stateless-browser-applications">18.3.2.
													CSRF and Stateless Browser Applications</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a href="csrf.html#csrf-using">18.4.
											Using Spring Security CSRF Protection</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="csrf.html#csrf-use-proper-verbs">18.4.1. Use
													proper HTTP verbs</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="csrf.html#csrf-configure">18.4.2. Configure CSRF
													Protection</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="csrf.html#csrf-include-csrf-token">18.4.3. Include
													the CSRF Token</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="csrf.html#csrf-include-csrf-token-form">Form
															Submissions</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="csrf.html#csrf-include-csrf-token-ajax">Ajax and
															JSON Requests</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="csrf.html#csrf-cookie">CookieCsrfTokenRepository</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a href="csrf.html#csrf-caveats">18.5.
											CSRF Caveats</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="csrf.html#csrf-timeouts">18.5.1. Timeouts</a></span>
										</dt>
										<dt>
											<span class="section"><a href="csrf.html#csrf-login">18.5.2.
													Logging In</a></span>
										</dt>
										<dt>
											<span class="section"><a href="csrf.html#csrf-logout">18.5.3.
													Logging Out</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="csrf.html#csrf-multipart">18.5.4. Multipart (file
													upload)</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="csrf.html#csrf-multipartfilter">Placing
															MultipartFilter before Spring Security</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="csrf.html#csrf-include-csrf-token-in-action">Include
															CSRF token in action</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="csrf.html#hiddenhttpmethodfilter">18.5.5.
													HiddenHttpMethodFilter</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="csrf.html#overriding-defaults">18.6. Overriding
											Defaults</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="cors.html">19. CORS</a></span>
						</dt>
						<dt>
							<span class="chapter"><a href="headers.html">20.
									Security HTTP Response Headers</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="headers.html#default-security-headers">20.1. Default
											Security Headers</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="headers.html#headers-cache-control">20.1.1. Cache
													Control</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="headers.html#headers-content-type-options">20.1.2.
													Content Type Options</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="headers.html#headers-hsts">20.1.3. HTTP Strict
													Transport Security (HSTS)</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="headers.html#headers-hpkp">20.1.4. HTTP Public Key
													Pinning (HPKP)</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="headers.html#headers-frame-options">20.1.5.
													X-Frame-Options</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="headers.html#headers-xss-protection">20.1.6.
													X-XSS-Protection</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="headers.html#headers-csp">20.1.7. Content Security
													Policy (CSP)</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="headers.html#headers-csp-configure">Configuring
															Content Security Policy</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="headers.html#headers-csp-links">Additional
															Resources</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="headers.html#headers-custom">20.2. Custom Headers</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="headers.html#headers-static">20.2.1. Static
													Headers</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="headers.html#headers-writer">20.2.2. Headers
													Writer</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="headers.html#headers-delegatingrequestmatcherheaderwriter">20.2.3.
													DelegatingRequestMatcherHeaderWriter</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="session-mgmt.html">21.
									Session Management</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="session-mgmt.html#sessionmanagementfilter">21.1.
											SessionManagementFilter</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="session-mgmt.html#sessionauthenticationstrategy">21.2.
											SessionAuthenticationStrategy</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="session-mgmt.html#concurrent-sessions">21.3.
											Concurrency Control</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="session-mgmt.html#list-authenticated-principals">21.3.1.
													Querying the SessionRegistry for currently authenticated
													users and their sessions</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="anonymous.html">22.
									Anonymous Authentication</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="anonymous.html#anonymous-overview">22.1. Overview</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="anonymous.html#anonymous-config">22.2. Configuration</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="anonymous.html#anonymous-auth-trust-resolver">22.3.
											AuthenticationTrustResolver</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="websocket.html">23.
									WebSocket Security</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="websocket.html#websocket-configuration">23.1.
											WebSocket Configuration</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="websocket.html#websocket-authentication">23.2.
											WebSocket Authentication</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="websocket.html#websocket-authorization">23.3.
											WebSocket Authorization</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="websocket.html#websocket-authorization-notes">23.3.1.
													WebSocket Authorization Notes</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="websocket.html#websocket-authorization-notes-messagetypes">WebSocket
															Authorization on Message Types</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="websocket.html#websocket-authorization-notes-destinations">WebSocket
															Authorization on Destinations</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="websocket.html#websocket-authorization-notes-outbound">23.3.2.
													Outbound Messages</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="websocket.html#websocket-sameorigin">23.4. Enforcing
											Same Origin Policy</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="websocket.html#websocket-sameorigin-why">23.4.1.
													Why Same Origin?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="websocket.html#websocket-sameorigin-spring">23.4.2.
													Spring WebSocket Allowed Origin</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="websocket.html#websocket-sameorigin-csrf">23.4.3.
													Adding CSRF to Stomp Headers</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="websocket.html#websocket-sameorigin-disable">23.4.4.
													Disable CSRF within WebSockets</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="websocket.html#websocket-sockjs">23.5. Working with
											SockJS</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="websocket.html#websocket-sockjs-sameorigin">23.5.1.
													SockJS &amp; frame-options</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="websocket.html#websocket-sockjs-csrf">23.5.2.
													SockJS &amp; Relaxing CSRF</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
					</dl>
				</dd>
				<dt>
					<span class="part"><a href="authorization.html">V.
							Authorization</a></span>
				</dt>
				<dd>
					<dl>
						<dt>
							<span class="chapter"><a href="authz-arch.html">24.
									Authorization Architecture</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="authz-arch.html#authz-authorities">24.1. Authorities</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="authz-arch.html#authz-pre-invocation">24.2.
											Pre-Invocation Handling</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="authz-arch.html#authz-access-decision-manager">24.2.1.
													The AccessDecisionManager</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="authz-arch.html#authz-voting-based">24.2.2.
													Voting-Based AccessDecisionManager Implementations</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="authz-arch.html#authz-role-voter">RoleVoter</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="authz-arch.html#authz-authenticated-voter">AuthenticatedVoter</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="authz-arch.html#authz-custom-voter">Custom
															Voters</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="authz-arch.html#authz-after-invocation-handling">24.3.
											After Invocation Handling</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="authz-arch.html#authz-hierarchical-roles">24.4.
											Hierarchical Roles</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="secure-object-impls.html">25.
									Secure Object Implementations</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="secure-object-impls.html#aop-alliance">25.1. AOP
											Alliance (MethodInvocation) Security Interceptor</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="secure-object-impls.html#explicit-methodsecurityinterceptor-configuration">25.1.1.
													Explicit MethodSecurityInterceptor Configuration</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="secure-object-impls.html#aspectj">25.2. AspectJ
											(JoinPoint) Security Interceptor</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="el-access.html">26.
									Expression-Based Access Control</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a href="el-access.html#overview">26.1.
											Overview</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="el-access.html#el-common-built-in">26.1.1. Common
													Built-In Expressions</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="el-access.html#el-access-web">26.2. Web Security
											Expressions</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="el-access.html#el-access-web-beans">26.2.1.
													Referring to Beans in Web Security Expressions</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="el-access.html#el-access-web-path-variables">26.2.2.
													Path Variables in Web Security Expressions</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="el-access.html#method-security-expressions">26.3.
											Method Security Expressions</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="el-access.html#el-pre-post-annotations">26.3.1.
													@Pre and @Post Annotations</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="el-access.html#access-control-using-preauthorize-and-postauthorize">Access
															Control using @PreAuthorize and @PostAuthorize</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="el-access.html#filtering-using-prefilter-and-postfilter">Filtering
															using @PreFilter and @PostFilter</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="el-access.html#el-method-built-in">26.3.2.
													Built-In Expressions</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="el-access.html#el-permission-evaluator">The
															PermissionEvaluator interface</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="el-access.html#method-security-meta-annotations">Method
															Security Meta Annotations</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
							</dl>
						</dd>
					</dl>
				</dd>
				<dt>
					<span class="part"><a href="advanced-topics.html">VI.
							Additional Topics</a></span>
				</dt>
				<dd>
					<dl>
						<dt>
							<span class="chapter"><a href="domain-acls.html">27.
									Domain Object Security (ACLs)</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="domain-acls.html#domain-acls-overview">27.1.
											Overview</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="domain-acls.html#domain-acls-key-concepts">27.2. Key
											Concepts</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="domain-acls.html#domain-acls-getting-started">27.3.
											Getting Started</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="preauth.html">28.
									Pre-Authentication Scenarios</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="preauth.html#pre-authentication-framework-classes">28.1.
											Pre-Authentication Framework Classes</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="preauth.html#abstractpreauthenticatedprocessingfilter">28.1.1.
													AbstractPreAuthenticatedProcessingFilter</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="preauth.html#j2ee-preauth-details">J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="preauth.html#preauthenticatedauthenticationprovider">28.1.2.
													PreAuthenticatedAuthenticationProvider</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="preauth.html#http403forbiddenentrypoint">28.1.3.
													Http403ForbiddenEntryPoint</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="preauth.html#concrete-implementations">28.2.
											Concrete Implementations</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="preauth.html#request-header-authentication-siteminder">28.2.1.
													Request-Header Authentication (Siteminder)</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="preauth.html#siteminder-example-configuration">Siteminder
															Example Configuration</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="preauth.html#java-ee-container-authentication">28.2.2.
													Java EE Container Authentication</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="ldap.html">29. LDAP
									Authentication</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a href="ldap.html#ldap-overview">29.1.
											Overview</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="ldap.html#using-ldap-with-spring-security">29.2.
											Using LDAP with Spring Security</a></span>
								</dt>
								<dt>
									<span class="section"><a href="ldap.html#ldap-server">29.3.
											Configuring an LDAP Server</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="ldap.html#using-an-embedded-test-server">29.3.1.
													Using an Embedded Test Server</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ldap.html#using-bind-authentication">29.3.2. Using
													Bind Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ldap.html#loading-authorities">29.3.3. Loading
													Authorities</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="ldap.html#implementation-classes">29.4.
											Implementation Classes</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="ldap.html#ldap-ldap-authenticators">29.4.1.
													LdapAuthenticator Implementations</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ldap.html#ldap-ldap-authenticators-common">Common
															Functionality</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="ldap.html#ldap-ldap-authenticators-bind">BindAuthenticator</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="ldap.html#ldap-ldap-authenticators-password">PasswordComparisonAuthenticator</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="ldap.html#ldap-context-source">29.4.2. Connecting
													to the LDAP Server</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ldap.html#ldap-searchobjects">29.4.3. LDAP Search
													Objects</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ldap.html#ldap-searchobjects-filter">FilterBasedLdapUserSearch</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="ldap.html#ldap-authorities">29.4.4.
													LdapAuthoritiesPopulator</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ldap.html#ldap-bean-config">29.4.5. Spring Bean
													Configuration</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="ldap.html#ldap-custom-user-details">29.4.6. LDAP
													Attributes and Customized UserDetails</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="ldap.html#ldap-active-directory">29.5. Active
											Directory Authentication</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="ldap.html#activedirectoryldapauthenticationprovider">29.5.1.
													ActiveDirectoryLdapAuthenticationProvider</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="ldap.html#active-directory-error-codes">Active
															Directory Error Codes</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="taglibs.html">30. JSP
									Tag Libraries</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="taglibs.html#declaring-the-taglib">30.1. Declaring
											the Taglib</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="taglibs.html#taglibs-authorize">30.2. The authorize
											Tag</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="taglibs.html#disabling-tag-authorization-for-testing">30.2.1.
													Disabling Tag Authorization for Testing</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="taglibs.html#the-authentication-tag">30.3. The
											authentication Tag</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="taglibs.html#the-accesscontrollist-tag">30.4. The
											accesscontrollist Tag</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="taglibs.html#the-csrfinput-tag">30.5. The csrfInput
											Tag</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="taglibs.html#the-csrfmetatags-tag">30.6. The
											csrfMetaTags Tag</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="jaas.html">31. Java
									Authentication and Authorization Service (JAAS) Provider</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a href="jaas.html#overview-2">31.1.
											Overview</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="jaas.html#jaas-abstractjaasauthenticationprovider">31.2.
											AbstractJaasAuthenticationProvider</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="jaas.html#jaas-callbackhandler">31.2.1. JAAS
													CallbackHandler</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jaas.html#jaas-authoritygranter">31.2.2. JAAS
													AuthorityGranter</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="jaas.html#jaas-defaultjaasauthenticationprovider">31.3.
											DefaultJaasAuthenticationProvider</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="jaas.html#jaas-inmemoryconfiguration">31.3.1.
													InMemoryConfiguration</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="jaas.html#jaas-djap-config">31.3.2.
													DefaultJaasAuthenticationProvider Example Configuration</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="jaas.html#jaas-jaasauthenticationprovider">31.4.
											JaasAuthenticationProvider</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="jaas.html#jaas-apiprovision">31.5. Running as a
											Subject</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="cas.html">32. CAS
									Authentication</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a href="cas.html#cas-overview">32.1.
											Overview</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="cas.html#cas-how-it-works">32.2. How CAS Works</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a href="cas.html#cas-sequence">32.2.1.
													Spring Security and CAS Interaction Sequence</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a href="cas.html#cas-client">32.3.
											Configuration of CAS Client</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a href="cas.html#cas-st">32.3.1.
													Service Ticket Authentication</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="cas.html#cas-singlelogout">32.3.2. Single Logout</a></span>
										</dt>
										<dt>
											<span class="section"><a href="cas.html#cas-pt-client">32.3.3.
													Authenticating to a Stateless Service with CAS</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="cas.html#cas-pt-client-config">Configuring CAS
															to Obtain Proxy Granting Tickets</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="cas.html#cas-pt-client-sample">Calling a
															Stateless Service Using a Proxy Ticket</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a href="cas.html#cas-pt">32.3.4.
													Proxy Ticket Authentication</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="x509.html">33. X.509
									Authentication</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a href="x509.html#x509-overview">33.1.
											Overview</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="x509.html#adding-x-509-authentication-to-your-web-application">33.2.
											Adding X.509 Authentication to Your Web Application</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="x509.html#x509-ssl-config">33.3. Setting up SSL in
											Tomcat</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="runas.html">34. Run-As
									Authentication Replacement</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="runas.html#runas-overview">34.1. Overview</a></span>
								</dt>
								<dt>
									<span class="section"><a href="runas.html#runas-config">34.2.
											Configuration</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="crypto.html">35. Spring
									Security Crypto Module</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="crypto.html#spring-security-crypto-introduction">35.1.
											Introduction</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="crypto.html#spring-security-crypto-encryption">35.2.
											Encryptors</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="crypto.html#spring-security-crypto-encryption-bytes">35.2.1.
													BytesEncryptor</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="crypto.html#spring-security-crypto-encryption-text">35.2.2.
													TextEncryptor</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="crypto.html#spring-security-crypto-keygenerators">35.3.
											Key Generators</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="crypto.html#byteskeygenerator">35.3.1.
													BytesKeyGenerator</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="crypto.html#stringkeygenerator">35.3.2.
													StringKeyGenerator</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="crypto.html#spring-security-crypto-passwordencoders">35.4.
											Password Encoding</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="concurrency.html">36.
									Concurrency Support</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="concurrency.html#delegatingsecuritycontextrunnable">36.1.
											DelegatingSecurityContextRunnable</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="concurrency.html#delegatingsecuritycontextexecutor">36.2.
											DelegatingSecurityContextExecutor</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="concurrency.html#spring-security-concurrency-classes">36.3.
											Spring Security Concurrency Classes</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="mvc.html">37. Spring
									MVC Integration</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="mvc.html#mvc-enablewebmvcsecurity">37.1.
											@EnableWebMvcSecurity</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="mvc.html#mvc-requestmatcher">37.2. MvcRequestMatcher</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="mvc.html#mvc-authentication-principal">37.3.
											@AuthenticationPrincipal</a></span>
								</dt>
								<dt>
									<span class="section"><a href="mvc.html#mvc-async">37.4.
											Spring MVC Async Integration</a></span>
								</dt>
								<dt>
									<span class="section"><a href="mvc.html#mvc-csrf">37.5.
											Spring MVC and CSRF Integration</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="mvc.html#automatic-token-inclusion">37.5.1.
													Automatic Token Inclusion</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="mvc.html#mvc-csrf-resolver">37.5.2. Resolving the
													CsrfToken</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
					</dl>
				</dd>
				<dt>
					<span class="part"><a href="data.html">VII. Spring Data
							Integration</a></span>
				</dt>
				<dd>
					<dl>
						<dt>
							<span class="chapter"><a href="data-configuration.html">38.
									Spring Data &amp; Spring Security Configuration</a></span>
						</dt>
						<dt>
							<span class="chapter"><a href="data-query.html">39.
									Security Expressions within @Query</a></span>
						</dt>
					</dl>
				</dd>
				<dt>
					<span class="part"><a href="appendix.html">VIII.
							Appendix</a></span>
				</dt>
				<dd>
					<dl>
						<dt>
							<span class="chapter"><a href="appendix-schema.html">40.
									Security Database Schema</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="appendix-schema.html#user-schema">40.1. User Schema</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-schema.html#group-authorities">40.1.1.
													Group Authorities</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="appendix-schema.html#persistent-login-remember-me-schema">40.2.
											Persistent Login (Remember-Me) Schema</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-schema.html#dbschema-acl">40.3. ACL Schema</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-schema.html#hypersql">40.3.1. HyperSQL</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-schema.html#postgresql">40.3.2.
													PostgreSQL</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-schema.html#mysql-and-mariadb">40.3.3.
													MySQL and MariaDB</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-schema.html#microsoft-sql-server">40.3.4.
													Microsoft SQL Server</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-schema.html#oracle-database">40.3.5.
													Oracle Database</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="appendix-namespace.html">41.
									The Security Namespace</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="appendix-namespace.html#nsa-web">41.1. Web
											Application Security</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-debug">41.1.1.
													&lt;debug&gt;</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-http">41.1.2.
													&lt;http&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-http-attributes">&lt;http&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-http-children">Child
															Elements of &lt;http&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-access-denied-handler">41.1.3.
													&lt;access-denied-handler&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-access-denied-handler-parents">Parent
															Elements of &lt;access-denied-handler&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-access-denied-handler-attributes">&lt;access-denied-handler&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-cors">41.1.4.
													&lt;cors&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-cors-attributes">&lt;cors&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-cors-parents">Parent
															Elements of &lt;cors&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-headers">41.1.5.
													&lt;headers&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-headers-attributes">&lt;headers&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-headers-parents">Parent
															Elements of &lt;headers&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-headers-children">Child
															Elements of &lt;headers&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-cache-control">41.1.6.
													&lt;cache-control&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-cache-control-attributes">&lt;cache-control&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-cache-control-parents">Parent
															Elements of &lt;cache-control&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-hsts">41.1.7.
													&lt;hsts&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-hsts-attributes">&lt;hsts&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-hsts-parents">Parent
															Elements of &lt;hsts&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-hpkp">41.1.8.
													&lt;hpkp&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-hpkp-attributes">&lt;hpkp&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-hpkp-parents">Parent
															Elements of &lt;hpkp&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-pins">41.1.9.
													&lt;pins&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-pins-children">Child
															Elements of &lt;pins&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-pin">41.1.10.
													&lt;pin&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-pin-attributes">&lt;pin&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-pin-parents">Parent
															Elements of &lt;pin&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-content-security-policy">41.1.11.
													&lt;content-security-policy&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-content-security-policy-attributes">&lt;content-security-policy&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-content-security-policy-parents">Parent
															Elements of &lt;content-security-policy&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-frame-options">41.1.12.
													&lt;frame-options&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-frame-options-attributes">&lt;frame-options&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-frame-options-parents">Parent
															Elements of &lt;frame-options&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-xss-protection">41.1.13.
													&lt;xss-protection&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-xss-protection-attributes">&lt;xss-protection&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-xss-protection-parents">Parent
															Elements of &lt;xss-protection&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-content-type-options">41.1.14.
													&lt;content-type-options&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-content-type-options-attributes">&lt;content-type-options&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-content-type-options-parents">Parent
															Elements of &lt;content-type-options&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-header">41.1.15.
													&lt;header&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-header-attributes">&lt;header-attributes&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-header-parents">Parent
															Elements of &lt;header&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-anonymous">41.1.16.
													&lt;anonymous&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-anonymous-parents">Parent
															Elements of &lt;anonymous&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-anonymous-attributes">&lt;anonymous&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-csrf">41.1.17.
													&lt;csrf&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-csrf-parents">Parent
															Elements of &lt;csrf&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-csrf-attributes">&lt;csrf&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-custom-filter">41.1.18.
													&lt;custom-filter&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-custom-filter-parents">Parent
															Elements of &lt;custom-filter&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-custom-filter-attributes">&lt;custom-filter&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-expression-handler">41.1.19.
													&lt;expression-handler&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-expression-handler-parents">Parent
															Elements of &lt;expression-handler&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-expression-handler-attributes">&lt;expression-handler&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-form-login">41.1.20.
													&lt;form-login&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-form-login-parents">Parent
															Elements of &lt;form-login&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-form-login-attributes">&lt;form-login&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-http-basic">41.1.21.
													&lt;http-basic&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-http-basic-parents">Parent
															Elements of &lt;http-basic&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-http-basic-attributes">&lt;http-basic&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-http-firewall">41.1.22.
													&lt;http-firewall&gt; Element</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-http-firewall-attributes">&lt;http-firewall&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-intercept-url">41.1.23.
													&lt;intercept-url&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-intercept-url-parents">Parent
															Elements of &lt;intercept-url&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-intercept-url-attributes">&lt;intercept-url&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-jee">41.1.24.
													&lt;jee&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-jee-parents">Parent
															Elements of &lt;jee&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-jee-attributes">&lt;jee&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-logout">41.1.25.
													&lt;logout&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-logout-parents">Parent
															Elements of &lt;logout&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-logout-attributes">&lt;logout&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-openid-login">41.1.26.
													&lt;openid-login&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-openid-login-parents">Parent
															Elements of &lt;openid-login&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-openid-login-attributes">&lt;openid-login&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-openid-login-children">Child
															Elements of &lt;openid-login&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-attribute-exchange">41.1.27.
													&lt;attribute-exchange&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-attribute-exchange-parents">Parent
															Elements of &lt;attribute-exchange&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-attribute-exchange-attributes">&lt;attribute-exchange&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-attribute-exchange-children">Child
															Elements of &lt;attribute-exchange&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-openid-attribute">41.1.28.
													&lt;openid-attribute&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-openid-attribute-parents">Parent
															Elements of &lt;openid-attribute&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-openid-attribute-attributes">&lt;openid-attribute&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-port-mappings">41.1.29.
													&lt;port-mappings&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-port-mappings-parents">Parent
															Elements of &lt;port-mappings&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-port-mappings-children">Child
															Elements of &lt;port-mappings&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-port-mapping">41.1.30.
													&lt;port-mapping&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-port-mapping-parents">Parent
															Elements of &lt;port-mapping&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-port-mapping-attributes">&lt;port-mapping&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-remember-me">41.1.31.
													&lt;remember-me&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-remember-me-parents">Parent
															Elements of &lt;remember-me&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-remember-me-attributes">&lt;remember-me&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-request-cache">41.1.32.
													&lt;request-cache&gt; Element</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-request-cache-parents">Parent
															Elements of &lt;request-cache&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-request-cache-attributes">&lt;request-cache&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-session-management">41.1.33.
													&lt;session-management&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-session-management-parents">Parent
															Elements of &lt;session-management&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-session-management-attributes">&lt;session-management&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-session-management-children">Child
															Elements of &lt;session-management&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-concurrency-control">41.1.34.
													&lt;concurrency-control&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-concurrency-control-parents">Parent
															Elements of &lt;concurrency-control&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-concurrency-control-attributes">&lt;concurrency-control&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-x509">41.1.35.
													&lt;x509&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-x509-parents">Parent
															Elements of &lt;x509&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-x509-attributes">&lt;x509&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-filter-chain-map">41.1.36.
													&lt;filter-chain-map&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-filter-chain-map-attributes">&lt;filter-chain-map&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-filter-chain-map-children">Child
															Elements of &lt;filter-chain-map&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-filter-chain">41.1.37.
													&lt;filter-chain&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-filter-chain-parents">Parent
															Elements of &lt;filter-chain&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-filter-chain-attributes">&lt;filter-chain&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-filter-security-metadata-source">41.1.38.
													&lt;filter-security-metadata-source&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-filter-security-metadata-source-attributes">&lt;filter-security-metadata-source&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-filter-security-metadata-source-children">Child
															Elements of &lt;filter-security-metadata-source&gt;</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="appendix-namespace.html#nsa-websocket-security">41.2.
											WebSocket Security</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-websocket-message-broker">41.2.1.
													&lt;websocket-message-broker&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-websocket-message-broker-attributes">&lt;websocket-message-broker&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-websocket-message-broker-children">Child
															Elements of &lt;websocket-message-broker&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-intercept-message">41.2.2.
													&lt;intercept-message&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-intercept-message-parents">Parent
															Elements of &lt;intercept-message&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-intercept-message-attributes">&lt;intercept-message&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="appendix-namespace.html#nsa-authentication">41.3.
											Authentication Services</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-authentication-manager">41.3.1.
													&lt;authentication-manager&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-authentication-manager-attributes">&lt;authentication-manager&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-authentication-manager-children">Child
															Elements of &lt;authentication-manager&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-authentication-provider">41.3.2.
													&lt;authentication-provider&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-authentication-provider-parents">Parent
															Elements of &lt;authentication-provider&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-authentication-provider-attributes">&lt;authentication-provider&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-authentication-provider-children">Child
															Elements of &lt;authentication-provider&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-jdbc-user-service">41.3.3.
													&lt;jdbc-user-service&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-jdbc-user-service-attributes">&lt;jdbc-user-service&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-password-encoder">41.3.4.
													&lt;password-encoder&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-password-encoder-parents">Parent
															Elements of &lt;password-encoder&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-password-encoder-attributes">&lt;password-encoder&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-password-encoder-children">Child
															Elements of &lt;password-encoder&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-salt-source">41.3.5.
													&lt;salt-source&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-salt-source-parents">Parent
															Elements of &lt;salt-source&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-salt-source-attributes">&lt;salt-source&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-user-service">41.3.6.
													&lt;user-service&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-user-service-attributes">&lt;user-service&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-user-service-children">Child
															Elements of &lt;user-service&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-user">41.3.7.
													&lt;user&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-user-parents">Parent
															Elements of &lt;user&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-user-attributes">&lt;user&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="appendix-namespace.html#nsa-method-security">41.4.
											Method Security</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-global-method-security">41.4.1.
													&lt;global-method-security&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-global-method-security-attributes">&lt;global-method-security&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-global-method-security-children">Child
															Elements of &lt;global-method-security&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-after-invocation-provider">41.4.2.
													&lt;after-invocation-provider&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-after-invocation-provider-parents">Parent
															Elements of &lt;after-invocation-provider&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-after-invocation-provider-attributes">&lt;after-invocation-provider&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-pre-post-annotation-handling">41.4.3.
													&lt;pre-post-annotation-handling&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-pre-post-annotation-handling-parents">Parent
															Elements of &lt;pre-post-annotation-handling&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-pre-post-annotation-handling-children">Child
															Elements of &lt;pre-post-annotation-handling&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-invocation-attribute-factory">41.4.4.
													&lt;invocation-attribute-factory&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-invocation-attribute-factory-parents">Parent
															Elements of &lt;invocation-attribute-factory&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-invocation-attribute-factory-attributes">&lt;invocation-attribute-factory&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-post-invocation-advice">41.4.5.
													&lt;post-invocation-advice&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-post-invocation-advice-parents">Parent
															Elements of &lt;post-invocation-advice&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-post-invocation-advice-attributes">&lt;post-invocation-advice&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-pre-invocation-advice">41.4.6.
													&lt;pre-invocation-advice&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-pre-invocation-advice-parents">Parent
															Elements of &lt;pre-invocation-advice&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-pre-invocation-advice-attributes">&lt;pre-invocation-advice&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-protect-pointcut">41.4.7.
													Securing Methods using</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-protect-pointcut-parents">Parent
															Elements of &lt;protect-pointcut&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-protect-pointcut-attributes">&lt;protect-pointcut&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-intercept-methods">41.4.8.
													&lt;intercept-methods&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-intercept-methods-attributes">&lt;intercept-methods&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-intercept-methods-children">Child
															Elements of &lt;intercept-methods&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-method-security-metadata-source">41.4.9.
													&lt;method-security-metadata-source&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-method-security-metadata-source-attributes">&lt;method-security-metadata-source&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-method-security-metadata-source-children">Child
															Elements of &lt;method-security-metadata-source&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-protect">41.4.10.
													&lt;protect&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-protect-parents">Parent
															Elements of &lt;protect&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-protect-attributes">&lt;protect&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="appendix-namespace.html#nsa-ldap">41.5. LDAP
											Namespace Options</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-ldap-server">41.5.1.
													Defining the LDAP Server using the</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-ldap-server-attributes">&lt;ldap-server&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-ldap-authentication-provider">41.5.2.
													&lt;ldap-authentication-provider&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-ldap-authentication-provider-parents">Parent
															Elements of &lt;ldap-authentication-provider&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-ldap-authentication-provider-attributes">&lt;ldap-authentication-provider&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-ldap-authentication-provider-children">Child
															Elements of &lt;ldap-authentication-provider&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-password-compare">41.5.3.
													&lt;password-compare&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-password-compare-parents">Parent
															Elements of &lt;password-compare&gt;</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-password-compare-attributes">&lt;password-compare&gt;
															Attributes</a></span>
												</dt>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-password-compare-children">Child
															Elements of &lt;password-compare&gt;</a></span>
												</dt>
											</dl>
										</dd>
										<dt>
											<span class="section"><a
												href="appendix-namespace.html#nsa-ldap-user-service">41.5.4.
													&lt;ldap-user-service&gt;</a></span>
										</dt>
										<dd>
											<dl>
												<dt>
													<span class="section"><a
														href="appendix-namespace.html#nsa-ldap-user-service-attributes">&lt;ldap-user-service&gt;
															Attributes</a></span>
												</dt>
											</dl>
										</dd>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="appendix-dependencies.html">42.
									Spring Security Dependencies</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-core-2">42.1.
											spring-security-core</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-remoting-2">42.2.
											spring-security-remoting</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-web-2">42.3.
											spring-security-web</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-ldap-2">42.4.
											spring-security-ldap</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-config-2">42.5.
											spring-security-config</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-acl-2">42.6.
											spring-security-acl</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-cas-2">42.7.
											spring-security-cas</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-openid-2">42.8.
											spring-security-openid</a></span>
								</dt>
								<dt>
									<span class="section"><a
										href="appendix-dependencies.html#spring-security-taglibs">42.9.
											spring-security-taglibs</a></span>
								</dt>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="appendix-faq.html">43.
									Spring Security FAQ</a></span>
						</dt>
						<dd>
							<dl>
								<dt>
									<span class="section"><a
										href="appendix-faq.html#appendix-faq-general-questions">43.1.
											General Questions</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-other-concerns">43.1.1.
													Will Spring Security take care of all my application
													security requirements?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-web-xml">43.1.2.
													Why not just use web.xml security?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-requirements">43.1.3.
													What Java and Spring Framework versions are required?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-start-simple">43.1.4.
													I’m new to Spring Security and I need to build an
													application that supports CAS single sign-on over HTTPS,
													while allowing Basic authentication locally for certain
													URLs, authenticating against multiple back end user
													information sources (LDAP and JDBC). I’ve copied some
													configuration files I found but it doesn’t work. What could
													be wrong?</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="appendix-faq.html#appendix-faq-common-problems">43.2.
											Common Problems</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-bad-credentials">43.2.1.
													When I try to log in, I get an error message that says "Bad
													Credentials". What’s wrong?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-login-loop">43.2.2.
													My application goes into an "endless loop" when I try to
													login, what’s going on?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-anon-access-denied">43.2.3.
													I get an exception with the message "Access is denied (user
													is anonymous);". What’s wrong?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-cached-secure-page">43.2.4.
													Why can I still see a secured page even after I’ve logged
													out of my application?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#auth-exception-credentials-not-found">43.2.5.
													I get an exception with the message "An Authentication
													object was not found in the SecurityContext". What’s wrong?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-ldap-authentication">43.2.6.
													I can’t get LDAP authentication to work. What’s wrong with
													my configuration?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#session-management">43.2.7.
													Session Management</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-concurrent-session-same-browser">43.2.8.
													I’m using Spring Security’s concurrent session control to
													prevent users from logging in more than once at a time.
													When I open another browser window after logging in, it
													doesn’t stop me from logging in again. Why can I log in
													more than once?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-new-session-on-authentication">43.2.9.
													Why does the session Id change when I authenticate through
													Spring Security?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-tomcat-https-session">43.2.10.
													I’m using Tomcat (or some other servlet container) and have
													enabled HTTPS for my login page, switching back to HTTP
													afterwards. It doesn’t work - I just end up back at the
													login page after authenticating.</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#i-m-not-switching-between-http-and-https-but-my-session-is-still-getting-lost">43.2.11.
													I’m not switching between HTTP and HTTPS but my session is
													still getting lost</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-session-listener-missing">43.2.12.
													I’m trying to use the concurrent session-control support
													but it won’t let me log back in, even if I’m sure I’ve
													logged out and haven’t exceeded the allowed sessions.</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-unwanted-session-creation">43.2.13.
													Spring Security is creating a session somewhere, even
													though I’ve configured it not to, by setting the
													create-session attribute to never.</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-forbidden-csrf">43.2.14.
													I get a 403 Forbidden when performing a POST</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-no-security-on-forward">43.2.15.
													I’m forwarding a request to another URL using the
													RequestDispatcher, but my security constraints aren’t being
													applied.</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-method-security-in-web-context">43.2.16.
													I have added Spring Security’s
													&lt;global-method-security&gt; element to my application
													context but if I add security annotations to my Spring MVC
													controller beans (Struts actions etc.) then they don’t seem
													to have an effect.</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-no-filters-no-context">43.2.17.
													I have a user who has definitely been authenticated, but
													when I try to access the SecurityContextHolder during some
													requests, the Authentication is null. Why can’t I see the
													user information?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-method-security-with-taglib">43.2.18.
													The authorize JSP Tag doesn’t respect my method security
													annotations when using the URL attribute.</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="appendix-faq.html#appendix-faq-architecture">43.3.
											Spring Security Architecture Questions</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-where-is-class-x">43.3.1.
													How do I know which package class X is in?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-namespace-to-bean-mapping">43.3.2.
													How do the namespace elements map to conventional bean
													configurations?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-role-prefix">43.3.3.
													What does "ROLE_" mean and why do I need it on my role
													names?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-what-dependencies">43.3.4.
													How do I know which dependencies to add to my application
													to work with Spring Security?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-apacheds-deps">43.3.5.
													What dependencies are needed to run an embedded ApacheDS
													LDAP server?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-what-is-userdetailservice">43.3.6.
													What is a UserDetailsService and do I need one?</a></span>
										</dt>
									</dl>
								</dd>
								<dt>
									<span class="section"><a
										href="appendix-faq.html#appendix-faq-howto">43.4. Common
											"Howto" Requests</a></span>
								</dt>
								<dd>
									<dl>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-extra-login-fields">43.4.1.
													I need to login in with more information than just the
													username. How do I add support for extra login fields (e.g.
													a company name)?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-matching-url-fragments">43.4.2.
													How do I apply different intercept-url constraints where
													only the fragment value of the requested URLs differs
													(e.g./foo#bar and /foo#blah?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-request-details-in-user-service">43.4.3.
													How do I access the user’s IP Address (or other web-request
													data) in a UserDetailsService?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-access-session-from-user-service">43.4.4.
													How do I access the HttpSession from a UserDetailsService?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-password-in-user-service">43.4.5.
													How do I access the user’s password in a
													UserDetailsService?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-dynamic-url-metadata">43.4.6.
													How do I define the secured URLs within an application
													dynamically?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-ldap-authorities">43.4.7.
													How do I authenticate against LDAP but load user roles from
													a database?</a></span>
										</dt>
										<dt>
											<span class="section"><a
												href="appendix-faq.html#appendix-faq-namespace-post-processor">43.4.8.
													I want to modify the property of a bean that is created by
													the namespace, but there is nothing in the schema to
													support it. What can I do short of abandoning namespace
													use?</a></span>
										</dt>
									</dl>
								</dd>
							</dl>
						</dd>
						<dt>
							<span class="chapter"><a href="m3to4.html">44.
									Migrating from 3.x to 4.x</a></span>
						</dt>
					</dl>
				</dd>
			</dl>
		</div>
	</div>
	<div class="navfooter">
		<hr>
		<table width="100%" summary="Navigation footer">
			<tbody>
				<tr>
					<td width="40%" align="left">&nbsp;</td>
					<td width="20%" align="center">&nbsp;</td>
					<td width="40%" align="right">&nbsp;<a accesskey="n"
						href="pr01.html">Next</a></td>
				</tr>
				<tr>
					<td width="40%" align="left" valign="top">&nbsp;</td>
					<td width="20%" align="center">&nbsp;</td>
					<td width="40%" align="right" valign="top">&nbsp;</td>
				</tr>
			</tbody>
		</table>
	</div>
<!-- 	<script type="text/javascript" async=""
		src="http://www.google-analytics.com/plugins/ga/inpage_linkid.js"
		id="undefined"></script>
	<script type="text/javascript" async=""
		src="http://www.google-analytics.com/ga.js"></script>
	<script type="text/javascript">
        var _gaq = _gaq || [];
        var pluginUrl = '//www.google-analytics.com/plugins/ga/inpage_linkid.js';
        _gaq.push(
            ['_require', 'inpage_linkid', pluginUrl],
            ['_setAccount', 'UA-2728886-23'],
            ['_trackPageview'],
            ['b._require', 'inpage_linkid', pluginUrl],
            ['b._setAccount', 'UA-2728886-19'],
            ['b._trackPageview']
        );
        (function() {
            var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
        })();
    </script>

	<script type="text/javascript"> -->

<!-- function detectLastFrame() {
   var thisF = this.window;
   var allF = parent.top.frames;
   return allF[allF.length - 1] == thisF; }

// Only inject the following code if this is a normal page or the last // frame of a frameset.
if (parent.top.frames.length == 0 || detectLastFrame()) {
    // Inject the SpringSource search widget too.
    document.write(unescape("%3Cscript src='http://search.springsource.org/widget/searchtool.js' type='text/javascript'%3E%3C/script%3E"));
    // Inject the Eloqua code
    //document.write(unescape("%3Cscript src='/elqNow/elqCfg.js' type='text/javascript'%3E%3C/script%3E"));
    //document.write(unescape("%3Cscript src='/elqNow/elqImg.js' type='text/javascript'%3E%3C/script%3E"));

} -->
<!-- </script> -->
	<script src="${pageContext.request.contextPath}/public-resources/js/searchtool.js" type="text/javascript"></script>

	<div id="bodyEmptyFiller">&nbsp;</div>
	<div id="searchBar">
		<div id="collapseLink" style="left: -1px; bottom: 1px;">
			<a href="javascript:void(0);"><img border="0" class="springClass"
				src="${pageContext.request.contextPath}/public-resources/images/ArrowLeft_spring.png"></a>
		</div>
		<div id="logoSpan" class="springClass">
			<a target="_new" href="http://spring.io"><img class="springClass"
				border="0"
				src="${pageContext.request.contextPath}/public-resources/images/Logo_spring_SearchBar.png"></a>
		</div>
		<div id="searchFields">
			<input name="searchKeys" type="text" id="searchKeys"
				value="Search Documentation" style="color: rgb(204, 204, 204);">
			<select name="searchWhat" id="searchWhat"><option
					value="Spring">Spring</option></select> 
					<img title="Search Documentation"
						src="${pageContext.request.contextPath}/public-resources/images/Btn_Search_spring.png"
						id="searchLens">
		</div>
		<div id="resultsDiv"></div>
		<div id="daAd">
			<div class="PivotalAdBannerDiv"></div>
		</div>
	</div>
</body>
</html>