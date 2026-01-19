package com.example.demo.Controller;

import com.example.demo.DTO.Notification.MessageRequestDTO;
import com.example.demo.Services.AuthService.ChatAndNotification.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService)
    {
        this.chatService = chatService;
    }

    @MessageMapping("/chat-send")
    public void sendMessage(MessageRequestDTO messageRequestDTO)
    {
        chatService.send(messageRequestDTO);
    }
}
