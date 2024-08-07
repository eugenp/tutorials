package com.baeldung.attributevaluegenerator;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

public class SortingHatHouseGenerator implements BeforeExecutionGenerator {

    private static final String[] HOUSES = { "Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin" };
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue,
            EventType eventType) {
        int houseIndex = RANDOM.nextInt(HOUSES.length);
        return HOUSES[houseIndex];
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }

}
