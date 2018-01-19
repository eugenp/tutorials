package com.baeldung.ditypes;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {

	public void getQuestion() {
		System.out.println("How are you doing today?");
	}
}
