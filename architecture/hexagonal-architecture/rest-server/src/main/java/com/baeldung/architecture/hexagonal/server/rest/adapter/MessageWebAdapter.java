package com.baeldung.architecture.hexagonal.server.rest.adapter;

import com.baeldung.architecture.hexagonal.common.annotation.WebAdapter;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.IGetAllMessageService;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.ISaveMessageService;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.server.rest.controller.IMessageRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@WebAdapter
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MessageWebAdapter implements IMessageRestController {

    private final IGetAllMessageService getAllMessageService;
    private final ISaveMessageService saveMessageService;

    @Override
    public List<Message> getAll() {
        return new ArrayList<>(getAllMessageService.handle().orElse(new HashSet<>()));
    }

    @Override
    public Message create(Message messageDTO) {
        return saveMessageService.handle(messageDTO).orElse(null);
    }
}
