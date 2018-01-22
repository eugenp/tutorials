package com.tut.questionnaire;

@Component
public class QuestionnaireController {
    private QuestionService;

    public QuestionnaireController(QuestionService questionService) {
        this.questionService = questionService;
    }
    
    public QuestionService getQuestionService() {
    	return questionService;    	
    }
}
