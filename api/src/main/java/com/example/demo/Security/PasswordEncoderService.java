package com.example.demo.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderService {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderService()
    {
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    public String encode( String rawPassword )
    {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches( String rawPassword, String ecnodedPassword )
    {
        return passwordEncoder.matches(rawPassword, ecnodedPassword);
    }
}
