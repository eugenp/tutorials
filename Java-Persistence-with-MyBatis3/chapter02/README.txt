Chapter 2: Bootstrapping MyBatis
=======================================
This module, chapter02, is a maven based java project to demonstrate the following approaches to configure and bootstrap MyBatis.
	. Configuration using XML 
	. Configuration using Java API.

Note: You can create MySQL Database tables using scripts in src/main/resources/sql folder.	

How to Run:
	1. Configure Database Connection properties like hostname, username and password in src/main/resources/application.properties file.
	2. Run StudentServiceTest JUnit Test class by using the appropriate configuration style in com.mybatis3.services.StudentServiceTest.setup() method.
		