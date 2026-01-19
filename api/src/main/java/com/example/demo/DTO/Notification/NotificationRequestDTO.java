package com.example.demo.DTO.Notification;

import jakarta.validation.constraints.NotBlank;

public class NotificationRequestDTO {

    @NotBlank(message = "title is requeid..")
    private String title;

    @NotBlank(message = "mesage if required...")
    private String message;

    public NotificationRequestDTO() {}

    public NotificationRequestDTO(String title, String message)
    {
        this.title = title;
        this.message = message;
    }

    public String getTitle()
    {
        return title;
    }
    public String getMessage()
    {
        return message;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }

}

