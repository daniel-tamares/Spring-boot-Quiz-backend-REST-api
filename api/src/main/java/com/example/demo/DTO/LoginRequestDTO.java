package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is requiered")
    private String password;

    public LoginRequestDTO() {}

    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }

}
