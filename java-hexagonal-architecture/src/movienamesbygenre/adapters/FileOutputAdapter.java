package movienamesbygenre.adapters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import movienamesbygenre.ports.GetMovieNamesPort;
import movienamesbygenre.ports.PrintOutputPort;

public class FileOutputAdapter implements PrintOutputPort{
	
	private GetMovieNamesPort inMemoryMoviesDB;
	
	public FileOutputAdapter(GetMovieNamesPort inMemoryMoviesDB){
		this.inMemoryMoviesDB = inMemoryMoviesDB;
	}

	@Override
	public void printMovieNamesByGenre(String genre) throws IOException{
		File file = new File(genre+"_Movies.txt");
		FileWriter writer = null;
		file.createNewFile();
		writer = new FileWriter(file); 
		List<String> moviesList = inMemoryMoviesDB.getMovieNamesByGenre(genre);
		for(String movie : moviesList){
			writer.append(movie+"\n");
		}
		writer.flush();
		writer.close();			
	}

	@Override
	public void printAllMovieNames() throws IOException {
		File file = new File("All_Movies.txt");
		FileWriter writer = null;
		file.createNewFile();
		writer = new FileWriter(file); 
		Map<String, List<String>> moviesList = inMemoryMoviesDB.getAllMovieNames();
		for(Map.Entry<String, List<String>> entry : moviesList.entrySet()){
			writer.append(entry.getKey()+" : "+entry.getValue()+"\n");
		}
		writer.flush();
		writer.close();			
	}

}
