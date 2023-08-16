package com.baeldung.telegram;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Map;

import static com.baeldung.telegram.Constants.START_TEXT;
import static com.baeldung.telegram.UserState.*;

public class ResponseHandler {
    private final SilentSender sender;
    private final Map<Long, UserState> chatStates;

    public ResponseHandler(SilentSender sender, DBContext db) {
        this.sender = sender;
        chatStates = db.getMap(Constants.CHAT_STATES);
    }

    public void replyToStart(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(START_TEXT);
        sender.execute(message);
        chatStates.put(chatId, AWAITING_NAME);
    }

public void replyToButtons(long chatId, Message message) {
    if (message.getText().equalsIgnoreCase("/stop")) {
        stopChat(chatId);
    }

    switch (chatStates.get(chatId)) {
        case AWAITING_NAME -> replyToName(chatId, message);
        case FOOD_DRINK_SELECTION -> replyToFoodDrinkSelection(chatId, message);
        case PIZZA_TOPPINGS -> replyToPizzaToppings(chatId, message);
        case AWAITING_CONFIRMATION -> replyToOrder(chatId, message);
        default -> unexpectedMessage(chatId);
    }
}

    private void unexpectedMessage(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("I did not expect that.");
        sender.execute(sendMessage);
    }

private void stopChat(long chatId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText("Thank you for your order. See you soon!\nPress /start to order again");
    chatStates.remove(chatId);
    sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
    sender.execute(sendMessage);
}

    private void replyToOrder(long chatId, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if ("yes".equalsIgnoreCase(message.getText())) {
            sendMessage.setText("We will deliver it soon. Thank you!\nOrder another?");
            sendMessage.setReplyMarkup(KeyboardFactory.getPizzaOrDrinkKeyboard());
            sender.execute(sendMessage);
            chatStates.put(chatId, FOOD_DRINK_SELECTION);
        } else if ("no".equalsIgnoreCase(message.getText())) {
            stopChat(chatId);
        } else {
            sendMessage.setText("Please select yes or no");
            sendMessage.setReplyMarkup(KeyboardFactory.getYesOrNo());
            sender.execute(sendMessage);
        }
    }

    private void replyToPizzaToppings(long chatId, Message message) {
        if ("margherita".equalsIgnoreCase(message.getText())) {
            promptWithKeyboardForState(chatId, "You selected Margherita Pizza.\nWe will deliver it soon. Thank you!\nOrder again?",
                    KeyboardFactory.getYesOrNo(), AWAITING_CONFIRMATION);
        } else if ("pepperoni".equalsIgnoreCase(message.getText())) {
            promptWithKeyboardForState(chatId, "We finished the Pepperoni Pizza.\nSelect another Topping",
                    KeyboardFactory.getPizzaToppingsKeyboard(), PIZZA_TOPPINGS);
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("We don't sell " + message.getText() + " Pizza.\nSelect the toppings!");
            sendMessage.setReplyMarkup(KeyboardFactory.getPizzaToppingsKeyboard());
            sender.execute(sendMessage);
        }
    }

    private void promptWithKeyboardForState(long chatId, String text, ReplyKeyboard YesOrNo, UserState awaitingReorder) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(YesOrNo);
        sender.execute(sendMessage);
        chatStates.put(chatId, awaitingReorder);
    }

private void replyToFoodDrinkSelection(long chatId, Message message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    if ("drink".equalsIgnoreCase(message.getText())) {
        sendMessage.setText("We don't sell drinks.\nBring your own drink!! :)");
        sendMessage.setReplyMarkup(KeyboardFactory.getPizzaOrDrinkKeyboard());
        sender.execute(sendMessage);
    } else if ("pizza".equalsIgnoreCase(message.getText())) {
        sendMessage.setText("We love Pizza in here.\nSelect the toppings!");
        sendMessage.setReplyMarkup(KeyboardFactory.getPizzaToppingsKeyboard());
        sender.execute(sendMessage);
        chatStates.put(chatId, UserState.PIZZA_TOPPINGS);
    } else {
        sendMessage.setText("We don't sell " + message.getText() + ". Please select from the options below.");
        sendMessage.setReplyMarkup(KeyboardFactory.getPizzaOrDrinkKeyboard());
        sender.execute(sendMessage);
    }
}

private void replyToName(long chatId, Message message) {
    promptWithKeyboardForState(chatId, "Hello " + message.getText() + ". What would you like to have?",
      KeyboardFactory.getPizzaOrDrinkKeyboard(),
      UserState.FOOD_DRINK_SELECTION);
}

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }
}