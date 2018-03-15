package com.baeldung;

import javax.jws.WebService;

@WebService
public interface CodeFirstService {
	String showPersonDetail(String text, Integer age);
}
