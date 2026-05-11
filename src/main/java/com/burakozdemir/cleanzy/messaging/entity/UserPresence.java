package com.burakozdemir.cleanzy.messaging.entity;

import com.burakozdemir.cleanzy.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_PRESENCE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPresence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    private User user;

    @Column(name = "IS_ONLINE", nullable = false)
    private boolean isOnline = false;

    @Column(name = "LAST_SEEN_AT")
    private LocalDateTime lastSeenAt;
}
