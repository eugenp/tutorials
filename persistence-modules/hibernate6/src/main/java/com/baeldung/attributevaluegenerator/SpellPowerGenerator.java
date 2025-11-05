package com.baeldung.attributevaluegenerator;

import java.util.EnumSet;

import org.hibernate.dialect.Dialect;
import org.hibernate.generator.EventType;
import org.hibernate.generator.OnExecutionGenerator;

public class SpellPowerGenerator implements OnExecutionGenerator {

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
        String sql = "50 + (EXTRACT(DAY FROM CURRENT_DATE) % 30) * 2";
        return new String[] { sql };
    }
    
    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }
    
}