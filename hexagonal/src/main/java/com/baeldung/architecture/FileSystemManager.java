package com.baeldung.architecture;

import com.google.inject.Singleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@Singleton
public class FileSystemManager {
    public static boolean put(MonthlyStats stats, String filename, String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            return false;
        }
        try {
            FileOutputStream f = new FileOutputStream(new File(directoryPath, filename));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(stats);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean delete(String filename, String directoryPath) {
        String filePath = directoryPath + "/" + filename;
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
