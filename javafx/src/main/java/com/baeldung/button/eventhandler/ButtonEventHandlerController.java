package com.baeldung.button.eventhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class ButtonEventHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ButtonEventHandlerController.class);

    @FXML
    private Button button;

    @FXML
    private Label label;

    @FXML
    private void initialize() {
        button.setText("Click me");

        handleClickEvent();
        handleHoverEffect();
        reuseRightClickEventHandler();
    }

    private void handleClickEvent() {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logger.info("OnAction {}", event);
            }
        });

        button.setOnAction(event -> logger.info("OnAction {}", event));
        button.setOnAction(event -> logger.info("OnAction2 {}", event));
    }

    private void handleHoverEffect() {
        Effect shadow = new DropShadow();
        button.setOnMouseEntered(e -> button.setEffect(shadow));
        button.setOnMouseExited(e -> button.setEffect(null));
    }

    private void reuseRightClickEventHandler() {
        EventHandler<MouseEvent> rightClickHandler = event -> {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
                button.setFont(new Font(button.getFont()
                    .getSize() + 1));
            }
        };
        button.setOnMousePressed(rightClickHandler);
        label.setOnMousePressed(rightClickHandler);
    }
}
