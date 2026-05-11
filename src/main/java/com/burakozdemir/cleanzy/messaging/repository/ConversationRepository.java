package com.burakozdemir.cleanzy.messaging.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.messaging.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("""
            SELECT c FROM Conversation c
            JOIN c.participants p
            WHERE p = :user
              AND :user NOT MEMBER OF c.hiddenBy
            ORDER BY c.lastMessageAt DESC NULLS LAST
            """)
    List<Conversation> findAllByParticipantAndNotHidden(@Param("user") User user);

    @Query("""
            SELECT c FROM Conversation c
            WHERE :user1 MEMBER OF c.participants
              AND :user2 MEMBER OF c.participants
              AND SIZE(c.participants) = 2
            """)
    Optional<Conversation> findByTwoParticipants(
            @Param("user1") User user1,
            @Param("user2") User user2
    );
}
