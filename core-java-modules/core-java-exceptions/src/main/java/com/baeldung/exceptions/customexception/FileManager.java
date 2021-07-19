package com.baeldung.exceptions.customexception;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileManager {

    public static String getFirstLine(String fileName) throws IncorrectFileNameException {
        try (Scanner file = new Scanner(new File(fileName))) {
            if (file.hasNextLine()) {
                return file.nextLine();
            } else {
                throw new IllegalArgumentException("Non readable file");
            }
        } catch (FileNotFoundException err) {
            if (!isCorrectFileName(fileName)) {
                throw new IncorrectFileNameException("Incorrect filename : " + fileName, err);
            }
            // Logging etc
        } catch (IllegalArgumentException err) {
            if (!containsExtension(fileName)) {
                throw new IncorrectFileExtensionException("Filename does not contain extension : " + fileName, err);
            }
            // Other error cases and logging
        }
        return "Default First Line";
    }

    private static boolean containsExtension(String fileName) {
        if (fileName.contains(".txt") || fileName.contains(".doc"))
            return true;
        return false;
    }

    private static boolean isCorrectFileName(String fileName) {
        if (fileName.equals("wrongFileName.txt"))
            return false;
        else
            return true;
    }

}
