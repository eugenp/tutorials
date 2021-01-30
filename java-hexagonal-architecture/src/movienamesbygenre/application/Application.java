package movienamesbygenre.application;

import java.io.IOException;

import movienamesbygenre.adapters.FileOutputAdapter;
import movienamesbygenre.adapters.InMemoryMoviesAdapter;
import movienamesbygenre.adapters.UserInputAdapter;
import movienamesbygenre.ports.GetMovieNamesPort;
import movienamesbygenre.ports.InputPort;
import movienamesbygenre.ports.PrintOutputPort;

public class Application {
	
	private GetMovieNamesPort getMovieNamesPort;
	private InputPort inputPort;
	private PrintOutputPort printOutputPort;
	
	Application(GetMovieNamesPort getMovieNamesPort, InputPort inputPort, PrintOutputPort printOutputPort){
		this.getMovieNamesPort = getMovieNamesPort;
		this.inputPort = inputPort;
		this.printOutputPort = printOutputPort;
	}
	
	public static void main(String[] args) throws IOException{
		GetMovieNamesPort getMovieNamesPort = new InMemoryMoviesAdapter();
		InputPort inputPort = new UserInputAdapter(getMovieNamesPort);
		PrintOutputPort printOutputPort = new FileOutputAdapter(getMovieNamesPort);
		
		System.out.println("Action Movies : "+inputPort.handleInput("Action"));
		
		printOutputPort.printMovieNamesByGenre("Action");
		printOutputPort.printAllMovieNames();		
	}

}
