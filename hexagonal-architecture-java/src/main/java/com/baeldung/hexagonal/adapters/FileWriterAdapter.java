package com.baeldung.hexagonal.adapters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.baeldung.hexagonal.model.SportRevenue;
import com.baeldung.hexagonal.ports.WriteSportsRevenue;

public class FileWriterAdapter implements WriteSportsRevenue {

    @Override
    public void writeSportsReveue(SportRevenue sportRevenue) {
        try (FileWriter fw = new FileWriter(new File("revenue.txt"),true)) {
            fw.write(sportRevenue.toString());
            fw.write(System.lineSeparator());
        } catch (IOException e) {

        }
    }
}
