package com.burakozdemir.cleanzy.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypingPayload {
    private Long conversationId;
    private Boolean isTyping;
    private Long userId;
    private String userName;
}
