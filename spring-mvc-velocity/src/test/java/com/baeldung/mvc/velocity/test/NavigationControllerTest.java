package com.baeldung.mvc.velocity.test;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.baeldung.mvc.velocity.controller.MainController;
import com.baeldung.mvc.velocity.domain.Tutorial;
import com.baeldung.mvc.velocity.service.TutorialsService;


@RunWith(MockitoJUnitRunner.class)
public class NavigationControllerTest {

	private MainController mainController;
	
	private TutorialsService tutorialsService;
	
	
	private Model model;
	
	@Before
	public final void setUp() throws Exception {
		model = new ExtendedModelMap();
		mainController = Mockito.spy(new MainController());
		tutorialsService = Mockito.mock(TutorialsService.class);
		
		mainController.setTutService(tutorialsService);
		
	}
	
	@Test
	public final void shouldGoToTutorialListView() {
		Mockito.when(tutorialsService.listTutorials()).thenReturn(TutorialDataFactory.createTutorialList());
		final String view = mainController.listTutorialsPage(model);
		final List<Tutorial> tutorialListAttribute = (List<Tutorial>) model.asMap().get("tutorials");
		
		assertEquals("index", view);
		assertNotNull(tutorialListAttribute);
	}


	

}
