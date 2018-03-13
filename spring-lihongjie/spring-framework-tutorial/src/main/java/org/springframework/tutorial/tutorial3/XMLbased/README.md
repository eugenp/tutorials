基于 XML 文件 的配置方式

对于基于 XML 的配置， Spring1.0 的配置文件采用 DTD 格式，Spring 2.0 以后使用Schema的格式，后者让不同类型的配置拥有自己的命名空间，使配置文件更具有扩展性。此外，Spring基于Schema配置方案为许多领域的问题提供了简化的配置方法，配置工作因此得到了大幅简化。

Bean配置信息定义了Bean的实现及依赖关系，Spring容器根据各种形式的Bean配置信息在容器内部建立Bean定义注册表，然后根据注册表加载 实例化Bean,并建立Bean 和Bean的依赖关系，最后将这些就绪的Bean放到缓存池中，以供外层的应用程序调用。

采取基于Schema配置格式， 文件头的声明会复杂一些 
<?xml version="1.0" encoding="UTF-8" ?>
<beans 
	xmlns="http://www.springframework.org/schema/beans" <!--默认命名空间 -->
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" <!--xsi标准命名空间，用于指定自定义命名空间的Schema文件 -->
    <!--自定义命名空间, aop是该命名空间的简称 -->
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:context="http://www.springframework.org/schema/context"
    <!-- 为每个命名空间指定具体的Schema文件-->
    xsi:schemaLocation=
        "http://www.springframework.org/schema/beans
       	http://www.springframework.org/schema/beans/spring-beans-3.2.xsd"> 
    
    <!-- 默认命名空间的配置 -->
    <bean id="foo" class="com.xxx.Foo"/>
    
    <aop:config>
        <aop:advisor pointut="execution(**..PetStoreFacade.*(..))" advice-ref="txAdvice"/>
    </aop>

</beans>

Spring 是一个基于容器的框架，但是如果你没有配置Spring，那它是一个空容器， 所以配置Spring来告诉容器它需要加载哪些Bean和如何装配这些Bean。

在<beans>元素内  ，可以放置所有的Spring配置信息，包括<bean>元素的声明。 但是<beans>命名空间并不是你遇到的唯一的Spring命名空间。

Spring的核心框架自带了10个命名空间配置， 如：



Java 自带了多种XML命名空间，通过这些命名空间可以配置Spring容器

<table>
<tr>
    <th>命名空间</th>
    <th>用途</th>
</tr>
<tr>
    <td>aop</td>
    <td>为声明切面以及将@AspectJ注解的类代理为Spring切面提供了配置元素</td>
</tr>
<tr>
    <td>beans</td>
    <td>支持声明Bean和装配Bean， 是Spring最核心的也是最原始的命名空间</td>
</tr>
<tr>
    <td>context</td>
    <td>为配置Spring应用上下文提供了配置元素，包括自动检测和自动装配Bean 注入非Spring 直接管理的对象</td>
</tr>
<tr>
    <td>jee</td>
    <td>提供了与Java EE API 的集成，例如 JNDI 和 EJB</td>
</tr>
<tr>
    <td>jms</td>
    <td>为声明消息驱动的POJO提供了配置元素</td>
</tr>
<tr>
    <td>lang</td>
    <td>支持配置由Groovy JRuby 或 BeanShell 等脚本实现的Bean </td>
</tr>
<tr>
    <td>mvc</td>
    <td>启动Spring MVC 的能力， 例如面向注解的控制器 视图控制器 和拦截器</td>
</tr>
<tr>
    <td>oxm</td>
    <td>支持Spring的对象到XML映射配置</td>
</tr>
<tr>
    <td>tx</td>
    <td>提供声明式事务配置</td>
</tr>
<tr>
    <td>util</td>
    <td>提供各种各样的工具类元素，包括把集合配置为Bean 支持属性占位符</td>
</tr>
</table>


在spring-idol.xml 配置文件中
<bean id="duke" class="org.springframework.tutorial.tutorial3.XMLbased.Juggler">

Duke 被定义为一个Spring Bean, id 属性 定义了Bean的名字， 也作为该Bean 在Spring 容器中的引用。
根据 class 属性得知， duke 是一个Juggler.

详细：

当Spring 容器加载该Bean时，Spring 将使用默认的构造器来实例化 duke Bean ， 实际上，duke 会使用如下代码来创建 ： （实际上， Spring 是使用反射来创建Bean的）

new org.springframework.tutorial.tutorial3.XMLbased.Juggler();


Spring bean tag properties (attributes)

<table>
<tr>
    <th>名称</th>
    <th>描述</th>
    <th>Example</th>
</tr>
<tr>
    <td>name / id</td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td>class</td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td>scope</td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td>constructor-arg</td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td>properties</td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td>autowiring mode</td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td>lazy-init(lazy-initialization mode)</td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td>init-method(initialization method)</td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td>destroy-method(destruction method)</td>
    <td></td>
    <td></td>
</tr>
</table>


通过构造器注入


##通过工厂方法创建Bean

有时候静态工厂方法是实例化对象的唯一方法。Spring支持通过<bean>元素factory-method 属性来装配工厂创建的Bean。


在Spring中将一个单例类配置为Bean ， 单例类的实例只能通过静态工厂方法来创建， 例子中 Stage类是典型的单例类。

<bean>元素有一个factory-method属性， 允许我们调用一个指定的静态方法，从而代替构造方法来创建一个类的实例。（配置方式见例子）

特别地，在Spring中如何使用factory-method将单例类配置为Bean， 如果要装配的对象需要通过静态方法来创建，那么这种配置可以适用于任何场景。











