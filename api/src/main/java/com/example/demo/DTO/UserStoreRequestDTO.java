package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserStoreRequestDTO {

    @NotBlank( message = "Username is required...dan")
    @Size( min = 3, max = 255, message = "Passwod must be bwtween 3 - 255 characters...dan")
    private String username;

    @NotBlank( message = "Passowrod is requied")
    @Size( min = 3, max = 512, message = "password is to short or long")
    private String password;

    @NotNull( message = "year is cannot be null")
    private int year;

    public UserStoreRequestDTO() {}

    public UserStoreRequestDTO( String username, String password, int year )
    {
        this.username = username;
        this.password = password;
        this.year = year;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getYear() { return year; }
}
