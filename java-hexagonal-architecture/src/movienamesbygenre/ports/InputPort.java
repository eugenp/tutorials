package movienamesbygenre.ports;

import java.util.List;

public interface InputPort {
	
	List<String> handleInput(String genre);

}
