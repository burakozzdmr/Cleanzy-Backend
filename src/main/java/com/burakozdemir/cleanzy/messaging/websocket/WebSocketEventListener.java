package com.burakozdemir.cleanzy.messaging.websocket;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.messaging.dto.PresencePayload;
import com.burakozdemir.cleanzy.messaging.entity.UserPresence;
import com.burakozdemir.cleanzy.messaging.repository.UserPresenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final UserPresenceRepository userPresenceRepository;
    private final AuthRepository authRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    @Transactional
    public void handleConnect(SessionConnectedEvent event) {
        Principal principal = event.getUser();
        if (principal == null) return;

        authRepository.findByEmail(principal.getName()).ifPresent(user -> {
            updatePresence(user, true);
            broadcastPresence(user, true);
        });
    }

    @EventListener
    @Transactional
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal principal = accessor.getUser();
        if (principal == null) return;

        authRepository.findByEmail(principal.getName()).ifPresent(user -> {
            updatePresence(user, false);
            broadcastPresence(user, false);
        });
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void updatePresence(User user, boolean online) {
        UserPresence presence = userPresenceRepository.findByUser(user)
                .orElseGet(() -> {
                    UserPresence p = new UserPresence();
                    p.setUser(user);
                    return p;
                });

        presence.setOnline(online);
        presence.setLastSeenAt(LocalDateTime.now());
        userPresenceRepository.save(presence);
    }

    private void broadcastPresence(User user, boolean online) {
        UserPresence presence = userPresenceRepository.findByUser(user).orElse(null);

        PresencePayload payload = new PresencePayload(
                user.getId(),
                online,
                presence != null ? presence.getLastSeenAt() : LocalDateTime.now()
        );

        messagingTemplate.convertAndSend("/topic/presence." + user.getId(), payload);
    }
}
