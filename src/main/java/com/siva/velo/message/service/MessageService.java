package com.siva.velo.message.service;

import com.siva.velo.common.mapper.MessageMapper;
import com.siva.velo.conversation.entity.Conversation;
import com.siva.velo.conversation.repository.ConversationRepository;
import com.siva.velo.exception.ConversationNotFoundException;
import com.siva.velo.exception.MessageNotFoundException;
import com.siva.velo.exception.UnauthorizedConversationException;
import com.siva.velo.exception.UserNotFoundException;
import com.siva.velo.message.dto.MessageResponse;
import com.siva.velo.message.dto.SendMessageRequest;
import com.siva.velo.message.entity.Message;
import com.siva.velo.message.repository.MessageRepository;
import com.siva.velo.user.entity.User;
import com.siva.velo.user.repository.UserRepository;
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

    public MessageResponse sendMessage(
            SendMessageRequest request,
            Authentication authentication) {

        User sender = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new UserNotFoundException("Sender not found"));

        Conversation conversation = conversationRepository
                .findById(request.getConversationId())
                .orElseThrow(() ->
                        new ConversationNotFoundException("Conversation not found"));

        if (!conversation.hasParticipant(sender)) {
            throw new UnauthorizedConversationException(
                    "You are not a participant in this conversation.");
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

    public List<MessageResponse> getConversationMessages(
            Long conversationId,
            Authentication authentication) {

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() ->
                        new ConversationNotFoundException("Conversation not found"));

        if (!conversation.hasParticipant(currentUser)) {
            throw new UnauthorizedConversationException(
                    "You are not a participant in this conversation.");
        }

        return messageRepository
                .findByConversationOrderByCreatedAtAsc(conversation)
                .stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }

    public void markMessageAsRead(
            Long messageId,
            Authentication authentication) {

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() ->
                        new MessageNotFoundException("Message not found"));

        Conversation conversation = message.getConversation();

        if (!conversation.hasParticipant(currentUser)) {
            throw new UnauthorizedConversationException(
                    "You are not a participant in this conversation.");
        }

        message.setIsRead(true);

        messageRepository.save(message);
    }
}