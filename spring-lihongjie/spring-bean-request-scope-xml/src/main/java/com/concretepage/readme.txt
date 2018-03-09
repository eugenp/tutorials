http://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch04s04.html

A 包含 B

1. A 为 singleton B 为 singleton  
不需要 <aop:scoped-proxy />
启动时 输出message 2次
请求时 输出message 无

2. A 为 singleton B 为 singleton  
需要 <aop:scoped-proxy />
启动时 输出message 2次
请求时 输出message 无

3. A 为 prototype B 为 singleton  
不需要 <aop:scoped-proxy />
启动时 输出message 2次
请求时 输出message 无

4. A 为 prototype B 为 singleton  
需要 <aop:scoped-proxy />
启动时 输出message 2次
请求时 输出message 无


-----------------------------------------
1. A 为 singleton B 为 prototype  
不需要 <aop:scoped-proxy />
启动时 输出message 2次
请求时 输出message 无

2. A 为 singleton B 为 request  
不需要 <aop:scoped-proxy />
启动报错

3. A 为 singleton B 为 session  
不需要 <aop:scoped-proxy />
启动报错

4. A 为 singleton B 为 globalSession  
不需要 <aop:scoped-proxy />
启动报错

1. A 为 singleton B 为 prototype  
需要 <aop:scoped-proxy />
启动时 输出message 无
请求多次时 输出message 多次

2. A 为 singleton B 为 request  
需要 <aop:scoped-proxy />
启动时 输出message 无
请求多次时 输出message 多次

3. A 为 singleton B 为 session  
需要 <aop:scoped-proxy />
启动时 输出message 无
请求多次时 输出message 1次

4. A 为 singleton B 为 globalSession  
需要 <aop:scoped-proxy />
启动时 输出message 无
请求多次时 输出message 1次



