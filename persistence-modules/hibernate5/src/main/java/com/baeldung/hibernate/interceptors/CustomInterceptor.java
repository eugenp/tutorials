package com.baeldung.hibernate.interceptors;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hibernate.interceptors.entity.User;

public class CustomInterceptor extends EmptyInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            logger.debug(entity.toString());
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }
    
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object [] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            ((User) entity).setLastModified(new Date());
            logger.debug(entity.toString());
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
