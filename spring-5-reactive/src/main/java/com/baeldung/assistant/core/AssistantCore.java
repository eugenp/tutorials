package com.baeldung.assistant;

/**
 * Smart voice assistant
 */
public class AssistantCore implements CommandListenerPort {
    private AnswerPort outPort;

    public AssistantCore(AnswerPort outPort) {
        setOutPort(outPort);
    }

    public void setOutPort(AnswerPort outPort) {
        if (outPort == null)
            throw new IllegalArgumentException("The AnswerPort can't be null!");
        this.outPort = outPort;
    }

    @Override public void onUserCommand(String text) {
        Model model = analyzeCommandAndPrepareAnswer(text);
        outPort.answer(model);
    }

    private Model analyzeCommandAndPrepareAnswer(String text) {
        Model answer = new Model();
        if (text.contains("hi"))
            answer.setTextMessage("hi!");
        else if (text.contains("bye"))
            answer.setTextMessage("bye!");
        else
            answer.setTextMessage("sorry, I don't understand.");
        return answer;
    }
}
