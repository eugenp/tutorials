package com.baeldung.tomcat;

import org.apache.catalina.startup.Catalina;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;

public class AppServerXML {
    
    public static void main(String[] args) throws Exception {
        AppServerXML app = new AppServerXML();
        Catalina catalina = app.startServer();
        catalina.getServer().await();
    }

    public Catalina startServer() throws Exception {
        URL staticUrl = getClass().getClassLoader().getResource("static");
        if (staticUrl == null) {
            throw new IllegalStateException("Static directory not found in classpath");
        }
        Path staticDir = Paths.get(staticUrl.toURI());
        
        Path baseDir = Paths.get("target/tomcat-base").toAbsolutePath();
        Files.createDirectories(baseDir);
        
        String config;
        try (InputStream serverXmlStream = getClass().getClassLoader().getResourceAsStream("server.xml")) {
            if (serverXmlStream == null) {
                throw new IllegalStateException("server.xml not found in classpath");
            }
            config = new String(serverXmlStream.readAllBytes())
                    .replace("STATIC_DIR_PLACEHOLDER", staticDir.toString());
        }
        
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
        
        return catalina;
    }
}
