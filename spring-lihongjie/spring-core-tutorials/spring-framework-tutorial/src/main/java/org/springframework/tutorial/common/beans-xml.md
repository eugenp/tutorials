beans XML 配置文件详解

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.2.xsd"
	default-autowire-candidates="" default-autowire="default"
	default-destroy-method="" default-init-method="" default-lazy-init="default"
	default-merge="default" profile="">

	<alias name="" alias="" />
	<import resource="" />
	<bean></bean>
	<beans></beans>
	<description></description>

	<bean id="helloWorld" class="org.springframework.tutorial.tutorial5.HelloWorld"
		init-method="init" destroy-method="destroy" abstract="true"
		autowire-candidate="default" autowire="default" depends-on=""
		factory-bean="" factory-method="" lazy-init="default" name="" parent=""
		primary="true" scope="">
		<constructor-arg index="" name="" ref="" type="" value="">
			<array merge="default" value-type="">
			</array>
			<bean></bean>
			<description></description>
			<idref bean="" local="" />
			<list merge="default" value-type=""></list>
			<map key-type="" merge="default" value-type=""></map>
			<null></null>
			<props merge="default" value-type=""></props>
			<ref bean="" local="" parent="" />
			<set merge="default" value-type=""></set>
			<value type=""></value>
		</constructor-arg>

		<lookup-method bean="" name="" />
		<meta key="" value="" />
		<property name="" ref="" value=""></property>
		<qualifier type="org.springframework.beans.factory.annotation.Qualifier" value="">
			<attribute key="" value="" />
		</qualifier>
		<replaced-method name="" replacer="">
			<arg-type match=""></arg-type>
		</replaced-method>
		<description></description>
		<property name="message" value="Hello lihongjie!" />
	</bean>


</beans>



