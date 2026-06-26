package com.siva.velo.conversation.repository;

import com.siva.velo.conversation.entity.Conversation;
import com.siva.velo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByUserOneAndUserTwo(User userOne, User userTwo);

    Optional<Conversation> findByUserOneAndUserTwoOrUserOneAndUserTwo(
            User userOne,
            User userTwo,
            User userTwoAgain,
            User userOneAgain
    );
    List<Conversation> findByUserOneOrUserTwo(
            User userOne,
            User userTwo
    );
}