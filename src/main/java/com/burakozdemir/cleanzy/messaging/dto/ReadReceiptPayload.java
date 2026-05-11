package com.burakozdemir.cleanzy.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadReceiptPayload {
    private Long conversationId;
    private Long userId;
}
