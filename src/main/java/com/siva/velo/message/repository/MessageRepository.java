package com.siva.velo.message.repository;

import com.siva.velo.conversation.entity.Conversation;
import com.siva.velo.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationOrderByCreatedAtAsc(
            Conversation conversation
    );
}