package com.siva.velo.message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SendMessageRequest {

    @NotNull(message = "Conversation id is required")
    private Long conversationId;

    @NotBlank(message = "Message content is required")
    private String content;

    public SendMessageRequest() {
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}