package com.baeldung.guice;

import com.baeldung.common.PersonDao;
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
