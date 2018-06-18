import java.io.IOException;

public class PlayerScoreException extends Exception {
	public PlayerScoreException(IOException e) {
		super(e);
	}
}
