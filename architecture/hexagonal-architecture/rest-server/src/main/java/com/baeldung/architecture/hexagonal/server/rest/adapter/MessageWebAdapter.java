package com.baeldung.architecture.hexagonal.server.rest.adapter;

import com.baeldung.architecture.hexagonal.common.annotation.WebAdapter;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.IGetAllMessageService;
import com.baeldung.architecture.hexagonal.domain.message.api.persistence.ISaveMessageService;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.server.rest.controller.IMessageRestController;
import com.baeldung.architecture.hexagonal.server.rest.dto.MessageDTO;
import com.baeldung.architecture.hexagonal.server.rest.mapper.IMessageWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@WebAdapter
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MessageWebAdapter implements IMessageRestController {

    private final IGetAllMessageService getAllMessageService;
    private final ISaveMessageService saveMessageService;

    @Setter(onMethod_ = { @Autowired })
    private IMessageWebMapper mapper;

    @Override
    public List<MessageDTO> getAll() {

        return getAllMessageService.handle().orElse(new HashSet<>()).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public MessageDTO create(MessageDTO messageDTO) {

        Message message = mapper.toModel(messageDTO);

        return mapper.toDTO(saveMessageService.handle(message).orElse(null));
    }
}
