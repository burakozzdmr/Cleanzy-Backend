package com.burakozdemir.cleanzy.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresencePayload {
    private Long userId;
    private Boolean isOnline;
    private LocalDateTime lastSeenAt;
}
