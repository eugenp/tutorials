package com.baeldung.mvc.velocity.test;


import com.baeldung.mvc.velocity.controller.MainController;
import com.baeldung.mvc.velocity.domain.Tutorial;
import com.baeldung.mvc.velocity.service.TutorialsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class NavigationControllerTest {

	private final MainController mainController = new MainController(Mockito.mock(TutorialsService.class));
	
	private final Model model = new ExtendedModelMap();
	
	@Test
	public final void shouldGoToTutorialListView() {
		Mockito.when(mainController.getTutService().listTutorials())
          .thenReturn(createTutorialList());

		final String view = mainController.listTutorialsPage(model);
		final List<Tutorial> tutorialListAttribute = (List<Tutorial>) model.asMap().get("tutorials");
		
		assertEquals("index", view);
		assertNotNull(tutorialListAttribute);
	}


	private static List<Tutorial> createTutorialList() {
		return Arrays.asList(new Tutorial(1, "TestAuthor", "Test Title", "Test Description"));
    }
}
