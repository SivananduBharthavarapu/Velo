package com.siva.velo.conversation.dto;

import jakarta.validation.constraints.NotNull;

public class CreateConversationRequest {

    @NotNull(message = "Receiver id is required")
    private Long receiverId;

    public CreateConversationRequest() {
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}