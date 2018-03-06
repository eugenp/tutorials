前置文章
----------
[Spring Security 入门：基于数据库验证](http://www.jianshu.com/p/d425f8031f83)

Github 地址
-----
https://github.com/ChinaSilence/any-spring-security
本文对应 [security-login-db-encryptPWD](https://github.com/ChinaSilence/any-spring-security/tree/master/security-login-db-encryptPWD)

摘要
-----
解决2个问题：
- 注册时密码加密后存入数据库
- 登录时密码加密校验

运行程序
------
1、clone 代码
```
git clone https://github.com/ChinaSilence/any-spring-security.git
```

2、启动应用
```
mvn spring-boot:run
```

3、登录（使用账号 anoy 密码 pwd，未使用密码加密前是可以登录的）
![登录失败](http://upload-images.jianshu.io/upload_images/3424642-fcb2967ef7237848.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

控制台会出现如下提示：
```
  Encoded password does not look like BCrypt
```

4、注册新账号并登录。

![登录成功](http://upload-images.jianshu.io/upload_images/3424642-0ca3defd9403f1a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

相关解释说明
--------
相比于上一个demo，在 `WebSecurityConfig` 中添加了如下代码：
```
/**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(anyUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 密码加密
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

```

> BCryptPasswordEncoder相关知识：

> 用户表的密码通常使用MD5等不可逆算法加密后存储，为防止彩虹表破解更会先使用一个特定的字符串（如域名）加密，然后再使用一个随机的salt（盐值）加密。

 > 特定字符串是程序代码中固定的，salt是每个密码单独随机，一般给用户表加一个字段单独存储，比较麻烦。

  > BCrypt算法将salt随机并混入最终加密后的密码，验证时也无需单独提供之前的salt，从而无需单独处理salt问题。

#### BCryptPasswordEncoder 是在哪里使用的？
登录时用到了 `DaoAuthenticationProvider` ，它有一个方法
`#additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)`，此方法用来校验从数据库取得的用户信息和用户输入的信息是否匹配。

#### 在注册时，对用户密码加密
应用 `BCryptPasswordEncoder ` 之后，明文密码是无法被识别的，就会校验失败，只有存入密文密码才能被正常识别。所以，应该在注册时对用户密码进行加密。
```
/**
     * 加密密码
     */
    private void encryptPassword(UserEntity userEntity){
        String password = userEntity.getPassword();
        password = new BCryptPasswordEncoder().encode(password);
        userEntity.setPassword(password);
    }
```
新用户注册后，数据库中就会存入密文密码，示例：

id|username|password|nickname|roles
--|--|--|--|--
5|testpwd|$2a$10$i9fKauPB/mUh8pA2xHTzN.LSAu5pqmfEboNqK6y2NU9PxAt80hLc2|加密测试|ROLE_USER

> 补充说明：即使不同的用户注册时输入相同的密码，存入数据库的密文密码也会不同。

其他
-----
    如需转载，请联系作者，邮箱 545544032@qq.com
    开源社区 http://spring4all.com 欢迎你
    个人网站 http://www.ictgu.cn （SpringBoot开发并开源）
