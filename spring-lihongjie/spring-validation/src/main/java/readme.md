binder.setValidator() : 设置自定义的验证器。

@Valid 告诉Spring验证 user对象，BindingResult 对象保存验证结果包括发生的错误信息，而且BingdingReult 必须放在待验证对象参数的后面，否则Spring 会抛异常。

form:errors 输出错误信息。
