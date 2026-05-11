package com.burakozdemir.cleanzy.messaging.repository;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.messaging.entity.Conversation;
import com.burakozdemir.cleanzy.messaging.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByConversationOrderBySentAtDesc(Conversation conversation, Pageable pageable);

    long countByConversationAndIsReadFalseAndSenderNot(Conversation conversation, User sender);

    @Modifying
    @Query("""
            UPDATE Message m
            SET m.isRead = true
            WHERE m.conversation = :conversation
              AND m.sender <> :sender
              AND m.isRead = false
            """)
    void markAllAsReadExceptSender(
            @Param("conversation") Conversation conversation,
            @Param("sender") User sender
    );

    @Query("SELECT m FROM Message m WHERE m.conversation = :conv AND m.isRead = false AND m.sender <> :user")
    List<Message> findUnreadMessages(@Param("conv") Conversation conv, @Param("user") User user);
}
