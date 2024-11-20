package com.baeldung.lanterna;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class GuiLiveTest {
    @Test
    void whenStartingAGui_thenWeHaveAGui() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (var screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                var gui = new MultiWindowTextGUI(screen);
                gui.updateScreen();

                Thread.sleep(5000);
            }
        }
    }

    @Test
    void whenAddingAMessageDialog_thenTheWindowDisplays() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (var screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                var gui = new MultiWindowTextGUI(screen);
                var window = new MessageDialogBuilder()
                    .setTitle("Message Dialog")
                    .setText("Dialog Contents")
                    .build();
                gui.addWindow(window);

                gui.updateScreen();

                Thread.sleep(5000);
            }
        }
    }

    @Test
    void whenAddingABlankWindow_thenTheWindowDisplays() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (var screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                var gui = new MultiWindowTextGUI(screen);
                var window = new BasicWindow("Basic Window");
                gui.addWindow(window);

                gui.updateScreen();

                Thread.sleep(5000);
            }
        }
    }

    @Test
    void whenChangingTheWindowHints_thenTheWindowDisplaysAsDesired() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (var screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                var gui = new MultiWindowTextGUI(screen);
                var window = new BasicWindow("Basic Window");
                window.setHints(Set.of(Window.Hint.CENTERED,
                    Window.Hint.NO_POST_RENDERING,
                    Window.Hint.EXPANDED));
                gui.addWindow(window);

                gui.updateScreen();

                Thread.sleep(5000);
            }
        }
    }

    @Test
    void whenAddingALabel_thenTheLabelDisplays() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (var screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                var gui = new MultiWindowTextGUI(screen);
                var window = new BasicWindow("Basic Window");
                window.setComponent(new Label("This is a label"));
                gui.addWindow(window);

                gui.updateScreen();

                Thread.sleep(5000);
            }
        }
    }

    @Test
    void whenAddingMultipleLabels_thenTheLabelsAreDisplayedCorrectly() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (var screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                var gui = new MultiWindowTextGUI(screen);
                var window = new BasicWindow("Basic Window");
                var innerPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
                innerPanel.addComponent(new Label("Left"));
                innerPanel.addComponent(new Label("Middle"));
                innerPanel.addComponent(new Label("Right"));

                var outerPanel = new Panel(new LinearLayout(Direction.VERTICAL));
                outerPanel.addComponent(new Label("Top"));
                outerPanel.addComponent(innerPanel);
                outerPanel.addComponent(new Label("Bottom"));

                window.setComponent(outerPanel);

                gui.addWindow(window);

                gui.updateScreen();
                Thread.sleep(5000);
            }
        }
    }

    @Test
    void whenAddingATextbox_thenTheTextboxCanBeTypedInto() throws Exception {
        try (Terminal terminal = new DefaultTerminalFactory().createTerminal()) {
            try (var screen = new TerminalScreen(terminal)) {
                screen.startScreen();

                var gui = new MultiWindowTextGUI(screen);

                var window = new BasicWindow("Basic Window");

                var textbox = new TextBox();
                var button = new Button("OK");
                button.addListener((b) -> {
                    System.out.println(textbox.getText());
                    window.close();
                });

                var panel = new Panel(new LinearLayout(Direction.VERTICAL));
                panel.addComponent(textbox);
                panel.addComponent(button);

                window.setComponent(panel);
                gui.addWindow(window);

                gui.updateScreen();
                window.waitUntilClosed();
            }
        }
    }
}
