package com.baeldung.hibernate.lifecycle;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DirtyDataInspector extends EmptyInterceptor {
    private static final ArrayList<FootballPlayer> dirtyEntities = new ArrayList<>();

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        dirtyEntities.add((FootballPlayer) entity);
        return true;
    }

    public static List<FootballPlayer> getDirtyEntities() {
        return dirtyEntities;
    }

    public static void clearDirtyEntitites() {
        dirtyEntities.clear();
    }
}