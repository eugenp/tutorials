package com.baeldung.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sasam0320
 * @description
 * This is a helper class that contains methods for generic mapping of the users list.
 * Initially, an instance of ModelMapper was created. In the static block we set the matching configuration to STRICT.
 */
public class MapperUtil {


    private static ModelMapper modelMapper = new ModelMapper();


    static {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    }

    private MapperUtil() {


    }

    public static <S, T> T mapTo(final S source, final Class<T> target) {
        return modelMapper.map(source, target);
    }

    public static <S, T> List<T> mapList(final List<S> sourceList, final Class<T> target) {
        List<T> targetList = new ArrayList<T>();

        for (S source : sourceList) {
            targetList.add(modelMapper.map(source, target));
        }

        return targetList;
    }

}
