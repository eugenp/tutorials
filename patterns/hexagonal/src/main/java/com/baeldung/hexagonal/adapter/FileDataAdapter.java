package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.dto.Student;
import com.baeldung.hexagonal.domain.port.IDataPort;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


@AllArgsConstructor
public class FileDataAdapter implements IDataPort {

    private String fileName;

    @Override
    public List<Student> getStudentsData() {
        Gson g = new Gson();
        Student[] students = new Student[0];
        try {
            students = g.fromJson(getJson().apply(ResourceUtils.getFile("classpath:" + fileName)), Student[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Arrays.asList(students);
    }

    public Function<File, String> getJson() {
        return file -> {
            try {
                return new String(Files.readAllBytes(Paths.get(file.getPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
