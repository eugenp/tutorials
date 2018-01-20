package com.tut.questionnaire;

import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionService {

	public boolean getQuestion() {
		System.out.println("How are you doing today?");
		return true;
	}
}
