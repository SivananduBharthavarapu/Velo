package com.siva.velo.conversation.controller;

import com.siva.velo.common.response.ApiResponse;
import com.siva.velo.conversation.dto.CreateConversationRequest;
import com.siva.velo.conversation.dto.CreateConversationResponse;
import com.siva.velo.conversation.service.ConversationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(
            ConversationService conversationService) {

        this.conversationService = conversationService;
    }

    @PostMapping
    public ApiResponse<CreateConversationResponse> createConversation(
            @Valid @RequestBody CreateConversationRequest request,
            Authentication authentication) {

        CreateConversationResponse response =
                conversationService.createConversation(
                        request.getReceiverId(),
                        authentication
                );

        return new ApiResponse<>(
                true,
                "Conversation created successfully",
                response
        );
    }
}