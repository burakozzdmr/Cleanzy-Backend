package com.burakozdemir.cleanzy.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {
    private Long id;
    private Long senderId;
    private String senderName;
    private String senderPhotoURL;
    private String content;
    private LocalDateTime sentAt;
    private Boolean isRead;
    private Boolean isMine;
}
