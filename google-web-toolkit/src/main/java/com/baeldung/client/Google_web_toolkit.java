package com.baeldung.client;

import com.baeldung.shared.MessageService;
import com.baeldung.shared.MessageServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Google_web_toolkit implements EntryPoint {
    private final MessageServiceAsync messageServiceAsync = GWT.create(MessageService.class);

    public void onModuleLoad() {
        Button sendButton = new Button("Submit");
        TextBox nameField = new TextBox();
        nameField.setText("Hi there");

        Label warningLabel = new Label();

        sendButton.addStyleName("sendButton");

        RootPanel.get("nameFieldContainer").add(nameField);
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(warningLabel);

        Button closeButton = new Button("Thanks");
        closeButton.getElement().setId("closeButton");

        Label textToServerLabel = new Label();
        HTML serverResponseLabel = new HTML();
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.addStyleName("vPanel");
        vPanel.add(new HTML("Sending message to the server:"));
        vPanel.add(textToServerLabel);
        vPanel.add(new HTML("<br><b>Server replies:</b>"));
        vPanel.add(serverResponseLabel);
        vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        vPanel.add(closeButton);
        vPanel.setVisible(false);
        RootPanel.get("serverResponseContainer").add(vPanel);

        closeButton.addClickHandler(event -> {
            sendButton.setEnabled(true);
            sendButton.setFocus(true);
            vPanel.setVisible(false);
        });

        class MyHandler implements ClickHandler, KeyUpHandler {

            public void onClick(ClickEvent event) {
                sendMessageToServer();
            }

            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendMessageToServer();
                }
            }

            private void sendMessageToServer() {

                warningLabel.setText("");
                String textToServer = nameField.getText();
                if (textToServer == null || textToServer.isEmpty()) {
                    warningLabel.setText("Please enter the message");
                    return;
                }

                sendButton.setEnabled(false);
                textToServerLabel.setText(textToServer);
                serverResponseLabel.setText("");
                messageServiceAsync.sendMessage(textToServer, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML("server error occurred");
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(String result) {
                        serverResponseLabel.removeStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(result);
                        closeButton.setFocus(true);
                        vPanel.setVisible(true);
                    }
                });
            }
        }

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        sendButton.addClickHandler(handler);
        nameField.addKeyUpHandler(handler);
    }
}
