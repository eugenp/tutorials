package com.baeldung.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sasam0320
 * @date 4/18/2020
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
