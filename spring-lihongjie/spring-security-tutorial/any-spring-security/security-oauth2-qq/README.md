### 准备工作
1、在 [QQ互联](https://connect.qq.com/index.html) 申请成为开发者，并创建应用，得到APP ID 和 APP Key。

2、了解QQ登录时的 [网站应用接入流程](http://wiki.connect.qq.com/%E7%BD%91%E7%AB%99%E5%BA%94%E7%94%A8%E6%8E%A5%E5%85%A5%E6%B5%81%E7%A8%8B)。（必须看完看懂）

为了方便各位测试，直接把我自己申请的贡献出来：

参数|值
---| :---:
APP ID|101386962
APP Key|2a0f820407df400b84a854d054be8b6a
回调地址|http://www.ictgu.cn/login/qq

> 提醒：因为回调地址不是 http://localhost ，所以在启动我提供的demo时，需要在host文件中添加一行：
127.0.0.1 www.ictgu.cn

### Github 地址
https://github.com/ChinaSilence/any-spring-security

### 运行应用
1、进入 security-oauth2-qq 目录，执行：
```
mvn spring-boot:run
```
2、此处假设你已经修改好host，并启动成功，访问 http://www.ictgu.cn

3、登录 -> QQ登录 -> 个人中心，将会看到个人信息。
![登录界面](http://upload-images.jianshu.io/upload_images/3424642-101f4bc4a543a45d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![个人信息](http://upload-images.jianshu.io/upload_images/3424642-8cb35f7aa7e55674.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


4、删除host中添加的那一行。

### 相关说明

> 腾讯官网原话：
openid是此网站上唯一对应用户身份的标识，网站可将此ID进行存储便于用户下次登录时辨识其身份，或将其与用户在网站上的原有账号进行绑定。

通过QQ登录获取的 openid 用于与自己网站的账号一一对应。

### 相关文章
- [Spring Security 入门：登录与退出](http://www.jianshu.com/p/a8e317e82425)
- [Spring Security 入门：自定义 Filter](http://www.jianshu.com/p/deb512b41f99)

### 相关资料
- [Spring Security Architecture](https://spring.io/guides/topicals/spring-security-architecture/)
- [What is authentication in Spring Security?](http://docs.spring.io/spring-security/site/docs/5.0.0.M2/reference/htmlsingle/#tech-intro-authentication)
