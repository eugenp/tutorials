package main.java.com.baeldung.core.domain;

public class Message {

    private String sourceText;

    private String targetText;

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTargetText() {
        return targetText;
    }

    public void setTargetText(String targetText) {
        this.targetText = targetText;
    }

    @Override
    public String toString() {
        return "Message [sourceText=" + sourceText + ", targetText=" + targetText + "]";
    }

}
