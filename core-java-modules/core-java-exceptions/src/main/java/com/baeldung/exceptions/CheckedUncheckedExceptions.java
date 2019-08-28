package com.baeldung.exceptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CheckedUncheckedExceptions {
    public static void checkedExceptionWithThrows() throws FileNotFoundException {
        File file = new File("not_existing_file.txt");
        FileInputStream stream = new FileInputStream(file);
    }

    public static void checkedExceptionWithTryCatch() {
        File file = new File("not_existing_file.txt");
        try {
            FileInputStream stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int divideByZero() {
        int numerator = 1;
        int denominator = 0;
        return numerator / denominator;
    }

    public static void checkFile(String fileName) throws IncorrectFileNameException {
        if (fileName == null || fileName.isEmpty()) {
            throw new NullOrEmptyException("The filename is null.");
        }
        if (!isCorrectFileName(fileName)) {
            throw new IncorrectFileNameException("Incorrect filename : " + fileName);
        }
    }

    private static boolean isCorrectFileName(String fileName) {
        if (fileName.equals("wrongFileName.txt"))
            return false;
        else
            return true;
    }
}
