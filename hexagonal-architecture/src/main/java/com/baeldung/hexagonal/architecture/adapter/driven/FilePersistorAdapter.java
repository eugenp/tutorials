package com.baeldung.hexagonal.architecture.adapter.driven;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.domain.dto.Account;
import com.baeldung.hexagonal.architecture.port.Persistor;

@Service
public class FilePersistorAdapter implements Persistor {

    @Override
    public boolean persist(Account data) {
        try (FileWriter fileWriter = new FileWriter("out.txt")) {
            fileWriter.write(data.getFirstName() + " " + data.getLastName());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
