package com.baeldung.modelmapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class that contains methods for generic mapping of the users list.
 * Initially, an instance of ModelMapper was created.
 *
 * @author Sasa Milenkovic
 */
public class MapperUtil {

    private static ModelMapper modelMapper = new ModelMapper();


    private MapperUtil() {


    }

    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> target) {
        List<T> targetList = new ArrayList<T>();

        for (S source : sourceList) {
            targetList.add(modelMapper.map(source, target));
        }

        return targetList;
    }

}
