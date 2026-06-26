package com.siva.velo.common.mapper;

import com.siva.velo.message.dto.MessageResponse;
import com.siva.velo.message.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse toMessageResponse(Message message) {

        MessageResponse response = new MessageResponse();

        response.setId(message.getId());
        response.setSenderId(message.getSender().getId());
        response.setSenderUsername(message.getSender().getUsername());
        response.setContent(message.getContent());
        response.setIsRead(message.getIsRead());
        response.setCreatedAt(message.getCreatedAt());

        return response;
    }
}