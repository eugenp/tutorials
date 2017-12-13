package com.baeldung;

import javax.jws.WebService;

import com.google.gson.Gson;

@WebService(
		endpointInterface = "com.baeldung.CodeFirstService", 
		serviceName = "CodeFirstService")
public class CodeFirstServiceImpl implements CodeFirstService {

	// TODO Auto-generated method stub
	public String showPersonDetail(String name, Integer age) {

		Person person = new Person();
		person.setAge(age);
		person.setCity("Dhaka");
		person.setCountry("Bangladesh");
		person.setName(name);

		Gson gson = new Gson();
		String jsonperson = gson.toJson(person);

		return jsonperson;
	}
	
}