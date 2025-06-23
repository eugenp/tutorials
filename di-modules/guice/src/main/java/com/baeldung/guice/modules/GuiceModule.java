package com.baeldung.guice.modules;

import com.baeldung.guice.common.AccountService;
import com.baeldung.guice.common.AccountServiceImpl;
import com.baeldung.guice.common.BookService;
import com.baeldung.guice.common.BookServiceImpl;
import com.baeldung.guice.common.PersonDao;
import com.baeldung.guice.common.PersonDaoImpl;
import com.baeldung.guice.Foo;
import com.baeldung.guice.Person;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;

public class GuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		try {
			bind(AccountService.class).to(AccountServiceImpl.class);
			bind(Person.class).toConstructor(Person.class.getConstructor());
			// bind(Person.class).toProvider(new Provider<Person>() {
			// public Person get() {
			// Person p = new Person();
			// return p;
			// }
			// });
			bind(Foo.class).toProvider(new Provider<Foo>() {
				public Foo get() {
					return new Foo();
				}
			});
			bind(PersonDao.class).to(PersonDaoImpl.class);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Provides
	public BookService bookServiceGenerator() {
		return new BookServiceImpl();
	}

}
