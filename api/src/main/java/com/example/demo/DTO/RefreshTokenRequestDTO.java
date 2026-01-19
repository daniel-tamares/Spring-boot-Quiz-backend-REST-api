package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequestDTO {

    @NotBlank(message = "token is required...")
    private String refreshToken;

    RefreshTokenRequestDTO() {}

    public String getRefreshToken()
    {
        return refreshToken;
    }

}
