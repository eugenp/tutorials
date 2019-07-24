package com.baeldung.memento;

public class TextWindow {

    private StringBuilder currentText;
    private Coordinates cursorPosition;

    public TextWindow() {
        this.currentText = new StringBuilder();
        this.cursorPosition = new Coordinates(0, 0);
    }

    public void addText(String text) {
        currentText.append(text);
    }

    public TextWindowState save() {
        return new TextWindowState(currentText.toString());
    }

    public void restore(TextWindowState save) {
        currentText = new StringBuilder(save.getText());
        cursorPosition = atTextEnd();
    }

    private Coordinates atTextEnd() {
        String[] lines = currentText.toString().split("\n");
        return new Coordinates(lines[lines.length - 1].length(), lines.length);
    }
}
