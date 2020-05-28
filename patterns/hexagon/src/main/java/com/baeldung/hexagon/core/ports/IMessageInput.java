package com.baeldung.hexagon.core.ports;

import java.util.Objects;

public interface IMessageInput {

    void post(PostMessageCommand command);

    class PostMessageCommand {
        private final String sender;
        private final String receiver;
        private final String content;

        public PostMessageCommand(String sender, String receiver, String content) {
            this.sender = sender;
            this.receiver = receiver;
            this.content = content;
        }

        public String getSender() {
            return sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public String getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            PostMessageCommand that = (PostMessageCommand) o;
            return sender.equals(that.sender) 
                && receiver.equals(that.receiver)
                && content.equals(that.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sender, receiver, content);
        }
    }
}
