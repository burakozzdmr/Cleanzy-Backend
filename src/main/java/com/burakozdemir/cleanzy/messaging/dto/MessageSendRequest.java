package com.burakozdemir.cleanzy.messaging.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageSendRequest {
    private Long conversationId;
    private String content;
}
