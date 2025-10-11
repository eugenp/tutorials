package com.baeldung.springai.agenticpatterns.aimodels;

public final class CodeReviewClientPrompts {

    private CodeReviewClientPrompts() {
    }

    /**
     * Prompt for the code review of a given PR
     */
    public static final String CODE_REVIEW_PROMPT = """
      Given a PR link -> generate a map with proposed code improvements.
      The key should be {class-name}:{line-number}:{short-description}.
      The value should be the code in one line. For example, 1 proposed improvement could be for the line 'int x = 0, y = 0;':
      {"Client:23:'no multiple variables defined in 1 line'", "int x = 0;\\n int y = 0;"}
      Rules are, to follow the checkstyle and spotless rules set to the repo. Keep java code clear, readable and extensible.
      
      Finally, if it is not your first attempt, there might feedback provided to you, including your previous suggestion.
      You should reflect on it and improve the previous suggestions, or even add more.""";

    /**
     * Prompt for the evaluation of the result
     */
    public static final String EVALUATE_PROPOSED_IMPROVEMENTS_PROMPT = """
      Evaluate the suggested code improvements for correctness, time complexity, and best practices.
      
      Return a Map with one entry. The key is the value the evaluation. The value will be your feedback.
      
      The evaluation field must be one of: "PASS", "NEEDS_IMPROVEMENT", "FAIL"
      Use "PASS" only if all criteria are met with no improvements needed.
      """;
}
