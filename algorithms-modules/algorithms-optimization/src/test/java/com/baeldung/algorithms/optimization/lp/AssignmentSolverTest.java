package com.baeldung.algorithms.optimization.lp;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class AssignmentSolverTest {

    @ParameterizedTest
    @MethodSource("assignmentMatrices")
    void whenSolveAssignmentMatrixByOjAlgo_thenTheTotalCostIsMinimized(double[][] cost, double expectedTotalCost, double[][] expectedAssignment) {
        // given
        AssignmentSolver solver = new OjAlgoAssignmentSolver();

        // when
        AssignmentSolution solution = solver.solve(cost);

        // then
        assertThat(solution.getTotalCost()).isEqualTo(expectedTotalCost);
        assertThat(solution.getAssignment()).isEqualTo(expectedAssignment);
    }

    @ParameterizedTest
    @MethodSource("assignmentMatrices")
    void whenSolveAssignmentMatrixByCommonMaths_thenTheTotalCostIsMinimized(double[][] cost, double expectedTotalCost, double[][] expectedAssignment) {
        // given
        AssignmentSolver solver = new CommonsMathAssignmentSolver();

        // when
        AssignmentSolution solution = solver.solve(cost);

        // then
        assertThat(solution.getTotalCost()).isEqualTo(expectedTotalCost);
        assertThat(solution.getAssignment()).isEqualTo(expectedAssignment);
    }

    static Stream<Arguments> assignmentMatrices() {
        return Stream.of(
            Arguments.of(
                new double[][] {
                    {27, 6, 21},
                    {18, 12, 9},
                    {15, 24, 3}
                },
                27.0,
                new double[][] {
                    {0, 1, 0},
                    {1, 0, 0},
                    {0, 0, 1}
                }
            ),
            Arguments.of(
                new double[][] {
                    {9, 2, 7, 8},
                    {6, 4, 3, 7},
                    {5, 8, 1, 8},
                    {7, 6, 9, 4}
                },
                13.0,
                new double[][] {
                    {0, 1, 0, 0},
                    {1, 0, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
                }
            )
        );
    }
}
