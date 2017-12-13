package com.baeldung;

import javax.jws.WebService;

import com.google.gson.Gson;


public class SimpleServiceImpl implements SimpleService {

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

	@Override
	public String addNumbers(Integer arg1, Integer arg2) {
		// TODO Auto-generated method stub				
		return String.valueOf(arg1+arg2);
	}
	
}