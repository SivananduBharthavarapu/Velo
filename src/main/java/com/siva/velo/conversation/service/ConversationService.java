package com.siva.velo.conversation.service;

import com.siva.velo.conversation.dto.CreateConversationResponse;
import com.siva.velo.conversation.entity.Conversation;
import com.siva.velo.conversation.repository.ConversationRepository;
import com.siva.velo.user.entity.User;
import com.siva.velo.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ConversationService(
            ConversationRepository conversationRepository,
            UserRepository userRepository) {

        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }
    public CreateConversationResponse createConversation(
            Long receiverId,
            Authentication authentication) {

        User sender = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new EntityNotFoundException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Receiver not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException("You cannot create a conversation with yourself.");
        }

        return conversationRepository
                .findByUserOneAndUserTwoOrUserOneAndUserTwo(
                sender,
                receiver,
                receiver,
                sender
        )
                .map(conversation ->
                        new CreateConversationResponse(conversation.getId()))
                .orElseGet(() -> {

                    Conversation conversation = new Conversation();

                    conversation.setUserOne(sender);
                    conversation.setUserTwo(receiver);

                    Conversation savedConversation =
                            conversationRepository.save(conversation);

                    return new CreateConversationResponse(
                            savedConversation.getId());
                });
    }
}