package com.baeldung.optaplanner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class CourseScheduleConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                roomConflict(constraintFactory)
        };
    }

    public Constraint roomConflict(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEachUniquePair(Lecture.class,
                                   Joiners.equal(Lecture::getRoomNumber), Joiners.equal(Lecture::getPeriod))
                .penalize("Room conflict", HardSoftScore.ONE_HARD);
    }
}
