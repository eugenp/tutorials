package com.baeldung.mvc.velocity.test;


import com.baeldung.mvc.velocity.controller.MainController;
import com.baeldung.mvc.velocity.domain.Tutorial;
import com.baeldung.mvc.velocity.service.TutorialsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:mvc-servlet.xml"})
public class NavigationControllerTest {

	private MainController mainController = new MainController(Mockito.mock(TutorialsService.class));
	
	private final Model model = new ExtendedModelMap();

	
	@Test
	public void shouldGoToTutorialListView() {
		Mockito.when(mainController.getTutService().listTutorials())
          .thenReturn(createTutorialList());

		final String view = mainController.listTutorialsPage(model);
		final List<Tutorial> tutorialListAttribute = (List<Tutorial>) model.asMap().get("tutorials");
		
		assertEquals("index", view);
		assertNotNull(tutorialListAttribute);
	}
	
	@Test
	public void testContent() throws Exception{

        List<Tutorial> tutorials = Arrays.asList(
		          new Tutorial(1, "Guava", "Introduction to Guava", "GuavaAuthor"),
		          new Tutorial(2, "Android", "Introduction to Android", "AndroidAuthor")
		        );
        Mockito.when(mainController.getTutService().listTutorials()).thenReturn(tutorials);

        String view = mainController.listTutorialsPage(model);

        verify(mainController.getTutService(), times(1)).listTutorials();
        verifyNoMoreInteractions(mainController.getTutService());
        
        assertEquals("index", view);
        assertEquals(tutorials, model.asMap().get("tutorials"));
	}

	private static List<Tutorial> createTutorialList() {
		return Arrays.asList(new Tutorial(1, "TestAuthor", "Test Title", "Test Description"));
    }
}
