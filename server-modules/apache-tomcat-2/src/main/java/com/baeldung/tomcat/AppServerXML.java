package com.baeldung.tomcat;

import org.apache.catalina.startup.Catalina;
import java.nio.file.*;

public class AppServerXML {
    
    public static void main(String[] args) throws Exception {
        Path staticDir = resolvePath("src/main/resources/static");
        Path serverXml = resolvePath("src/main/resources/server.xml");
        Path baseDir = Paths.get("target/tomcat-base").toAbsolutePath();

        Files.createDirectories(baseDir);
        String config = Files.readString(serverXml)
            .replace("STATIC_DIR_PLACEHOLDER", staticDir.toString());
        Path configFile = baseDir.resolve("server.xml");
        Files.writeString(configFile, config);

        System.setProperty("catalina.base", baseDir.toString());
        System.setProperty("catalina.home", baseDir.toString());

        Catalina catalina = new Catalina();
        catalina.load(new String[]{"-config", configFile.toString()});
        catalina.start();

        System.out.println("\nTomcat started with multiple connectors!");
        System.out.println("http://localhost:8081");
        System.out.println("http://localhost:7081");
        catalina.getServer().await();
    }

    private static Path resolvePath(String relativePath) {
        Path path = Paths.get(relativePath);
        return Files.exists(path) ? path.toAbsolutePath() 
            : Paths.get("apache-tomcat-2", relativePath).toAbsolutePath();
    }
}
