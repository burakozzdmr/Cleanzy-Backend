package com.burakozdemir.cleanzy.messaging.websocket;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.messaging.dto.MessageResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageSendRequest;
import com.burakozdemir.cleanzy.messaging.dto.ReadReceiptPayload;
import com.burakozdemir.cleanzy.messaging.dto.TypingPayload;
import com.burakozdemir.cleanzy.messaging.entity.Conversation;
import com.burakozdemir.cleanzy.messaging.entity.Message;
import com.burakozdemir.cleanzy.messaging.repository.ConversationRepository;
import com.burakozdemir.cleanzy.messaging.repository.MessageRepository;
import com.burakozdemir.cleanzy.messaging.service.ConversationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final AuthRepository authRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ConversationServiceImpl conversationService;

    /**
     * Client publishes to /app/chat.send
     * Server broadcasts to /topic/conversation.{conversationId}
     */
    @MessageMapping("/chat.send")
    @Transactional
    public void sendMessage(MessageSendRequest request, Principal principal) {
        User sender = resolveUser(principal);

        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new BusinessException(ErrorType.CONVERSATION_NOT_FOUND));

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(request.getContent());
        message.setSentAt(LocalDateTime.now());
        message.setRead(false);

        Message saved = messageRepository.save(message);

        // Update conversation last message
        conversation.setLastMessage(request.getContent());
        conversation.setLastMessageAt(saved.getSentAt());
        conversationRepository.save(conversation);

        MessageResponseDTO dto = conversationService.toMessageResponseDTO(saved, sender.getId());

        messagingTemplate.convertAndSend(
                "/topic/conversation." + request.getConversationId(),
                dto
        );
    }

    /**
     * Client publishes to /app/chat.typing
     * Server broadcasts to /topic/conversation.{conversationId}.typing
     */
    @MessageMapping("/chat.typing")
    public void typing(TypingPayload payload, Principal principal) {
        User user = resolveUser(principal);

        TypingPayload outgoing = new TypingPayload(
                payload.getConversationId(),
                payload.getIsTyping(),
                user.getId(),
                user.getFullName()
        );

        messagingTemplate.convertAndSend(
                "/topic/conversation." + payload.getConversationId() + ".typing",
                outgoing
        );
    }

    /**
     * Client publishes to /app/chat.read
     * Server broadcasts to /topic/conversation.{conversationId}.read
     */
    @MessageMapping("/chat.read")
    @Transactional
    public void markRead(ReadReceiptPayload payload, Principal principal) {
        User user = resolveUser(principal);

        Conversation conversation = conversationRepository.findById(payload.getConversationId())
                .orElseThrow(() -> new BusinessException(ErrorType.CONVERSATION_NOT_FOUND));

        messageRepository.markAllAsReadExceptSender(conversation, user);

        ReadReceiptPayload outgoing = new ReadReceiptPayload(
                payload.getConversationId(),
                user.getId()
        );

        messagingTemplate.convertAndSend(
                "/topic/conversation." + payload.getConversationId() + ".read",
                outgoing
        );
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private User resolveUser(Principal principal) {
        if (principal == null) {
            throw new BusinessException(ErrorType.AUTHENTICATION_ERROR);
        }
        return authRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));
    }
}
