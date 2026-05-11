package com.burakozdemir.cleanzy.messaging.service;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.auth.repository.AuthRepository;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.customer.entity.Customer;
import com.burakozdemir.cleanzy.customer.repository.CustomerRepository;
import com.burakozdemir.cleanzy.messaging.dto.ConversationResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageResponseDTO;
import com.burakozdemir.cleanzy.messaging.dto.MessageSendRequest;
import com.burakozdemir.cleanzy.messaging.entity.Conversation;
import com.burakozdemir.cleanzy.messaging.entity.Message;
import com.burakozdemir.cleanzy.messaging.entity.UserPresence;
import com.burakozdemir.cleanzy.messaging.repository.ConversationRepository;
import com.burakozdemir.cleanzy.messaging.repository.MessageRepository;
import com.burakozdemir.cleanzy.messaging.repository.UserPresenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserPresenceRepository userPresenceRepository;
    private final AuthRepository authRepository;
    private final CleanerRepository cleanerRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public ApiSuccessResponse<List<ConversationResponseDTO>> getConversations(Long userId) {
        User user = authRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        List<ConversationResponseDTO> result = conversationRepository
                .findAllByParticipantAndNotHidden(user)
                .stream()
                .map(conv -> toConversationResponseDTO(conv, user))
                .toList();

        return ApiSuccessResponse.of(result, result.size());
    }

    @Override
    @Transactional
    public ApiSuccessResponse<ConversationResponseDTO> createOrGetConversation(
            Long currentUserId,
            Long participantId
    ) {
        User currentUser = authRepository.findById(currentUserId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));
        User participant = authRepository.findById(participantId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        Conversation conversation = conversationRepository
                .findByTwoParticipants(currentUser, participant)
                .orElseGet(() -> {
                    Conversation newConv = new Conversation();
                    newConv.setParticipants(List.of(currentUser, participant));
                    return conversationRepository.save(newConv);
                });

        // If user had hidden it, un-hide
        conversation.getHiddenBy().remove(currentUser);
        conversationRepository.save(conversation);

        return ApiSuccessResponse.of(toConversationResponseDTO(conversation, currentUser));
    }

    @Override
    @Transactional
    public ApiSuccessResponse<Page<MessageResponseDTO>> getMessages(
            Long conversationId,
            Long currentUserId,
            int page,
            int size
    ) {
        User currentUser = authRepository.findById(currentUserId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BusinessException(ErrorType.CONVERSATION_NOT_FOUND));

        // Mark unread messages as read
        messageRepository.markAllAsReadExceptSender(conversation, currentUser);

        Page<MessageResponseDTO> messages = messageRepository
                .findByConversationOrderBySentAtDesc(conversation, PageRequest.of(page, size))
                .map(msg -> toMessageResponseDTO(msg, currentUserId));

        return ApiSuccessResponse.of(messages);
    }

    @Override
    @Transactional
    public ApiSuccessResponse<MessageResponseDTO> sendMessage(Long conversationId, MessageSendRequest request) {
        User sender = authRepository.findById(request.getSenderId())
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BusinessException(ErrorType.CONVERSATION_NOT_FOUND));

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(request.getContent());
        message.setRead(false);

        Message saved = messageRepository.save(message);

        conversation.setLastMessage(request.getContent());
        conversation.setLastMessageAt(saved.getSentAt());
        conversationRepository.save(conversation);

        MessageResponseDTO dto = MessageResponseDTO.builder()
                .id(saved.getId())
                .conversationId(conversationId)
                .senderId(sender.getId())
                .senderName(sender.getFullName())
                .senderPhotoURL(resolvePhotoURL(sender))
                .content(saved.getContent())
                .sentAt(saved.getSentAt())
                .isRead(false)
                .isMine(true)
                .build();

        return ApiSuccessResponse.of(dto);
    }

    @Override
    @Transactional
    public ApiSuccessResponse<Void> deleteConversation(Long conversationId, Long userId) {
        User user = authRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BusinessException(ErrorType.CONVERSATION_NOT_FOUND));

        conversation.getHiddenBy().add(user);
        conversationRepository.save(conversation);

        return ApiSuccessResponse.of(null);
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    public ConversationResponseDTO toConversationResponseDTO(Conversation conv, User currentUser) {
        User otherUser = conv.getParticipants().stream()
                .filter(p -> !p.getId().equals(currentUser.getId()))
                .findFirst()
                .orElse(currentUser);

        UserPresence presence = userPresenceRepository.findByUser(otherUser).orElse(null);
        long unread = messageRepository.countByConversationAndIsReadFalseAndSenderNot(conv, currentUser);

        return ConversationResponseDTO.builder()
                .id(conv.getId())
                .otherUserId(otherUser.getId())
                .otherUserName(otherUser.getFullName())
                .otherUserPhotoURL(resolvePhotoURL(otherUser))
                .otherUserIsOnline(presence != null && presence.isOnline())
                .otherUserLastSeen(presence != null ? presence.getLastSeenAt() : null)
                .lastMessage(conv.getLastMessage())
                .lastMessageAt(conv.getLastMessageAt())
                .unreadCount((int) unread)
                .build();
    }

    public MessageResponseDTO toMessageResponseDTO(Message msg, Long currentUserId) {
        return MessageResponseDTO.builder()
                .id(msg.getId())
                .senderId(msg.getSender().getId())
                .senderName(msg.getSender().getFullName())
                .senderPhotoURL(resolvePhotoURL(msg.getSender()))
                .content(msg.getContent())
                .sentAt(msg.getSentAt())
                .isRead(msg.isRead())
                .isMine(msg.getSender().getId().equals(currentUserId))
                .build();
    }

    private String resolvePhotoURL(User user) {
        return switch (user.getRole()) {
            case CLEANER -> cleanerRepository.findByUser(user)
                    .map(Cleaner::getProfilePhotoURL)
                    .orElse(null);
            case CUSTOMER -> customerRepository.findByUser(user)
                    .map(Customer::getProfilePhotoURL)
                    .orElse(null);
        };
    }
}
