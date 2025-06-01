package com.baeldung.openai;

public class Consts {

    private Consts() {
    }

    static final String INITIAL_MESSAGE = "Hello! What do you want to learn?";

    static final String DEVELOPER_MESSAGE = "You're helping me to create a curriculum"
                                            + "to learn programming."
                                            + "I want to use Baedlung website as the base."
                                            + "I will tell you the topic,"
                                            + "and you should return me the list of articles"
                                            + "and tutorials with links."
                                            + "Order the articles from beginner to more advanced,"
                                            + "so I can learn them one-by-one."
                                            + "Use only the articles from www.baeldung.com.";

    static final String DEVELOPER_MESSAGE_CONVERSATION = "Continue providing help following the same rules as before.";

    static final String ASSISTANT_INSTRUCTION = "You're a personal programming tutor specialized in research online learning courses.";

    static final String AI_MESSAGE = "Anything else you would like to know? Otherwise type EXIT to stop the program.";

}
