package movienamesbygenre.application;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import movienamesbygenre.adapters.InMemoryMoviesAdapter;
import movienamesbygenre.adapters.UserInputAdapter;
import movienamesbygenre.ports.GetMovieNamesPort;
import movienamesbygenre.ports.InputPort;


public class ApplicationTest {
	
	private GetMovieNamesPort getMovieNamesPort;
	private InputPort inputPort;	
	
	@Before                                         
    public void setUp() throws Exception {
		getMovieNamesPort = new InMemoryMoviesAdapter();
		inputPort = new UserInputAdapter(getMovieNamesPort);		
    }
	
	@Test
	public void testUserInputAdapter(){
		List<String> moviesList = inputPort.handleInput("Action");
		assertNotNull(moviesList);
		assertFalse(moviesList.isEmpty());
	}

}
