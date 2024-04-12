package com.baeldung.enummapping.converters;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.baeldung.enummapping.enums.Level;

public class StringToLevelConverter implements Converter<String, Level> {

    @Override
    public Level convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return EnumUtils.getEnum(Level.class, source.toUpperCase());
    }

}
