package com.siva.velo.message.service;

import com.siva.velo.common.mapper.MessageMapper;
import com.siva.velo.conversation.entity.Conversation;
import com.siva.velo.conversation.repository.ConversationRepository;
import com.siva.velo.message.dto.MessageResponse;
import com.siva.velo.message.dto.SendMessageRequest;
import com.siva.velo.message.entity.Message;
import com.siva.velo.message.repository.MessageRepository;
import com.siva.velo.user.entity.User;
import com.siva.velo.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    public MessageService(
            MessageRepository messageRepository,
            ConversationRepository conversationRepository,
            UserRepository userRepository,
            MessageMapper messageMapper) {

        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
    }
    public List<MessageResponse> getConversationMessages(
            Long conversationId,
            Authentication authentication) {

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Conversation not found"));

        if (!conversation.hasParticipant(currentUser)) {
            throw new IllegalArgumentException(
                    "You are not a participant in this conversation.");
        }

        return messageRepository
                .findByConversationOrderByCreatedAtAsc(conversation)
                .stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }
    public MessageResponse sendMessage(
            SendMessageRequest request,
            Authentication authentication) {

        User sender = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new EntityNotFoundException("Sender not found"));

        Conversation conversation = conversationRepository
                .findById(request.getConversationId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Conversation not found"));
        if (!conversation.hasParticipant(sender)) {
            throw new IllegalArgumentException(
                    "You are not a participant in this conversation."
            );
        }

        Message message = new Message();

        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(request.getContent());

        Message savedMessage = messageRepository.save(message);

        conversation.setUpdatedAt(savedMessage.getCreatedAt());
        conversationRepository.save(conversation);
        return messageMapper.toMessageResponse(savedMessage);
    }
    public void markMessageAsRead(
            Long messageId,
            Authentication authentication) {

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found"));

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Message not found"));

        Conversation conversation = message.getConversation();

        if (!conversation.hasParticipant(currentUser)) {
            throw new IllegalArgumentException(
                    "You are not a participant in this conversation.");
        }

        message.setIsRead(true);

        messageRepository.save(message);
    }
}
