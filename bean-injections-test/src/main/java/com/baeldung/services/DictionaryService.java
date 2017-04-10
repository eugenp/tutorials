package com.baeldung.services;

import com.baeldung.repo.languages.Translator;


public class DictionaryService {	
	

	private Translator translator;

	public DictionaryService(Translator translator) {		
		this.translator = translator;
	}

	public void translate(){
		translator.translate();
	}
	
	/*
	 * to setter-based injection use this
	public void setTranslator(Translator translator) {
		this.translator = translator;
	}
	*/
	
}
