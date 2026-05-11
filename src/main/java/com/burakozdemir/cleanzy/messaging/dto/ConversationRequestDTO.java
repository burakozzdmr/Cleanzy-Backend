package com.burakozdemir.cleanzy.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationRequestDTO {

    @NotNull
    private Long participantId;
}
