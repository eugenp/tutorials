package com.baeldung.assistant;

/**
 * Example implementation of AnswerPort that speaks message aloud.
 */
class VoiceAnswerAdapter implements AnswerPort {
    @Override public void answer(Model model) { /* Text-to-speech ... */ }
}
