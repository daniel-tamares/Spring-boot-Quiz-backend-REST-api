package com.example.demo.DTO.Notification;

import jakarta.validation.constraints.NotBlank;

public class MessageRequestDTO {
    @NotBlank(message = "dsafdf")
    private String sender;
    @NotBlank(message = "dsafdf")
    private String receiver;
    @NotBlank(message = "dsafdf")// null for broadcast
    private String content;

    public MessageRequestDTO() {}

    public MessageRequestDTO(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    // getters & setters
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
