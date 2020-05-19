package com.baeldung.value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@PropertySource("classpath:values.properties")
public class CollectionProvider {

    private final List<String> values = new ArrayList<>();

    public Collection<String> getValues() {
        return Collections.unmodifiableCollection(values);
    }

    @Autowired
    public void setValues(@Value("#{'${listOfValues}'.split(',')}") List<String> values) {
        this.values.addAll(values);
    }
}
