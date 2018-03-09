####Bean Scopes

When defining a <bean> in Spring, you have the option of declaring a scope for that bean. For example, To force Spring to produce a new bean instance each time one is needed, you should declare the bean's scope attribute to be prototype. Similar way if you want Spring to return the same bean instance each time one is needed, you should declare the bean's scope attribute to be singleton.

The Spring Framework supports following five scopes, three of which are available only if you use a web-aware ApplicationContext.

<table>
<tr>
<th>Scope</th>
<th>Description</th>
<th>描述</th>
</tr>
<tr>
<td>single</td>
<td>This scopes the bean definition to a single instance per Spring IoC container (default).</td>
<td>在Spring IoC容器中仅存在一个Bean实例，Bean以单实例的方式存在</td>
</tr>
<tr>
<td>prototype</td>
<td>This scopes a single bean definition to have any number of object instances.</td>
<td>每次从容器中调用Bean时，都返回一个新的实例，即每次调用getBean()时，相当于 new XxxBean()的操作</td>
</tr>
<tr>
    <td>request</td>
    <td>This scopes a bean definition to an HTTP request. Only valid in the context of a web-aware Spring ApplicationContext.</td>
    <td>每次HTTP请求都会创建一个新的Bean。 该作用域仅使用于WebApplicationContext环境</td>
</tr>
<tr>
    <td>session</td>
    <td>This scopes a bean definition to an HTTP session. Only valid in the context of a web-aware Spring ApplicationContext.</td>
    <td>同一个HTTP Session 共享一个Bean， 不同 HTTP Session 使用不同的Bean,该作用域仅适用于WebApplicationContext环境</td>
</tr>
<tr>
    <td>globalSession</td>
    <td>This scopes a bean definition to a global HTTP session. Only valid in the context of a web-aware Spring ApplicationContext.</td>
    <td>同一个全局Session 共享一个Bean ， 一般用于 Portet 应用环境。 该作用域仅适用于 WebApplicationContext环境</td>
</tr>
</table>

低版本由于只有两个Bean作用域，所以采用 singleton="true false"的配置方式，Spring 为了向后兼容，依旧支持这种配置方式。 推荐使用新的配置方式： scope="作用域类型".

####The singleton scope: singleton 作用域

<!-- A bean definition with singleton scope -->
<bean id="..." class="..." scope="singleton">
    <!-- collaborators and configuration for this bean go here -->
</bean>



####The prototype scope: prototype 作用域

<!-- A bean definition with prototype scope -->
<bean id="..." class="..." scope="prototype">
    <!-- collaborators and configuration for this bean go here -->
</bean>
