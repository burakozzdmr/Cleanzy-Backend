package com.burakozdemir.cleanzy.messaging.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageSendRequest {

    @Schema(description = "Conversation ID. Used by the WebSocket layer; ignored when conversationId is supplied as a path variable.")
    private Long conversationId;

    @NotNull
    @Schema(description = "ID of the user sending the message", example = "1")
    private Long senderId;

    @NotBlank
    @Schema(description = "Message text", example = "Merhaba, yarın müsait misiniz?")
    private String content;
}
