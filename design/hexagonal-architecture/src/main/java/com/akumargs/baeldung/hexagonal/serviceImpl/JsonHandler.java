package com.akumargs.baeldung.hexagonal.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jvnet.hk2.annotations.Service;

import com.akumargs.baeldung.hexagonal.service.ResourceHandler;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@Service
public class JsonHandler implements ResourceHandler {

    public Map<String, String> extract(File file, List<String> properties) {
        try {
            Map<String, String> jsonMap = new Gson().fromJson(new FileReader(file), Map.class);
            return jsonMap.entrySet()
                .stream()
                .filter(entry -> properties.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
