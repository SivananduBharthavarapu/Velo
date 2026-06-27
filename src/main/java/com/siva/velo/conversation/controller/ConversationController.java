package com.siva.velo.conversation.controller;

import com.siva.velo.common.response.ApiResponse;
import com.siva.velo.conversation.dto.ConversationSummaryResponse;
import com.siva.velo.conversation.dto.CreateConversationRequest;
import com.siva.velo.conversation.dto.CreateConversationResponse;
import com.siva.velo.conversation.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@Tag(
        name = "Conversation Management",
        description = "APIs for creating and managing conversations"
)
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(
            ConversationService conversationService) {

        this.conversationService = conversationService;
    }

    @Operation(
            summary = "Create conversation",
            description = "Creates a new conversation or returns the existing one."
    )
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

    @Operation(
            summary = "Get my conversations",
            description = "Returns all conversations of the authenticated user."
    )
    @GetMapping
    public ApiResponse<List<ConversationSummaryResponse>> getMyConversations(
            Authentication authentication) {

        List<ConversationSummaryResponse> response =
                conversationService.getMyConversations(authentication);

        return new ApiResponse<>(
                true,
                "Conversations fetched successfully",
                response
        );
    }
}