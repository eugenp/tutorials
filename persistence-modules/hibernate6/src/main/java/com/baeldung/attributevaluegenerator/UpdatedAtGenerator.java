package com.baeldung.attributevaluegenerator;

import java.util.EnumSet;

import org.hibernate.dialect.Dialect;
import org.hibernate.generator.EventType;
import org.hibernate.generator.OnExecutionGenerator;

public class UpdatedAtGenerator implements OnExecutionGenerator {

    @Override
    public boolean referenceColumnsInSql(Dialect dialect) {
        return true;
    }

    @Override
    public boolean writePropertyValue() {
        return false;
    }

    @Override
    public String[] getReferencedColumnValues(Dialect dialect) {
        return new String[] { dialect.currentTimestamp() };
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT, EventType.UPDATE);
    }

}