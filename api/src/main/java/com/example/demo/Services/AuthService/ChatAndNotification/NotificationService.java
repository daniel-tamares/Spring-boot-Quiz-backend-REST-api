package com.example.demo.Services.AuthService.ChatAndNotification;

import com.example.demo.DTO.Notification.NotificationRequestDTO;
import com.example.demo.Entity.NotificationMessage;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate)
    {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendPrivateNotification(String username, NotificationRequestDTO notificationMessage)
    {
        messagingTemplate.convertAndSend(
                "/topic/notifications." + username,
                notificationMessage
        );
    }

}
