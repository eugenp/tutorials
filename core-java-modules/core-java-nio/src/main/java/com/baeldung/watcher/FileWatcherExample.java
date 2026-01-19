package com.baeldung.watcher;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileWatcherExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        
        Path path = Paths.get(System.getProperty("user.home"));
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey key;
       
        Path fileToWatch = path.resolve("config.properties");
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                Path changed = (Path) event.context();

                if (changed.equals(fileToWatch.getFileName())) {
                    System.out.println(
                      "Event kind: " + event.kind() 
                        + ". File affected: " + changed);
                }
            }
            key.reset();
        }

        watchService.close();
    }

}
