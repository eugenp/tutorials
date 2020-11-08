package com.baeldung.boot.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverterFactory 
  implements ConverterFactory<String, Enum> {
 
    private static class StringToEnumConverter<T extends Enum> 
      implements Converter<String, T> {
 
        private Class<T> enumType;
 
        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }
 
        public T convert(String source) {
            return (T) Enum.valueOf(this.enumType, source.trim());
        }
    }
 
    @Override
    public <T extends Enum> Converter<String, T> getConverter(
      Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }
}