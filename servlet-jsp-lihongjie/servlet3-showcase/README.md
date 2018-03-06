##servlet3.x新特性示例：
* 最近JavaEE 7也发布了，其包含Servlet3.1规范，目前glassfish4已经实现了该规范，所以写了一个参考示例方便参考学习。建议学习之前，请先阅读Servlet3规范，效果更佳。

###1、更加便利的注解支持
* 提供了@WebServlet、@WebFilter、@WebListener、@WebInitParam等注解的支持

###2、可插性设计
#####2.1、web模块化
* 可以将一个项目分成N个模块，然后通过扫描模块下的META-INF/web-fragment.xml进行装配

#####2.2、容器启动时可插拔
* 使用ServletContainerInitializer实现，可以在容器启动时自动回调其onStartup方法，插入一些功能

#####2.3、零XML化SpringMVC
* 使用ServletContainerInitializer即SpringMVC注解配置实现无XML化的SpringMVC配置

###3、异步处理支持
#####3.1、servlet的异步支持
* 通过Servlet提供的异步支持完成了comet：streaming(长连接)和ajax长轮询
* 使用Servlet提供的AsyncListener进行状态回调
* 最后通过ajax长轮询实现了一个聊天室功能

#####3.2、SpringMVC对Servlet的异步支持
* 使用SpringMVC框架提供的异步支持实现comet：streaming(长连接)和ajax长轮询
* 使用SpringMVC框架提供的Callable实现异步计算
* 使用SpringMVC框架提供的DeferredResult实现延迟结果（实现ajax长轮询）
* Spring框架没有提供长连接实现，具体还得使用原生支持
* 最后通过ajax长轮询实现了一个聊天室功能

###4、其他
#####4.1、API改进
* 比如提供HttpOnly支持、HttpServletRequest#getRequestedSessionId直接获取请求时的会话ID、HttpServletResponse#getStatus等直接获取响应状态码、响应头等信息
* 比如Servlet3.1的request/response#getContentLengthLong得到long型内容长度、ServletContext#getVirtualServerName得到虚拟主机名
* 比如Servlet3.1的通过HttpServletRequest#changeSessionId()直接更改会话ID，并可以通过HttpSessionIdListener监听
* 其他的请参考源代码

#####4.2、获取静态资源
* 可以直接获取Jar包里的/META-INF/resources下的资源(包括jsp)

#####4.3、全新的文件上传支持
* 使用HttpServletRequest#getPart/#getParts得到请求的Part(如文件/参数)等

#####4.4、servlet 3.1的非阻塞I/O支持
* 提供WriteListener、ReadListener实现非阻塞I/O支持

#####4.5、servlet 3.1的协议升级支持
* 使用HttpServletRequest.upgrade和HttpUpgradeHandler实现HTTP/1.1协议升级，如升级到Websocket等等
* 示例实现了一个直接与socket通信的回显和时间功能

###5、pjax与bigpipe
* 使用pjax(ajax + pushState) 无刷新ajax（并能记录ajax加载的历史记录）加载内容
* 使用bigpipe并发加载页面片段，并使用springmvc抽象bigpipe，简化bigpipe开发，可以基于此完善的更易使用

具体请参考源代码，欢迎一起探讨学习。有问题请回复。

###几点说明
* 本系列使用Maven构建，需要Maven基础；
* 本系列并没有写学习指南，作者认为直接看代码即可，如有不懂的可参考如《Servlet 3.1规范》和javadoc；
* servlet3.1目前仅glassfish支持，在运行servlet3.1的程序时，请使用glassfish maven插件运行。

