//package com.java.test;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//
//
//public class JacksonTester {
//	public static void main(String args[]){
//		JacksonTester tester = new JacksonTester();
//
//		try {
//			Student student = new Student();
//			student.setAge(10);
//			student.setName("Mahesh");
//			tester.writeJSON(student);
//
//			Student student1 = tester.readJSON();
//			System.out.println(student1);
//
//		} 
//		catch (JsonParseException e) { e.printStackTrace(); }
//		catch (JsonMappingException e) { e.printStackTrace(); }
//		catch (IOException e) { e.printStackTrace(); }
//	}
//
//	private void writeJSON(Student student) throws JsonGenerationException, JsonMappingException, IOException{
//		ObjectMapper mapper = new ObjectMapper();	
//		mapper.writeValue(new File("student.json"), student);
//	}
//
//	private Student readJSON() throws JsonParseException, JsonMappingException, IOException{
//		ObjectMapper mapper = new ObjectMapper();
//		Student student = mapper.readValue(new File("student.json"), Student.class);
//		return student;
//	}
//}
