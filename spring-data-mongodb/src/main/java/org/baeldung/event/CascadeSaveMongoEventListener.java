package org.baeldung.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ReflectionUtils;

public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(final Object source) {
        ReflectionUtils.doWithFields(source.getClass(), new CascadeCallback(source, mongoOperations));
    }
}