package com.baeldung.assistant;

/**
 * Example implementation of AnswerPort that prints text message to console
 */
class TextAnswerAdapter implements AnswerPort {
    @Override public void answer(Model model) {
        System.out.println(model.getTextMessage());
    }
}
