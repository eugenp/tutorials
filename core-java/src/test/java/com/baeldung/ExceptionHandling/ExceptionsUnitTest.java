
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

class ExceptionsTest {
	
	Exceptions exceptions = new Exceptions();


	@Test
	public void getPlayers() {
        assertThrows(NoSuchFileException.class, () -> exceptions.getPlayers());
	}
	
    @Test
    public void loadAllPlayers() {
    	assertThrows(IOException.class, () -> exceptions.loadAllPlayers(""));
    }

    @Test
    public void getPlayerScoreThrows() {
    	assertThrows(FileNotFoundException.class, () -> exceptions.getPlayerScoreThrows(""));
    }
    
    @Test
    public void getPlayerScoreTryCatch() {
    	assertThrows(IllegalArgumentException.class, () -> exceptions.getPlayerScoreTryCatch(""));
    }
    
    @Test
    public void getPlayerScoreFinally() {
    	assertThrows(FileNotFoundException.class, () -> exceptions.getPlayerScoreFinally(""));
    }
    
    @Test
    public void loadAllPlayersThrowingChecked() {
    	assertThrows(TimeoutException.class, () -> exceptions.loadAllPlayersThrowingChecked(""));
    }
    
    @Test
    public void loadAllPlayersThrowingUnchecked() {
    	assertThrows(IllegalArgumentException.class, () -> exceptions.loadAllPlayersThrowingUnchecked(""));
    }
    
    @Test
    public void loadAllPlayersWrapping() {
    	assertThrows(IOException.class, () -> exceptions.loadAllPlayersWrapping(""));
    }
    
    @Test
    public void loadAllPlayersRethrowing() {
    	assertThrows(PlayerLoadException.class, () -> exceptions.loadAllPlayersRethrowing(""));
    }
    
    @Test
    public void loadAllPlayersThrowable() {
    	assertThrows(NullPointerException.class, () -> exceptions.loadAllPlayersThrowable(""));
    }
    
    @Test
    public void throwAsGotoAntiPattern() {
    	assertThrows(MyException.class, () -> exceptions.throwAsGotoAntiPattern());
    }
    
    @Test
    public void getPlayerScoreSwallowingExceptionAntiPatternAlternative2() {
    	assertThrows(PlayerScoreException.class, () -> exceptions.getPlayerScoreSwallowingExceptionAntiPatternAlternative2(""));
    }
}
