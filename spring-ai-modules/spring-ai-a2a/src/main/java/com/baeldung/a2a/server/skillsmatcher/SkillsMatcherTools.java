package com.baeldung.a2a.server.skillsmatcher;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class SkillsMatcherTools {

    @Tool(
        name = "match-skills",
        description = "Compares a candidate's skills against a job's required skills and returns a fit score"
    )
    SkillsMatchResult matchSkills(
        @ToolParam(description = "Candidate skills, comma-separated") String candidateSkills,
        @ToolParam(description = "Required job skills, comma-separated") String requiredSkills
    ) {
        Set<String> candidateSkillSet = normalize(candidateSkills);
        Set<String> requiredSkillSet = normalize(requiredSkills);

        Set<String> matchedSkillSet = new HashSet<>(candidateSkillSet);
        matchedSkillSet.retainAll(requiredSkillSet);

        Set<String> missingSkillSet = new HashSet<>(requiredSkillSet);
        missingSkillSet.removeAll(candidateSkillSet);

        int score = requiredSkillSet.isEmpty() ? 0 : (matchedSkillSet.size() * 100) / requiredSkillSet.size();
        Verdict verdict = score >= 75
            ? Verdict.STRONG_MATCH
            : score >= 40
            ? Verdict.PARTIAL_MATCH
            : Verdict.WEAK_MATCH;

        return new SkillsMatchResult(score, verdict, matchedSkillSet, missingSkillSet);
    }

    private Set<String> normalize(String csv) {
        return Arrays.stream(csv.split(","))
            .map(String::trim)
            .map(String::toLowerCase)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toSet());
    }

    record SkillsMatchResult(
        int score,
        Verdict verdict,
        Set<String> matchedSkills,
        Set<String> missingSkills
    ) {}

    enum Verdict {
        STRONG_MATCH,
        PARTIAL_MATCH,
        WEAK_MATCH
    }
}