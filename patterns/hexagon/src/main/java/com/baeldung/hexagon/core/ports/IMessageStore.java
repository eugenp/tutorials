package com.baeldung.hexagon.core.ports;

import com.baeldung.hexagon.core.domain.Message;

public interface IMessageStore {

    void saveMessage(Message message);
}
