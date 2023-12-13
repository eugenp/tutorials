package com.baeldung.timefoldsolver;

import static ai.timefold.solver.core.api.score.stream.Joiners.equal;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;

public class ShiftScheduleConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] { atMostOneShiftPerDay(constraintFactory), requiredSkill(constraintFactory) };
    }

    public Constraint atMostOneShiftPerDay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Shift.class)
            .join(Shift.class, equal(shift -> shift.getStart()
                .toLocalDate()), equal(Shift::getEmployee))
            .filter((shift1, shift2) -> shift1 != shift2)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("At most one shift per day");
    }

    public Constraint requiredSkill(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Shift.class)
            .filter(shift -> !shift.getEmployee()
                .getSkills()
                .contains(shift.getRequiredSkill()))
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("Required skill");
    }

}
