package com.baeldung.eval;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.*;

public class ApplicationUnitTest {
	
	@Test
	public void whenDoingSomething_ThenUiDatabaseAndLoggerCalled() {
		
		UserInterface ui = Mockito.mock(UserInterface.class);
		Database db = Mockito.mock(Database.class);
		Logger logger = Mockito.mock(Logger.class);
		
		Application application = new Application(ui, db, logger);
		
		application.doSomething();
		
		verify(ui, times(1)).notifyEventComplete(eq("foo"));
		verify(db, times(1)).store(eq("foo"), eq("bar"));
		verify(logger, times(1)).log(eq("Something"));
	}
}
