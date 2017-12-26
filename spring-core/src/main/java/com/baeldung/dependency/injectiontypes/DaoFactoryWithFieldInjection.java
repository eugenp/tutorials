package com.baeldung.dependency.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

class DaoFactoryWithFieldInjection {

	@Autowired
	private Dao databaseDao;

	@Autowired
	private Dao fileDao;

	Dao create(boolean shouldUseDatabase) {
		if (shouldUseDatabase) {
			return databaseDao;
		}
		return fileDao;
	}

}
