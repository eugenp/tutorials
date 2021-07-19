package com.baeldung.exceptions.exceptionhandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Exceptions {

    private final static Logger logger = Logger.getLogger("ExceptionLogging");

    public static List<Player> getPlayers() throws IOException {
        Path path = Paths.get("players.dat");
        List<String> players = Files.readAllLines(path);

        return players.stream()
          .map(Player::new)
          .collect(Collectors.toList());
    }

    public List<Player> loadAllPlayers(String playersFile) throws IOException{
        try {
            throw new IOException();
        } catch(IOException ex) {
            throw new IllegalStateException();
        }
    }

    public int getPlayerScoreThrows(String playerFile) throws FileNotFoundException {		 
        Scanner contents = new Scanner(new File(playerFile));
        return Integer.parseInt(contents.nextLine());
    }

    public int getPlayerScoreTryCatch(String playerFile) {
        try {
            Scanner contents = new Scanner(new File(playerFile));
            return Integer.parseInt(contents.nextLine());
        } catch (FileNotFoundException noFile) {
            throw new IllegalArgumentException("File not found");
        }
    }

    public int getPlayerScoreTryCatchRecovery(String playerFile) {
        try {
            Scanner contents = new Scanner(new File(playerFile));
            return Integer.parseInt(contents.nextLine());
        } catch ( FileNotFoundException noFile ) {
            logger.warning("File not found, resetting score.");
            return 0;
        }
    }

    public int getPlayerScoreFinally(String playerFile) throws FileNotFoundException {
        Scanner contents = null;
        try {
            contents = new Scanner(new File(playerFile));
            return Integer.parseInt(contents.nextLine());
        } finally {
            if (contents != null) {
                contents.close();
            }
        }
    }

    public int getPlayerScoreTryWithResources(String playerFile) {
        try (Scanner contents = new Scanner(new File(playerFile))) {
            return Integer.parseInt(contents.nextLine());
        } catch (FileNotFoundException e ) {
            logger.warning("File not found, resetting score.");
            return 0;
        }
    }

    public int getPlayerScoreMultipleCatchBlocks(String playerFile) {
        try (Scanner contents = new Scanner(new File(playerFile))) {
            return Integer.parseInt(contents.nextLine());
        } catch (IOException e) {
            logger.warning("Player file wouldn't load!");
            return 0;
        } catch (NumberFormatException e) {
            logger.warning("Player file was corrupted!");
            return 0;
        }
    }

    public int getPlayerScoreMultipleCatchBlocksAlternative(String playerFile) {
        try (Scanner contents = new Scanner(new File(playerFile)) ) {
            return Integer.parseInt(contents.nextLine());
        } catch (FileNotFoundException e) {
            logger.warning("Player file not found!");
            return 0;
        } catch (IOException e) {
            logger.warning("Player file wouldn't load!");
            return 0;
        } catch (NumberFormatException e) {
            logger.warning("Player file was corrupted!");
            return 0;
        }
    }

    public int getPlayerScoreUnionCatchBlocks(String playerFile) {
        try (Scanner contents = new Scanner(new File(playerFile))) {
            return Integer.parseInt(contents.nextLine());
        } catch (IOException | NumberFormatException e) {
            logger.warning("Failed to load score!");
            return 0;
        }
    }

    public List<Player> loadAllPlayersThrowingChecked(String playersFile) throws TimeoutException {
        boolean tooLong = true;

        while (!tooLong) {
            // ... potentially long operation
        }
            throw new TimeoutException("This operation took too long");
    }

    public List<Player> loadAllPlayersThrowingUnchecked(String playersFile) throws TimeoutException {
        if(!isFilenameValid(playersFile)) {
            throw new IllegalArgumentException("Filename isn't valid!");
        }
        return null;

        // ...
    }

    public List<Player> loadAllPlayersWrapping(String playersFile) throws IOException {
        try { 
            throw new IOException();
        } catch (IOException io) { 		
            throw io;
        }
    }

    public List<Player> loadAllPlayersRethrowing(String playersFile) throws PlayerLoadException {
        try { 
            throw new IOException();
        } catch (IOException io) { 		
            throw new PlayerLoadException(io);
        }
    }

    public List<Player> loadAllPlayersThrowable(String playersFile) {
        try {
            throw new NullPointerException();
        } catch ( Throwable t ) {
            throw t;
        }
    }

    class FewerExceptions extends Exceptions {
        @Override
        public List<Player> loadAllPlayers(String playersFile) {  //can't add "throws MyCheckedException 
            return null;
            // overridden
        }
    }

    public void throwAsGotoAntiPattern() throws MyException {
        try {
            // bunch of code
            throw new MyException();
            // second bunch of code
        } catch ( MyException e ) {
            // third bunch of code
        }
    }

    public int getPlayerScoreSwallowingExceptionAntiPattern(String playerFile) {
        try {
        // ...
        } catch (Exception e) {} // <== catch and swallow
            return 0;
    }

    public int getPlayerScoreSwallowingExceptionAntiPatternAlternative(String playerFile) {
        try {
            // ...
        } catch (Exception e) {
             e.printStackTrace();
        }
      return 0;
    }

    public int getPlayerScoreSwallowingExceptionAntiPatternAlternative2(String playerFile) throws PlayerScoreException {
        try {
            throw new IOException();
        } catch (IOException e) {
            throw new PlayerScoreException(e);
        }
    }

    public int getPlayerScoreReturnInFinallyAntiPattern(String playerFile) {
        int score = 0;
        try {
            throw new IOException();
        } finally {
            return score; // <== the IOException is dropped
        }
    }

    private boolean isFilenameValid(String name) {
        return false;
    }
}
