package movienamesbygenre.ports;

import java.io.IOException;

public interface PrintOutputPort {
	
	void printMovieNamesByGenre(String genre) throws IOException;
	
	void printAllMovieNames() throws IOException;

}
