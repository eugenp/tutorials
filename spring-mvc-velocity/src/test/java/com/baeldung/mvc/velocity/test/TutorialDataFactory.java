package com.baeldung.mvc.velocity.test;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.mvc.velocity.domain.Tutorial;

public final class TutorialDataFactory {
	
	public static final Integer TEST_TUTORIAL_ID = 1;
	
	public static final String TEST_TUTORIAL_AUTHOR = "TestAuthor";
	
	public static final String TEST_TUTORIAL_TITLE = "Test Title";

	public static final String TEST_TUTORIAL_DESCRIPTION = "Test Description";
	

	private TutorialDataFactory() {
	}

	public static Tutorial createByDefault() {
		final Tutorial tutorial = new Tutorial();
		tutorial.setTutId(TEST_TUTORIAL_ID);
		tutorial.setAuthor(TEST_TUTORIAL_AUTHOR);
		tutorial.setTitle(TEST_TUTORIAL_TITLE);
		tutorial.setDescription(TEST_TUTORIAL_DESCRIPTION);
		return tutorial;
	}

	public static Tutorial create(final Integer id , final String title) {
		final Tutorial tutorial = createByDefault();
		tutorial.setTutId(id);
		tutorial.setTitle(title);
		return tutorial;
	}
	
	public static List<Tutorial> createTutorialList() {
		final List<Tutorial> tutorialList = new ArrayList<Tutorial>();
		tutorialList.add(createByDefault());
		return tutorialList;
	}

}
