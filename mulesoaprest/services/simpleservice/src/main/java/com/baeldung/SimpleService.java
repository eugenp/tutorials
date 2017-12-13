package com.baeldung;

import javax.jws.WebService;

public interface SimpleService {

	String addNumbers(Integer arg1, Integer arg2);

	String showPersonDetail(String name, Integer age);
}
