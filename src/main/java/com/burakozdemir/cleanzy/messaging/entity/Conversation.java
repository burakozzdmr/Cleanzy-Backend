package com.burakozdemir.cleanzy.messaging.entity;

import com.burakozdemir.cleanzy.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CONVERSATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "CONVERSATION_PARTICIPANTS",
            joinColumns = @JoinColumn(name = "CONVERSATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private List<User> participants;

    @ManyToMany
    @JoinTable(
            name = "CONVERSATION_HIDDEN_BY",
            joinColumns = @JoinColumn(name = "CONVERSATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private Set<User> hiddenBy = new HashSet<>();

    @Column(name = "LAST_MESSAGE", length = 1000)
    private String lastMessage;

    @Column(name = "LAST_MESSAGE_AT")
    private LocalDateTime lastMessageAt;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
