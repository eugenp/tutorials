Chapter 4: SQL Mappers using Annotations
========================================
This chapter describes mapping SQL statements and query results to java beans in SQL Mappers using Annotation based approach.
Topics covered:			
	•	CRUD Mapping
	•	ResultSet Mapping
	•	One-To-One, One-To-Many mappings
	•	Dynamic SQL mapping	

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
