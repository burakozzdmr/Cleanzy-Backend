package com.burakozdemir.cleanzy.messaging.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.messaging.dto.ConversationRequestDTO;
import com.burakozdemir.cleanzy.messaging.dto.ConversationResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageSendRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ConversationController {

    ResponseEntity<ApiSuccessResponse<List<ConversationResponseDTO>>> getConversations(Long userId);

    ResponseEntity<ApiSuccessResponse<ConversationResponseDTO>> createOrGetConversation(
            Long currentUserId,
            ConversationRequestDTO request
    );

    ResponseEntity<ApiSuccessResponse<Page<MessageResponseDTO>>> getMessages(
            Long conversationId,
            Long currentUserId,
            int page,
            int size
    );

    ResponseEntity<ApiSuccessResponse<Void>> deleteConversation(Long conversationId, Long userId);

    ResponseEntity<ApiSuccessResponse<MessageResponseDTO>> sendMessage(
            Long conversationId,
            MessageSendRequest request
    );
}
