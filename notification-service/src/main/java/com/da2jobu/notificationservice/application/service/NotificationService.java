package com.da2jobu.notificationservice.application.service;

import com.da2jobu.notificationservice.interfaces.dto.MessageSendRequest;
import com.da2jobu.notificationservice.interfaces.dto.MessageSendResponse;
import com.da2jobu.notificationservice.interfaces.dto.SlackMessageCursorResponse;

import java.time.LocalDateTime;

public interface NotificationService {
    MessageSendResponse sendMessage(MessageSendRequest request);
    SlackMessageCursorResponse getMessages(LocalDateTime cursor, int size);
}
