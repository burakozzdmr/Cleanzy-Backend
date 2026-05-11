package com.burakozdemir.cleanzy.messaging.service;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.messaging.dto.ConversationResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageSendRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ConversationService {

    ApiSuccessResponse<List<ConversationResponseDTO>> getConversations(Long userId);

    ApiSuccessResponse<ConversationResponseDTO> createOrGetConversation(Long currentUserId, Long participantId);

    ApiSuccessResponse<Page<MessageResponseDTO>> getMessages(Long conversationId, Long currentUserId, int page, int size);

    ApiSuccessResponse<Void> deleteConversation(Long conversationId, Long userId);

    ApiSuccessResponse<MessageResponseDTO> sendMessage(Long conversationId, MessageSendRequest request);
}
