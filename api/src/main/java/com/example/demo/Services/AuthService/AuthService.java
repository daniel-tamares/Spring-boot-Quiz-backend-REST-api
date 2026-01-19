package com.example.demo.Services.AuthService;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.RefreshTokenRequestDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.DTO.UserStoreRequestDTO;
import com.example.demo.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.http.HttpClient;

public interface AuthService {

    UserResponseDTO register(UserStoreRequestDTO requestDTO, String ip);
    UserResponseDTO login(LoginRequestDTO requestDTO, String ip, HttpServletResponse response);
    UserResponseDTO refreshToken(String refreshToken, String ip, HttpServletResponse response);
    String getClientIp(HttpServletRequest request);
    void saveRefreshToken(User user, String refreshToken, String ipAddress, String familyId);
    void deleteToken(String rt);
    String logout(String refreshToken, HttpServletResponse response);

}
