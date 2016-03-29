package org.baeldung.spring.data.cassandra.model;

import java.io.Serializable;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;

public abstract class AbstractModel {

    public static final String COLUMN_NAME_ID = "id";

    public MapId getMapId() {
        return BasicMapId.id(COLUMN_NAME_ID, getId());
    }

    public abstract Serializable getId();

}
