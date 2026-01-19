package com.example.demo.Controller;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.RefreshTokenRequestDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.DTO.UserStoreRequestDTO;
import com.example.demo.Security.CustomUserDetails;
import com.example.demo.Services.Auth;
import com.example.demo.Services.AuthService.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserStoreRequestDTO requestDTO, HttpServletRequest request)
    {
        String ipAddress = authService.getClientIp(request);
        UserResponseDTO response = authService.register(requestDTO, ipAddress);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO, HttpServletRequest request, HttpServletResponse httpServletResponse)
    {
        String ipAddress = authService.getClientIp(request);
        UserResponseDTO response = authService.login(requestDTO, ipAddress, httpServletResponse);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(Authentication authentication, HttpServletResponse request)
    {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Map<String, Object> res = new HashMap<>();
        res.put("username", userDetails.getUsername());
        res.put("roles", userDetails.getAuthorities());

        return ResponseEntity.ok(res);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserResponseDTO> refresh(HttpServletRequest request, @CookieValue("refreshToken") String refreshToken, HttpServletResponse response)
    {
        String ipAddress = authService.getClientIp(request);

        UserResponseDTO res = authService.refreshToken(refreshToken, ipAddress, response);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response, @Valid @CookieValue("refreshToken") String refreshToken)
    {
        String res = authService.logout(refreshToken, response);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/rt")
    public String rt(@CookieValue("refreshToken") String rt)
    {
        return rt;
    }

}
