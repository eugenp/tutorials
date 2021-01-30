package movienamesbygenre.ports;

import java.util.List;
import java.util.Map;

public interface GetMovieNamesPort {
	
	List<String> getMovieNamesByGenre(String genre);
	
	Map<String,List<String>> getAllMovieNames();

}
