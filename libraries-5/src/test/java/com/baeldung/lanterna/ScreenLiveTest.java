package com.baeldung.lanterna;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;

public class ScreenLiveTest {
    @Test
    void whenWrappingATerminal_thenWeHaveAUsableScreen() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (var screen = new TerminalScreen(terminal)) {
                screen.startScreen();
                Thread.sleep(5000);
            }
        }
    }

    @Test
    void whenCreatingAScreenDirectly_thenWeHaveAUsableScreen() throws Exception {
        try (Screen screen = new DefaultTerminalFactory().createScreen()) {
            screen.startScreen();
            Thread.sleep(5000);
        }
    }

    @Test
    void whenDrawingACharacter_thenTheCharacterAppearsAsDesired() throws Exception {
        try (Screen screen = new DefaultTerminalFactory().createScreen()) {
            screen.startScreen();
            screen.setCharacter(5, 5,
                new TextCharacter('!',
                    TextColor.ANSI.RED, TextColor.ANSI.YELLOW_BRIGHT,
                    SGR.UNDERLINE, SGR.BOLD));
            screen.refresh();
            Thread.sleep(5000);
        }
    }

    @Test
    void whenDrawingAString_thenTheCharacterAppearsAsDesired() throws Exception {
        try (Screen screen = new DefaultTerminalFactory().createScreen()) {
            screen.startScreen();
            var text = screen.newTextGraphics();
            text.setForegroundColor(TextColor.ANSI.RED);
            text.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
            text.putString(5, 5, "Hello");
            text.putString(6, 6, "World!");

            screen.refresh();
            Thread.sleep(5000);
        }
    }
}
