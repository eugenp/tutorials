package com.baeldung.timefoldsolver;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.SolverConfig;

public class ShiftScheduleApp {

    private static final Logger logger = LoggerFactory.getLogger(ShiftScheduleApp.class);

    public static void main(String[] args) {
        SolverFactory<ShiftSchedule> solverFactory = SolverFactory.create(new SolverConfig().withSolutionClass(ShiftSchedule.class)
            .withEntityClasses(Shift.class)
            .withConstraintProviderClass(ShiftScheduleConstraintProvider.class)
            // The solver runs only for 5 seconds on this small dataset.
            // It's recommended to run for at least 5 minutes ("5m") otherwise.
            .withTerminationSpentLimit(Duration.ofSeconds(5)));
        Solver<ShiftSchedule> solver = solverFactory.buildSolver();

        ShiftSchedule problem = loadProblem();
        ShiftSchedule solution = solver.solve(problem);
        printSolution(solution);
    }

    private static ShiftSchedule loadProblem() {
        LocalDate monday = LocalDate.of(2030, 4, 1);
        LocalDate tuesday = LocalDate.of(2030, 4, 2);
        return new ShiftSchedule(
            List.of(new Employee("Ann", Set.of("Bartender")), new Employee("Beth", Set.of("Waiter", "Bartender")), new Employee("Carl", Set.of("Waiter"))),
            List.of(new Shift(monday.atTime(6, 0), monday.atTime(14, 0), "Waiter"), new Shift(monday.atTime(9, 0), monday.atTime(17, 0), "Bartender"),
                new Shift(monday.atTime(14, 0), monday.atTime(22, 0), "Bartender"), new Shift(tuesday.atTime(6, 0), tuesday.atTime(14, 0), "Waiter"),
                new Shift(tuesday.atTime(14, 0), tuesday.atTime(22, 0), "Bartender")));
    }

    private static void printSolution(ShiftSchedule solution) {
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
