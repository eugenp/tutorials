package com.baeldung.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.repo.languages.Translator;

public class DictionaryAutowiredService {
	@Autowired
	private Translator translator;
	
	public void translate(){
		translator.translate();
	}
}
