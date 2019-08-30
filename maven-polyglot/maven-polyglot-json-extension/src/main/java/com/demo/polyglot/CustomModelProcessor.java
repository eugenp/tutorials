package com.demo.polyglot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.FileModelSource;
import org.apache.maven.model.building.ModelProcessor;
import org.apache.maven.model.io.ModelParseException;
import org.apache.maven.model.io.ModelReader;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.util.ReaderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

@Component(role = ModelProcessor.class)
public class CustomModelProcessor implements ModelProcessor {

    private static final String XML_POM = "pom.xml";
    private static final String JSON_POM = "pom.json";
    private static final String JSON_EXT = ".json";

    ObjectMapper objectMapper = new ObjectMapper();

    @Requirement
    private ModelReader modelReader;

    @Override
    public File locatePom(File projectDirectory) {
        File pomFile = new File(projectDirectory, JSON_POM);
        if (!pomFile.exists()) {
            pomFile = new File(projectDirectory, XML_POM);
        }
        return pomFile;
    }

    @Override
    public Model read(InputStream input, Map<String, ?> options) throws IOException, ModelParseException {
        try (final Reader in = ReaderFactory.newPlatformReader(input)) {
            return read(in, options);
        }
    }

    @Override
    public Model read(Reader reader, Map<String, ?> options) throws IOException, ModelParseException {
        FileModelSource source = (options != null) ? (FileModelSource) options.get(SOURCE) : null;
        if (source != null && source.getLocation().endsWith(JSON_EXT)) {
            Model model = objectMapper.readValue(reader, Model.class);
            return model;
        }
        //It's a normal maven project with a pom.xml file
        return modelReader.read(reader, options);
    }

    @Override
    public Model read(File input, Map<String, ?> options) throws IOException, ModelParseException {
        return null;
    }
}