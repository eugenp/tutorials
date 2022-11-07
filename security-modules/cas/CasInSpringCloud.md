# Zuul上实现CAS的单点登录

Zuul功能角色

- 路由配置
- 负载均衡
- 日志生成
- 鉴权：同样由于外部服务都经过Zuul，因此对于SpringCloud体系来说做CAS的单点登录的集成Zuul是最合适不过的。

Zuul基于SpringBoot，可以使用Spring Security的套路实现CAS的拦截与验证等工作。

主要操作：

- 将CAS集成放到Zuul上
- 使用Spring Security套餐

关键：Zuul核心是ZuulFilter，而Spring Security实质上也是一系列的Filter来处理，把这两套Fillter理清楚是搞定这个问题的先决条件。

Maven pom

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zw.se2</groupId>
    <artifactId>demo-zuul</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>hy-zuul</name>
    <description>Demo project for Zuul and CAS</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.1.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>Finchley.RC1</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>net.sf.json-lib</groupId>
            <artifactId>json-lib</artifactId>
            <version>2.4</version>
            <classifier>jdk15</classifier>
        </dependency>
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session</artifactId>
            <version>1.3.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-cas</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-taglibs</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>
</project>
```

Zuul application.properties

```properties
#默认情况下，敏感的头信息无法经过API网关进行传递,需要开启。解决使用zuul网关后spring session无效问题
zuul.routes.tim-service.sensitiveHeaders="*"
zuul.routes.main-service.path=/main-service/**
zuul.routes.main-service.url=http://localhost:8383 
zuul.routes.main-service.sensitiveHeaders="*"
devMode=false
spring.application.name=demo-zuul-server
#下面那个参数是去掉zuul-prefix参数产生的前缀的，跟path一毛钱关系都没有
zuul.strip-prefix=false
server.port=8085

#将 hystrix 的超时时间禁用掉
hystrix.command.default.execution.timeout.enabled=false
#session存储
spring.session.store-type=none
#日志配置文件路径
logging.config=ext/conf/logback.xml

#CAS服务地址
cas.server.host.url=http://10.0.4.53:8080/cas-server-webapp-3.5.0
#CAS服务登录地址
cas.server.host.login_url=${cas.server.host.url}/login
#应用访问地址
app.server.host.url=http://localhost:8085
#应用登录地址
app.login.url=/cas/login/zuul
```

启动配置bootstrap.yaml

```yaml
eureka:
  client:
    service-url:
        defaultZone: http://localhost:8797/eureka
spring:
  cloud:
    config:
      uri: http://localhost:8888
      profile: dev
      name: hyConfig
```

cas CasProperties.java

```java
package com.zw.se2.hy.zuul.cas.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ZEW on 2018/6/7.
 */
@Data
@Component
public class CasProperties {
    @Value("${cas.server.host.url}")
    private String casServerUrl;

    @Value("${cas.server.host.login_url}")
    private String casServerLoginUrl;

    @Value("${app.server.host.url}")
    private String appServerUrl;

    @Value("${app.login.url}")
    private String appLoginUrl;
}
```

CAS SecurityConfig.java

配置的核心，配置拦截策略和处理过滤器

```java
package com.zw.se2.hy.zuul.cas.config;

import com.zw.se2.hy.zuul.cas.custom.CustomUserDetailsService;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

@Configuration
@EnableWebSecurity //启用web权限
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法验证
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CasProperties casProperties;

    /**定义认证用户信息获取来源，密码校验规则等*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(casAuthenticationProvider());
    }

    /**定义安全策略*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//配置安全策略
                .antMatchers("/**/api/**").permitAll()
                .antMatchers("/**/**.html").permitAll()
                .anyRequest().authenticated();//定义/请求不需要验证

        http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint())
                .and()
                .addFilter(casAuthenticationFilter())
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
//这个地方需要注意，这里禁用是为了照顾需要使用post请求且这个请求是外部系统提供的，没有办法，
//为了安全还是推荐不禁用转而使用csrf-token的模式
        http.csrf().disable(); 
    }

    /**认证的入口*/
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(casProperties.getCasServerLoginUrl());
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    /**指定service相关信息*/
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(casProperties.getAppServerUrl() + casProperties.getAppLoginUrl());
        serviceProperties.setAuthenticateAllArtifacts(true);
        return serviceProperties;
    }

    /**CAS认证过滤器*/
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setFilterProcessesUrl(casProperties.getAppLoginUrl());
        return casAuthenticationFilter;
    }

    /**cas 认证 Provider*/
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
       //这里实现了一个自定义的认证服务，其实没有特殊需求可以采用默认的服务 casAuthenticationProvider.setAuthenticationUserDetailsService(customUserDetailsService());

        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("an_id_for_this_auth_provider_only");
        return casAuthenticationProvider;
    }

    /*@Bean
    public UserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }*/

    /**用户自定义的AuthenticationUserDetailsService*/
    @Bean
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService(){
        return new CustomUserDetailsService();
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new Cas20ServiceTicketValidator(casProperties.getCasServerUrl());
    }

    // 自定义的认证服务
}
```

在loadUserDetails函数中要对userInfo的权限相关信息赋值，否则就不能通过验证。

```java
 UserInfo userInfo = new UserInfo();
        userInfo.setUsername(token.getName());
        userInfo.setName(token.getName());
        Set<AuthorityInfo> authorities = new HashSet<>();
        AuthorityInfo authorityInfo = new AuthorityInfo("CAS");
        authorities.add(authorityInfo);
        userInfo.setAuthorities(authorities);
        userInfo.setAccountNonLocked(true);
        userInfo.setAccountNonExpired(true);
        userInfo.setCredentialsNonExpired(true);
```

集成完CAS，该处理Zuul本身的过滤器，经过试验CAS的过滤器优先级要高于Zuul的Pre过滤器，只需要处理登录等日志，也就是增加一个post过滤器。

如下所示 LoginResponseFilter.java

```java
package com.zw.se2.hy.zuul.filter.post;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zw.se2.hy.zuul.UserLoginInfoCache;
import com.zw.se2.hy.zuul.filter.ConstantPath;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

@Component
public class LoginResponseFilter extends ZuulFilter {
      private static Logger log = LoggerFactory.getLogger("monitor");


    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        String url = context.getRequest().getRequestURL().toString();
        //只处理登录请求
        return StringUtils.endsWith(url, ConstantPath.LOGIN_PATH);

    }

    @Override
    public Object run() {
        try {
            RequestContext context = RequestContext.getCurrentContext();
            InputStream stream = context.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            String url = context.getRequest().getRequestURL().toString();

                if (StringUtils.isNotBlank(body)) {
                    //验证响应结果是否为登录成功
                    JSONObject bodyJson = JSONObject.fromObject(body);
                    if (bodyJson.has(ConstantPath.LOGIN_RESPONSE_STATUS)) {
                        String status = bodyJson.getString(ConstantPath.LOGIN_RESPONSE_STATUS);
                        if (StringUtils.equals(status, "200")) {
                            if (bodyJson.has(ConstantPath.LOGIN_RESPONSE_RESULT)) {
                                JSONArray resultArray = bodyJson.getJSONArray(ConstantPath.LOGIN_RESPONSE_RESULT);
                                if (resultArray != null && resultArray.size() > 0) {
                                    JSONObject userObject = resultArray.getJSONObject(0);
                                    processLogin(context, userObject);
                                }
                            }
                        }


                }
            }

            context.setResponseBody(body);
        } catch (IOException e) {
            rethrowRuntimeException(e);
        }
        return null;
    } 

    private void processLogin(RequestContext context, JSONObject userObject) {
        if (userObject.has(ConstantPath.LOGIN_USERNAME)) {
            String userName = userObject.getString(ConstantPath.LOGIN_USERNAME);
            //将用户名存储在session中，判断用户是否登录
            HttpServletRequest request = context.getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("userName", userName);
            session.setMaxInactiveInterval(1800);           
            log.info(">>>用户>>>" + userName + "执行了>>>登录>>>操作);
        }
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

}
```

