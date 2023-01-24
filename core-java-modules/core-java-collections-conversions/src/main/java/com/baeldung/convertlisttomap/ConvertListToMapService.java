package com.baeldung.convertlisttomap;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConvertListToMapService {

    public Map<Integer, Animal> convertListBeforeJava8(List<Animal> list) {

        Map<Integer, Animal> map = new HashMap<>();

        for (Animal animal : list) {
            map.put(animal.getId(), animal);
        }
        return map;
    }

    public Map<Integer, Animal> convertListAfterJava8(List<Animal> list) {
        Map<Integer, Animal> map = list.stream().collect(Collectors.toMap(Animal::getId, Function.identity()));
        return map;
    }

    public Map<Integer, Animal> convertListWithGuava(List<Animal> list) {

        Map<Integer, Animal> map = Maps.uniqueIndex(list, Animal::getId);
        return map;
    }

    public Map<Integer, Animal> convertListWithApacheCommons(List<Animal> list) {

        Map<Integer, Animal> map = new HashMap<>();

        MapUtils.populateMap(map, list, Animal::getId);

        return map;
    }
}
