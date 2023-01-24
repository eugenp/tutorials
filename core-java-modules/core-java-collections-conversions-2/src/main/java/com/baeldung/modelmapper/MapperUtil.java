package com.baeldung.modelmapper;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a helper class that contains method for custom mapping of the users list.
 * Initially, an instance of ModelMapper was created.
 *
 * @author Sasa Milenkovic
 */
public class MapperUtil {

    private static ModelMapper modelMapper = new ModelMapper();


    private MapperUtil() {


    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {

        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
