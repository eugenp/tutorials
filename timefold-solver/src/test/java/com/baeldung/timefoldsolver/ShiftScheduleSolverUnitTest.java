package com.baeldung.timefoldsolver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.config.solver.termination.TerminationConfig;

public class ShiftScheduleSolverUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(ShiftScheduleSolverUnitTest.class);

    @Test
    public void given3Employees5Shifts_whenSolve_thenScoreIsOptimalAndAllShiftsAssigned() {
        SolverFactory<ShiftSchedule> solverFactory = SolverFactory.create(new SolverConfig().withSolutionClass(ShiftSchedule.class)
            .withEntityClasses(Shift.class)
            .withConstraintProviderClass(ShiftScheduleConstraintProvider.class)
            // For this dataset, we know the optimal score in advance (which is normally not the case).
            // So we can use .withBestScoreLimit() instead of .withTerminationSpentLimit().
            .withTerminationConfig(new TerminationConfig().withBestScoreLimit("0hard/0soft")));
        Solver<ShiftSchedule> solver = solverFactory.buildSolver();

        ShiftSchedule problem = loadProblem();
        ShiftSchedule solution = solver.solve(problem);
        assertThat(solution.getScore()).isEqualTo(HardSoftScore.ZERO);
        assertThat(solution.getShifts().size()).isNotZero();
        for (Shift shift : solution.getShifts()) {
            assertThat(shift.getEmployee()).isNotNull();
        }
        printSolution(solution);
    }

    private ShiftSchedule loadProblem() {
        LocalDate monday = LocalDate.of(2030, 4, 1);
        LocalDate tuesday = LocalDate.of(2030, 4, 2);
        return new ShiftSchedule(
            List.of(new Employee("Ann", Set.of("Bartender")), new Employee("Beth", Set.of("Waiter", "Bartender")), new Employee("Carl", Set.of("Waiter"))),
            List.of(new Shift(monday.atTime(6, 0), monday.atTime(14, 0), "Waiter"), new Shift(monday.atTime(9, 0), monday.atTime(17, 0), "Bartender"),
                new Shift(monday.atTime(14, 0), monday.atTime(22, 0), "Bartender"), new Shift(tuesday.atTime(6, 0), tuesday.atTime(14, 0), "Waiter"),
                new Shift(tuesday.atTime(14, 0), tuesday.atTime(22, 0), "Bartender")));
    }

    private void printSolution(ShiftSchedule solution) {
        logger.info("Shift assignments");
        for (Shift shift : solution.getShifts()) {
            logger.info("  " + shift.getStart()
                .toLocalDate() + " " + shift.getStart()
                .toLocalTime() + " - " + shift.getEnd()
                .toLocalTime() + ": " + shift.getEmployee()
                .getName());
        }
    }

}
