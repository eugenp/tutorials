package com.tut.questionnaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("questionController")
public class QuestionnaireController {
    private QuestionService questionService;

//    @Autowired
//    public QuestionnaireController(QuestionService questionService) {
//        this.questionService = questionService;
//    }

    public QuestionService getQuestionService() {
    	return questionService;    	
    }

    @Autowired
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
}
