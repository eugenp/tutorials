package com.baeldung.architecture.hexagonal.domain.message.spi.persistence;

import com.baeldung.architecture.hexagonal.common.control.SelfValidating;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface ISaveMessagePort {
    Optional<Message> handle(SaveMessageCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class SaveMessageCommand extends SelfValidating<SaveMessageCommand> {
        @NotNull String body;
        @NotNull String sender;
        @NotNull String receiver;

        public SaveMessageCommand(String body, String sender, String receiver) {
            this.body = body;
            this.sender = sender;
            this.receiver = receiver;
            this.validateSelf();
        }
    }
}

