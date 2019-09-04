package com.baeldung.hexagonalarch.adapter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.baeldung.hexagonalarch.domain.RandomFact;
import com.baeldung.hexagonalarch.domain.ports.StorageService;

public class FileSystemStorageService implements StorageService {

    @Override
    public void save(RandomFact randomFact) throws IOException {
        BufferedWriter bufferedWriter = null;
        try(FileWriter fstream = new FileWriter("out.txt", true)) {
            bufferedWriter = new BufferedWriter(fstream);
            bufferedWriter.write("\n" + randomFact.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
