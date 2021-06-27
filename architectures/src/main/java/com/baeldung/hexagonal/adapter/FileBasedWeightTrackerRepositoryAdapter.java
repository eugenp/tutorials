package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.exception.RepositoryException;
import com.baeldung.hexagonal.core.port.WeightTrackerRepository;
import com.baeldung.hexagonal.core.model.WeightInTime;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileBasedWeightTrackerRepositoryAdapter implements WeightTrackerRepository {
    private static final String FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATTER_PATTERN);
    private final Path path;

    public FileBasedWeightTrackerRepositoryAdapter(String pathToFile) {
        this.path = Paths.get(pathToFile);

        if (!Files.exists(path)) createDataFile();
    }

    private void createDataFile() {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RepositoryException("Unable to create data file.", e);
        }
    }

    @Override
    public void addWeight(float weightInKG, LocalDateTime date) {
        String line = String.format("%s|%s|%s", getNextId(), date.format(FORMATTER), weightInKG);
        writeHistory(line);
    }

    @Override
    public List<WeightInTime> getWeightHistory() {
        return readHistory().stream().map(row -> new WeightInTime(row.id, row.weight, row.when)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(long id) {
        List<Row> rows = readHistory();
        List<Row> cleanedRows = rows.stream().filter(row -> row.id != id).sorted(Comparator.comparing(row -> row.id)).collect(Collectors.toList());

        truncate();
        cleanedRows.forEach(this::writeHistory);
        return rows.size() > cleanedRows.size();
    }

    private long getNextId() {
        // This is a weak implantation just for small files.
        List<Row> rows = readHistory();
        if (rows == null || rows.isEmpty()) return 1;

        return rows.stream().mapToLong(row -> row.id).max().getAsLong() + 1;
    }

    private void writeHistory(Row row) {
        writeHistory(row.toString());
    }

    private void truncate() {
        try {
            Files.write(path, new byte[0]);
        } catch (IOException e) {
            throw new RepositoryException("Unable to save the weight.", e);
        }
    }

    private void writeHistory(String line) {
        try {
            Files.write(path, (line + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RepositoryException("Unable to save the weight.", e);
        }
    }

    public List<Row> readHistory() {
        try {
            return Files.readAllLines(path).stream().map(line -> {
                String idPart = line.split("\\|")[0];
                String datePart = line.split("\\|")[1];
                String weightPart = line.split("\\|")[2];

                return new Row(Long.parseLong(idPart), Float.parseFloat(weightPart), LocalDateTime.parse(datePart, FORMATTER));
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RepositoryException("Unable to read the history file.", e);
        }
    }

    private static class Row {
        long id;
        float weight;
        LocalDateTime when;

        public Row(long id, float weight, LocalDateTime when) {
            this.id = id;
            this.weight = weight;
            this.when = when;
        }

        @Override
        public String toString() {
            return String.format("%s|%s|%s", id, when.format(FORMATTER), weight);
        }
    }
}
