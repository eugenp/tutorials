package com.baeldung.ditypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireController {
    private QuestionService questionService;

//    @Autowired
//    public QuestionnaireController(QuestionService questionService) {
//        this.questionService = questionService;
//    }

    public QuestionService getQuestionService() {
    	return questionService;    	
    }

    //@Autowired
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
}
