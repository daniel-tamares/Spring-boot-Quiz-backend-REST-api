package com.example.demo.DTO;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Set;

public class UserResponseDTO {

    private Long id;
    private String username;
    private String password;
    private int year;
    private Set<String> roles;
    private String accessToken;
    private String refreshToken;

    public UserResponseDTO() {}

    public UserResponseDTO( Long id, String username, String password, int year, Set<String> roles, String accessToken, String refreshToken )
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.year = year;
        this.roles = roles;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Long getId() { return  id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getYear() { return year; }
    public Set<String> getRoles() { return roles; }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }

    public void setId( Long id )
    {
        this.id = id;
    }
    public void setUsername( String username )
    {
        this.username = username;
    }
    public void setPassword( String password )
    {
        this.password = password;
    }
    public void setYear( int year )
    {
        this.year = year;
    }
    public void setRoles(Set<String> roles)
    {
        this.roles = roles;
    }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

}
