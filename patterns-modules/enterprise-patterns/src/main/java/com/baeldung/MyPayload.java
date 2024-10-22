package com.baeldung;

class Title {

	private String name;

	// standard constructors, getters/setters, equals and hashcode

	public Title deepCopy() {
		return new Title(name);
	}
}

class Book {

	private Title title;
	private String author;

	// standard constructors, getters/setters, equals and hashcode

	public Book deepCopy() {
		return new Book(title.deepCopy(), author);
	}
}

class MyTest {

	@Test
	public void whenShallowCopyInvoked_thenObjectsAreNotSame_butParametersAreSame() {

		Book originalBook = new Book(new Title("Original"));

		Book shallowCopy = new Book(originalBook.getTitle(), "Baeldung");

		assertThat(shallowCopy).isNotSameAs(originalBook);
		assertThat(shallowCopy.getTitle()).isSameAs(originalBook.getTitle());
	}

	@Test
	public void whenModifingOriginal_thenFieldsOfCopyAreChanged() {

		Book originalBook = new Book(new Title("Original"));
		Book shallowCopy = new Book(originalBook.getTitle(), "Baeldung");

		shallowCopy.getTitle().setName("Copy");

		assertThat(originalBook.getTitle().getValue()).isNotEqualTo("Original");
		assertThat(originalBook.getTitle().getValue()).isEqualTo(shallowCopy.getTitle().getValue());
	}

	@Test
	public void whenModifingOriginal_thenFieldsOfDeepCopyAreNotAffected() {

		Book originalBook = new Book(new Title("Original"));
		Book deepCopy = originalBook.deepCopy();

		shallowCopy.getTitle().setName("Copy");

		assertThat(originalBook.getTitle().getValue()).isEqual("Original");
		assertThat(originalBook).isNotSameAs(shallowCopy);
		assertThat(originalBook.getTitle()).isNotSameAs(shallowCopy.getTitle());
	}

}