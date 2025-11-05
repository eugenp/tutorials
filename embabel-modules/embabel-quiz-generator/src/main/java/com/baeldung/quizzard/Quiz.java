package com.baeldung.quizzard;

import java.util.List;

record Quiz(List<QuizQuestion> questions) {

    record QuizQuestion(
        String question,
        List<String> options,
        String correctAnswer
    ) {
    }

}