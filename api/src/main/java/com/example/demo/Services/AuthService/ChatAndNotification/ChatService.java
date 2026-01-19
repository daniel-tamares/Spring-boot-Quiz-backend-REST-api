package com.example.demo.Services.AuthService.ChatAndNotification;

import com.example.demo.DTO.Notification.MessageRequestDTO;
import com.example.demo.DTO.Notification.NotificationRequestDTO;
import com.example.demo.Entity.NotificationMessage;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Map;

@Service
public class ChatService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    public ChatService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService)
    {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    public void send(MessageRequestDTO message)
    {
        if (message == null) return;
        if (message.getSender() == null) return;
        if (message.getContent() == null) return;
        if (message.getReceiver() == null) return;

        messagingTemplate.convertAndSend(
                "/queue/private." + message.getReceiver(),
                message
        );

//        messagingTemplate.convertAndSendToUser(
//                message.getReceiver(),
//                "/queue/private",
//                message
//        );

//        notificationService.sendPrivateNotification(
//                message.getReceiver(),
//                new NotificationRequestDTO("noti...",
//                        "dfasfdas" + message.getSender())
//        );

        messagingTemplate.convertAndSend(
                "/queue/notify-" + message.getReceiver(),
                Map.of(
                        "sender", message.getSender(),
                        "message", message.getContent(),
                        "title", "dasdfadadf",
                        "fasddfasfd", "daniel"
                )
        );

        System.out.print("send...");

    }

}
