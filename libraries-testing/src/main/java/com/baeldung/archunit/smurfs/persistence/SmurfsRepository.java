package com.baeldung.archunit.smurfs.persistence;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.baeldung.archunit.smurfs.persistence.domain.Smurf;

import static java.util.stream.Collectors.toList;

public class SmurfsRepository {
    
    private static Map<String,Smurf> smurfs = Collections.synchronizedMap(new TreeMap<>());
    
    static {
        // Just a few here. A full list can be found 
        // at https://smurfs.fandom.com/wiki/List_of_Smurf_characters
        smurfs.put("Papa", new Smurf("Papa", true, true));
        smurfs.put("Actor", new Smurf("Actor", true, true));
        smurfs.put("Alchemist", new Smurf("Alchemist", true, true));
        smurfs.put("Archeologist", new Smurf("Archeologist", true, true));
        smurfs.put("Architect", new Smurf("Architect", true, true));
        smurfs.put("Baby", new Smurf("Baby", true, true));
        smurfs.put("Baker", new Smurf("Baker", true, true));
    }

    public List<Smurf> findAll() {
       return Collections.unmodifiableList(smurfs.values().stream().collect(toList()));
    }
    
    public Optional<Smurf> findByName(String name) {
        return Optional.of(smurfs.get(name));
    }

}
