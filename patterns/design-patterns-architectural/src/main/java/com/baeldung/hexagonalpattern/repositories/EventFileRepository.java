package com.baeldung.hexagonalpattern.repositories;

import com.baeldung.hexagonalpattern.entities.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

public class EventFileRepository implements EventRepository {

    private static final String FILE_PATH = "/tmp/events";
    private static final String SEPARATOR = ":";
    private static final String EVENT_FILE_FORMAT = "%s:%s";

    private final File eventsFile = new File(FILE_PATH);

    @Override
    public void saveEvent(Event event) {
        try (PrintWriter printWriter
            = new PrintWriter(new FileOutputStream(eventsFile, true))) {
            printWriter.println(format(EVENT_FILE_FORMAT, event.getId(), event.getName()));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Could not save events");
        }
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
            events = lines.map(line -> {
                String[] eventData = line.split(SEPARATOR);
                return new Event(Long.parseLong(eventData[0]), eventData[1]);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("Could not read events");
        }
        return events;
    }
}
