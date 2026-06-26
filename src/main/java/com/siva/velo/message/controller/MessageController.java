package com.siva.velo.message.controller;

import com.siva.velo.common.response.ApiResponse;
import com.siva.velo.message.dto.MessageResponse;
import com.siva.velo.message.dto.SendMessageRequest;
import com.siva.velo.message.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ApiResponse<MessageResponse> sendMessage(
            @Valid @RequestBody SendMessageRequest request,
            Authentication authentication) {

        MessageResponse response =
                messageService.sendMessage(request, authentication);

        return new ApiResponse<>(
                true,
                "Message sent successfully",
                response
        );
    }
    @GetMapping("/conversation/{conversationId}")
    public ApiResponse<List<MessageResponse>> getConversationMessages(
            @PathVariable Long conversationId,
            Authentication authentication) {

        List<MessageResponse> response =
                messageService.getConversationMessages(
                        conversationId,
                        authentication
                );

        return new ApiResponse<>(
                true,
                "Messages fetched successfully",
                response
        );
    }
    @PatchMapping("/{messageId}/read")
    public ApiResponse<Void> markMessageAsRead(
            @PathVariable Long messageId,
            Authentication authentication) {

        messageService.markMessageAsRead(
                messageId,
                authentication
        );

        return new ApiResponse<>(
                true,
                "Message marked as read",
                null
        );
    }
}