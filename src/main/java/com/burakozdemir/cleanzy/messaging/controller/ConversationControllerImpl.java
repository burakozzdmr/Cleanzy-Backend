package com.burakozdemir.cleanzy.messaging.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.messaging.dto.ConversationRequestDTO;
import com.burakozdemir.cleanzy.messaging.dto.ConversationResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageSendRequest;
import com.burakozdemir.cleanzy.messaging.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/v1/conversations")
@RequiredArgsConstructor
@Tag(name = "Conversations", description = "Real-time messaging — REST layer")
@SecurityRequirement(name = "bearerAuth")
public class ConversationControllerImpl implements ConversationController {

    private final ConversationService conversationService;

    @Override
    @GetMapping
    @Operation(summary = "Get all conversations for a user, ordered by lastMessageAt DESC")
    public ResponseEntity<ApiSuccessResponse<List<ConversationResponseDTO>>> getConversations(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(conversationService.getConversations(userId));
    }

    @Override
    @PostMapping
    @Operation(summary = "Create or retrieve an existing conversation between two users")
    public ResponseEntity<ApiSuccessResponse<ConversationResponseDTO>> createOrGetConversation(
            @RequestParam Long currentUserId,
            @Valid @RequestBody ConversationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conversationService.createOrGetConversation(currentUserId, request.getParticipantId()));
    }

    @Override
    @GetMapping("/{conversationId}/messages")
    @Operation(summary = "Get paginated messages for a conversation (marks as read)")
    public ResponseEntity<ApiSuccessResponse<Page<MessageResponseDTO>>> getMessages(
            @PathVariable Long conversationId,
            @RequestParam Long currentUserId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return ResponseEntity.ok(conversationService.getMessages(conversationId, currentUserId, page, size));
    }

    @Override
    @PostMapping("/{conversationId}/messages")
    @Operation(summary = "Send a message in a conversation (REST fallback — prefer WebSocket for real-time delivery)")
    public ResponseEntity<ApiSuccessResponse<MessageResponseDTO>> sendMessage(
            @PathVariable Long conversationId,
            @Valid @RequestBody MessageSendRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(conversationService.sendMessage(conversationId, request));
    }

    @Override
    @DeleteMapping("/{conversationId}")
    @Operation(summary = "Soft-delete (hide) the conversation for the requesting user only")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteConversation(
            @PathVariable Long conversationId,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(conversationService.deleteConversation(conversationId, userId));
    }
}
