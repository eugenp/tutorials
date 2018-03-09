当scope = request 时，
在访问/scope 后不会更改message的值。
即：
/hello, 输出 Hello,
/scope, 输出 Good morning
/hello, 输出 Hello

当scope = session 时，
在访问/scope 后， session生命周期内会改变message的值。
即：
/hello, 输出 Hello
/scope, 输出 Good morning
/hello, 输出 Good morning
关闭浏览器后，session 失效, 再次打开/hello ，输出 Hello