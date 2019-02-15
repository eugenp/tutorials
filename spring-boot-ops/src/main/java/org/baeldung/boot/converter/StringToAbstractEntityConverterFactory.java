package org.baeldung.boot.converter;

import org.baeldung.boot.domain.AbstractEntity;
import org.baeldung.boot.domain.Bar;
import org.baeldung.boot.domain.Foo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToAbstractEntityConverterFactory implements ConverterFactory<String, AbstractEntity>{

    @Override
    public <T extends AbstractEntity> Converter<String, T> getConverter(Class<T> targetClass) {

        return new StringToAbstractEntityConverter<>(targetClass);
    }


    private static class StringToAbstractEntityConverter<T extends AbstractEntity> implements Converter<String, T> {

        private Class<T> targetClass;

        public StringToAbstractEntityConverter(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public T convert(String source) {
            long id = Long.parseLong(source);
            if(this.targetClass == Foo.class) {
                return (T) new Foo(id);
            }
            else if(this.targetClass == Bar.class) {
                return (T) new Bar(id);
            } else {
                return null;
            }
        }
    }
}
