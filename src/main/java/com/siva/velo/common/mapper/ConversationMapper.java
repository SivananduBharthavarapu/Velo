package com.siva.velo.common.mapper;

import com.siva.velo.conversation.dto.ConversationSummaryResponse;
import com.siva.velo.conversation.dto.CreateConversationResponse;
import com.siva.velo.conversation.entity.Conversation;
import com.siva.velo.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ConversationMapper {

    public CreateConversationResponse toCreateConversationResponse(
            Conversation conversation) {

        return new CreateConversationResponse(conversation.getId());
    }

    public ConversationSummaryResponse toConversationSummaryResponse(
            Conversation conversation,
            User currentUser) {

        User otherUser = conversation.getUserOne().getId().equals(currentUser.getId())
                ? conversation.getUserTwo()
                : conversation.getUserOne();

        ConversationSummaryResponse response =
                new ConversationSummaryResponse();

        response.setConversationId(conversation.getId());
        response.setParticipantId(otherUser.getId());
        response.setParticipantName(
                otherUser.getFirstName() + " " + otherUser.getLastName());
        response.setParticipantUsername(otherUser.getUsername());
        response.setUpdatedAt(conversation.getUpdatedAt());

        return response;
    }
}