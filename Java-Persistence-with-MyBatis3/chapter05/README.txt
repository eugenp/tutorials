Chapter 5: Integration with Spring
This chapter explains how to integration MyBatis with Spring framework.
Topics covered:
	. Configuring MyBatis in Spring ApplicationContext
	. Injecting SqlSession and SQL Mappers
	. Transaction Management using Spring
	
How to Run:
	Update the database properties in application.properties file.
	You can run the JUnit tests in src/test/java folder.
	In JUnit Tests we have Database Initialization logic to setup sample data.
	
	public class StudentServiceTest 
	{
		@BeforeClass
		public static void setup() {
			studentService = new StudentService();
			TestDataPopulator.initDatabase(); // this will drop and re-create database and populates sample data.
		}
	}
