package com.baeldung.lanterna;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;

public class LowLevelLiveTest {
    @Test
    void whenPrintingCharacters_thenTheCharactersAppear() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            terminal.putCharacter('H');
            terminal.putCharacter('e');
            terminal.putCharacter('l');
            terminal.putCharacter('l');
            terminal.putCharacter('o');
            terminal.flush();
            Thread.sleep(15000);
            terminal.exitPrivateMode();
        }
    }

    @Test
    void whenMovingTheCursor_thenCharactersAppearAtTheNewPosition() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            terminal.setCursorPosition(10, 10);
            terminal.putCharacter('H');
            terminal.putCharacter('e');
            terminal.putCharacter('l');
            terminal.putCharacter('l');
            terminal.putCharacter('o');
            terminal.setCursorPosition(11, 11);
            terminal.putCharacter('W');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('l');
            terminal.putCharacter('d');
            terminal.flush();
            Thread.sleep(15000);
            terminal.exitPrivateMode();
        }
    }

    @Test
    void whenQueryingTheTerminalSize_thenWeGetTheCorrectSize() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            var size = terminal.getTerminalSize();
            System.out.println("Rows: " + size.getRows());
            System.out.println("Columns: " + size.getColumns());
            Thread.sleep(15000);
            terminal.exitPrivateMode();
        }
    }

    @Test
    void whenSettingCharacterColours_thenTheCharactersUseThoseColours() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            terminal.setForegroundColor(TextColor.ANSI.RED);
            terminal.putCharacter('H');
            terminal.putCharacter('e');
            terminal.putCharacter('l');
            terminal.putCharacter('l');
            terminal.putCharacter('o');
            terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
            terminal.setBackgroundColor(TextColor.ANSI.BLUE);
            terminal.putCharacter('W');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('l');
            terminal.putCharacter('d');
            terminal.flush();
            Thread.sleep(15000);
            terminal.exitPrivateMode();
        }
    }

    @Test
    void whenSettingSGRAttributes_thenTheCharactersUseThoseColours() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            terminal.enableSGR(SGR.BOLD);
            terminal.putCharacter('H');
            terminal.putCharacter('e');
            terminal.putCharacter('l');
            terminal.putCharacter('l');
            terminal.putCharacter('o');
            terminal.disableSGR(SGR.BOLD);
            terminal.enableSGR(SGR.UNDERLINE);
            terminal.putCharacter('W');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('l');
            terminal.putCharacter('d');
            terminal.resetColorAndSGR();
            terminal.flush();
            Thread.sleep(15000);
            terminal.exitPrivateMode();
        }
    }


    @Test
    void whenReadingInput_thenTheInputIsProvided() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            terminal.enterPrivateMode();
            while (true) {
                var keystroke = terminal.readInput();
                if (keystroke.getKeyType() == KeyType.Escape) {
                    break;
                } else if (keystroke.getKeyType() == KeyType.Character) {
                    terminal.putCharacter(keystroke.getCharacter());
                    terminal.flush();
                }
            }
            terminal.exitPrivateMode();
        }
    }
}
