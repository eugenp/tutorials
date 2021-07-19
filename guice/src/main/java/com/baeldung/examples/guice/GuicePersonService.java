package com.baeldung.examples.guice;

import com.baeldung.examples.common.PersonDao;
import com.google.inject.Inject;

public class GuicePersonService {

	@Inject
	private PersonDao personDao;

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

}
