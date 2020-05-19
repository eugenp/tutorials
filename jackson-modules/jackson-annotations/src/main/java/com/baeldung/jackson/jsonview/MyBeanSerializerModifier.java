package com.baeldung.jackson.jsonview;

import java.util.List;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class MyBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(final SerializationConfig config, final BeanDescription beanDesc, final List<BeanPropertyWriter> beanProperties) {
        for (int i = 0; i < beanProperties.size(); i++) {
            final BeanPropertyWriter beanPropertyWriter = beanProperties.get(i);
            if (beanPropertyWriter.getName() == "name") {
                beanProperties.set(i, new UpperCasingWriter(beanPropertyWriter));
            }
        }
        return beanProperties;
    }
}