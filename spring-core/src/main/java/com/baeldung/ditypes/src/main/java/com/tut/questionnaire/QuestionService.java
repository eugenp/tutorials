package com.tut.questionnaire;

import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionService {

	public String getQuestion() {
		return "How are you doing today?";
	}
}
