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
public class ConversationResponseDTO {
    private Long id;
    private Long otherUserId;
    private String otherUserName;
    private String otherUserPhotoURL;
    private Boolean otherUserIsOnline;
    private LocalDateTime otherUserLastSeen;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private Integer unreadCount;
}
