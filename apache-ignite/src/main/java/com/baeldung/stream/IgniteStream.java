package com.baeldung.stream;

import com.baeldung.model.Employee;
import com.google.gson.Gson;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.stream.StreamTransformer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class IgniteStream {

    public static void main(String[] args) throws Exception {

        Ignition.setClientMode(true);

        Ignite ignite = Ignition.start();

        IgniteCache<Integer, Employee> cache = ignite.getOrCreateCache(CacheConfig.employeeCache());
        IgniteDataStreamer<Integer, Employee> streamer = ignite.dataStreamer(cache.getName());
        streamer.allowOverwrite(true);

        streamer.receiver(StreamTransformer.from((e, arg) -> {

            Employee employee = e.getValue();
            employee.setEmployed(true);
            e.setValue(employee);

            return null;
        }));

        Path path = Paths.get(IgniteStream.class.getResource("employees.txt").toURI());

        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> {
            Stream<Employee> employees = Stream.of(new Gson().fromJson(line, Employee.class));
            employees.forEach(employee -> streamer.addData(employee.getId(), employee));
        });

    }
}
