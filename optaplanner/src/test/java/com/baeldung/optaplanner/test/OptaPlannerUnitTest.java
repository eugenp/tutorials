package com.baeldung.optaplanner.test;

import com.baeldung.optaplanner.CourseSchedule;
import com.baeldung.optaplanner.CourseScheduleConstraintProvider;
import com.baeldung.optaplanner.Lecture;
import com.baeldung.optaplanner.ScoreCalculator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;

public class OptaPlannerUnitTest {

    Logger logger = LoggerFactory.getLogger("OptaPlannerUnitTest");

    static CourseSchedule unsolvedCourseSchedule;

    @BeforeAll
    public static void setUp() {

        unsolvedCourseSchedule = new CourseSchedule();

        for(long i = 0; i < 10; i++) {
            unsolvedCourseSchedule.getLectureList().add(new Lecture(i));
        }

        unsolvedCourseSchedule.getPeriodList().addAll(Arrays.asList(new Integer[] { 1, 2, 3 }));
        unsolvedCourseSchedule.getRoomList().addAll(Arrays.asList(new Integer[] { 1, 2 }));
    }

    @Test
    public void test_whenCustomJavaSolver() {

        SolverFactory<CourseSchedule> solverFactory = SolverFactory.create(new SolverConfig()
                                                                                   .withSolutionClass(CourseSchedule.class)
                                                                                   .withEntityClasses(Lecture.class)
                                                                                   .withEasyScoreCalculatorClass(ScoreCalculator.class)
                                                                                   .withTerminationSpentLimit(Duration.ofSeconds(1)));

        Solver<CourseSchedule> solver = solverFactory.buildSolver();
        CourseSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);

        Assert.assertNotNull(solvedCourseSchedule.getScore());
        Assert.assertEquals(-4, solvedCourseSchedule.getScore().getHardScore());
    }

    @Test
    public void test_whenConstraintStreamSolver() {

        SolverFactory<CourseSchedule> solverFactory = SolverFactory.create(new SolverConfig()
                                                                                   .withSolutionClass(CourseSchedule.class)
                                                                                   .withEntityClasses(Lecture.class)
                                                                                   .withConstraintProviderClass(CourseScheduleConstraintProvider.class)
                                                                                   .withTerminationSpentLimit(Duration.ofSeconds(1)));

        Solver<CourseSchedule> solver = solverFactory.buildSolver();
        CourseSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);

        logger.info(ScoreManager.create(solverFactory).explainScore(solvedCourseSchedule).getSummary());

        Assert.assertNotNull(solvedCourseSchedule.getScore());
        Assert.assertEquals(-4, solvedCourseSchedule.getScore().getHardScore());

    }

}
