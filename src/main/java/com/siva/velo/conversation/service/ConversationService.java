package com.siva.velo.conversation.service;

import com.siva.velo.common.mapper.ConversationMapper;
import com.siva.velo.conversation.dto.ConversationSummaryResponse;
import com.siva.velo.conversation.dto.CreateConversationResponse;
import com.siva.velo.conversation.entity.Conversation;
import com.siva.velo.conversation.repository.ConversationRepository;
import com.siva.velo.exception.SelfConversationNotAllowedException;
import com.siva.velo.exception.UserNotFoundException;
import com.siva.velo.user.entity.User;
import com.siva.velo.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ConversationMapper conversationMapper;

    public ConversationService(
            ConversationRepository conversationRepository,
            UserRepository userRepository,
            ConversationMapper conversationMapper) {

        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.conversationMapper = conversationMapper;
    }

    public CreateConversationResponse createConversation(
            Long receiverId,
            Authentication authentication) {

        User sender = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                new UserNotFoundException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() ->
                        new UserNotFoundException("Receiver not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new SelfConversationNotAllowedException(
                    "You cannot create a conversation with yourself.");
        }

        return conversationRepository
                .findByUserOneAndUserTwoOrUserOneAndUserTwo(
                        sender,
                        receiver,
                        receiver,
                        sender
                )
                .map(conversationMapper::toCreateConversationResponse)
                .orElseGet(() -> {

                    Conversation conversation = new Conversation();

                    conversation.setUserOne(sender);
                    conversation.setUserTwo(receiver);

                    Conversation savedConversation =
                            conversationRepository.save(conversation);

                    return conversationMapper.toCreateConversationResponse(savedConversation);
                });
    }

    public List<ConversationSummaryResponse> getMyConversations(
            Authentication authentication) {

        User currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        List<Conversation> conversations =
                conversationRepository.findByUserOneOrUserTwo(
                        currentUser,
                        currentUser
                );

        return conversations.stream()
                .map(conversation ->
                        conversationMapper.toConversationSummaryResponse(
                                conversation,
                                currentUser))
                .toList();
    }
}