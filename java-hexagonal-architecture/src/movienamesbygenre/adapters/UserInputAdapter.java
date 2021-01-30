package movienamesbygenre.adapters;

import java.util.List;

import movienamesbygenre.ports.GetMovieNamesPort;
import movienamesbygenre.ports.InputPort;

public class UserInputAdapter implements InputPort{
	
	private GetMovieNamesPort inMemoryMoviesDB;
	
	public UserInputAdapter(GetMovieNamesPort inMemoryMoviesDB){
		this.inMemoryMoviesDB = inMemoryMoviesDB;
	}

	@Override
	public List<String> handleInput(String genre) {
		return inMemoryMoviesDB.getMovieNamesByGenre(genre);		
	}

}
