package org.baeldung.scopes;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScopesTest {

	private static final String NAME = "John Smith";
	private static final int AGE = 30;

	private static final String NAME_OTHER = "Anna Jones";
	private static final int AGE_OTHER = 40;

	@Test
	public void testScopeSingleton() {
		final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("scopes.xml");

		final Person personSingletonA = (Person) applicationContext.getBean("personSingleton");
		final Person personSingletonB = (Person) applicationContext.getBean("personSingleton");

		personSingletonA.setName(NAME);
		personSingletonB.setAge(AGE);

		Assert.assertEquals(NAME, personSingletonB.getName());
		Assert.assertEquals(AGE, personSingletonB.getAge());

		((AbstractApplicationContext) applicationContext).close();
	}

	@Test
	public void testScopePrototype() {
		final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("scopes.xml");

		final Person personPrototypeA = (Person) applicationContext.getBean("personPrototype");
		final Person personPrototypeB = (Person) applicationContext.getBean("personPrototype");

		personPrototypeA.setName(NAME);
		personPrototypeA.setAge(AGE);

		personPrototypeB.setName(NAME_OTHER);
		personPrototypeB.setAge(AGE_OTHER);

		Assert.assertEquals(NAME, personPrototypeA.getName());
		Assert.assertEquals(NAME_OTHER, personPrototypeB.getName());

		((AbstractApplicationContext) applicationContext).close();
	}

}
