https://www.mkyong.com/spring-mvc/spring-3-mvc-and-json-example/
Spring 3 MVC and JSON example
By mkyong | July 28, 2011 | Updated : May 28, 2013 | Viewed : 673,532 times +1,827 pv/w

In this tutorial, we show you how to output JSON data in Spring MVC framework.

Technologies used :

Spring 3.2.2.RELEASE
Jackson 1.9.10
JDK 1.6
Eclipse 3.6
Maven 3
P.S In Spring 3, to output JSON data, just puts Jackson library in the project classpath.

1. Project Dependencies
Get Jackson and Spring dependencies.

pom.xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
        http://maven.apache.org/maven-v4_0_0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.mkyong.common</groupId>
	<artifactId>SpringMVC</artifactId>
	<packaging>war</packaging>
	<version>1.0-SNAPSHOT</version>
	<name>SpringMVC Json Webapp</name>
	<url>http://maven.apache.org</url>

	<properties>
		<spring.version>3.2.2.RELEASE</spring.version>
		<jackson.version>1.9.10</jackson.version>
		<jdk.version>1.6</jdk.version>
	</properties>

	<dependencies>

		<!-- Spring 3 dependencies -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-web</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>${spring.version}</version>
		</dependency>

		<!-- Jackson JSON Mapper -->
		<dependency>
			<groupId>org.codehaus.jackson</groupId>
			<artifactId>jackson-mapper-asl</artifactId>
			<version>${jackson.version}</version>
		</dependency>

	</dependencies>

	<build>
		<finalName>SpringMVC</finalName>
		<plugins>
		  <plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-eclipse-plugin</artifactId>
			<version>2.9</version>
			<configuration>
				<downloadSources>true</downloadSources>
				<downloadJavadocs>false</downloadJavadocs>
				<wtpversion>2.0</wtpversion>
			</configuration>
		  </plugin>
		  <plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-compiler-plugin</artifactId>
			<version>2.3.2</version>
			<configuration>
				<source>${jdk.version}</source>
				<target>${jdk.version}</target>
			</configuration>
		  </plugin>
		</plugins>
	</build>

</project>

 
2. Model
A simple POJO, later output this object as formatted JSON data.

package com.mkyong.common.model;

public class Shop {

	String name;
	String staffName[];

	//getter and setter methods

}

 
3. Controller
Add @ResponseBody as return value. Wen Spring sees

Jackson library is existed in the project classpath
The mvc:annotation-driven is enabled
Return method annotated with @ResponseBody
Spring will handle the JSON conversion automatically.

JSONController.java
package com.mkyong.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mkyong.common.model.Shop;

@Controller
@RequestMapping("/kfc/brands")
public class JSONController {

	@RequestMapping(value="{name}", method = RequestMethod.GET)
	public @ResponseBody Shop getShopInJSON(@PathVariable String name) {

		Shop shop = new Shop();
		shop.setName(name);
		shop.setStaffName(new String[]{"mkyong1", "mkyong2"});

		return shop;

	}

}
4. mvc:annotation-driven
Enable mvc:annotation-driven in your Spring configuration XML file.

mvc-dispatcher-servlet.xml
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context-3.0.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd">

	<context:component-scan base-package="com.mkyong.common.controller" />

	<mvc:annotation-driven />

</beans>
5. Demo
URL : http://localhost:8080/SpringMVC/rest/kfc/brands/kfc-kampar

