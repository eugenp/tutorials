package movienamesbygenre.adapters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movienamesbygenre.ports.GetMovieNamesPort;

public class InMemoryMoviesAdapter implements GetMovieNamesPort{
	
	static Map<String,List<String>> moviesDatabase;
	
	public InMemoryMoviesAdapter(){
		initialize();
	}

	private void initialize() {
		moviesDatabase = new HashMap<>();
		moviesDatabase.put("Drama", Arrays.asList("As the Crow Flies","Lake Effect","And life is back","The White Reindeer","Small Time"));
		moviesDatabase.put("Comedy",Arrays.asList("Game Night","Spy","The Other Guys","Due Date","The Interview"));
		moviesDatabase.put("Action", Arrays.asList("John Wick","Honest Thief","The Equalizer","Homefront","Kingsman"));
		moviesDatabase.put("Romantic", Arrays.asList("The Fault in our Stars","About Time","The Vow","The Last Song","A Walk to Remember"));
	}

	@Override
	public List<String> getMovieNamesByGenre(String genre) {
		return moviesDatabase.get(genre);
	}

	@Override
	public Map<String, List<String>> getAllMovieNames() {
		return moviesDatabase;
	}

}
