package com.baeldung.exceptionhandling;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionsUnitTest {

    Exceptions exceptions = new Exceptions();

    @Test
    public void getPlayers() {
        assertThatThrownBy(() -> exceptions.getPlayers())
            .isInstanceOf(NoSuchFileException.class);
    }

    @Test
    public void loadAllPlayers() {
        assertThatThrownBy(() -> exceptions.loadAllPlayers(""))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void getPlayerScoreThrows() {
        assertThatThrownBy(() -> exceptions.getPlayerScoreThrows(""))
            .isInstanceOf(FileNotFoundException.class);
    }
    
    @Test
    public void getPlayerScoreTryCatch() {
        assertThatThrownBy(() -> exceptions.getPlayerScoreTryCatch(""))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void getPlayerScoreFinally() {
        assertThatThrownBy(() -> exceptions.getPlayerScoreFinally(""))
            .isInstanceOf(FileNotFoundException.class);
    }
    
    @Test
    public void loadAllPlayersThrowingChecked() {
        assertThatThrownBy(() -> exceptions.loadAllPlayersThrowingChecked(""))
            .isInstanceOf(TimeoutException.class);
    }
    
    @Test
    public void loadAllPlayersThrowingUnchecked() {
        assertThatThrownBy(() -> exceptions.loadAllPlayersThrowingUnchecked(""))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void loadAllPlayersWrapping() {
        assertThatThrownBy(() -> exceptions.loadAllPlayersWrapping(""))
            .isInstanceOf(IOException.class);
    }
    
    @Test
    public void loadAllPlayersRethrowing() {
        assertThatThrownBy(() -> exceptions.loadAllPlayersRethrowing(""))
            .isInstanceOf(PlayerLoadException.class);
    }
    
    @Test
    public void loadAllPlayersThrowable() {
        assertThatThrownBy(() -> exceptions.loadAllPlayersThrowable(""))
            .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    public void getPlayerScoreSwallowingExceptionAntiPatternAlternative2() {
        assertThatThrownBy(() -> exceptions.getPlayerScoreSwallowingExceptionAntiPatternAlternative2(""))
            .isInstanceOf(PlayerScoreException.class);
    }
}
