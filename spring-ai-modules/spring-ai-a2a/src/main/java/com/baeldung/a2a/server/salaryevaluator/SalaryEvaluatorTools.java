package com.baeldung.a2a.server.salaryevaluator;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
class SalaryEvaluatorTools {

    @Tool(
        name = "evaluate-salary",
        description = "Compares a candidate's expected salary for a given job title"
    )
    SalaryEvaluationResult evaluateSalary(
        @ToolParam(description = "The job title being applied for") String jobTitle,
        @ToolParam(description = "Candidate's expected annual salary") int expectedSalary
    ) {
        SalaryRange salaryRange = budgetLookup(jobTitle);
        Verdict verdict = expectedSalary > salaryRange.max()
            ? Verdict.ABOVE_BUDGET
            : expectedSalary < salaryRange.min()
            ? Verdict.BELOW_BUDGET
            : Verdict.WITHIN_BUDGET;
        return new SalaryEvaluationResult(verdict);
    }

    private SalaryRange budgetLookup(String jobTitle) {
        return new SalaryRange(80000, 120000);
    }

    record SalaryRange(
        int min,
        int max
    ) {}

    record SalaryEvaluationResult(
        Verdict verdict
    ) {}

    enum Verdict {
        WITHIN_BUDGET,
        ABOVE_BUDGET,
        BELOW_BUDGET
    }
}