package com.example.demo.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table( name = "refresh_token", uniqueConstraints = @UniqueConstraint(columnNames = "token"))
public class RefreshToken {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "token", nullable = false, unique = true, length = 512)
    private String token;

    @Column(name = "family_id", nullable = false)
    private String familyId;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id", nullable = false)
    private User user;

    @Column( name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column( name = "is_revoked", nullable = false)
    private boolean revoked = false;

    @Column( name = "ip_address", length = 45)
    private String ipAddress;

    @Column( name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public RefreshToken()
    {
        this.createdAt = LocalDateTime.now();
    }

    public RefreshToken( String token, String familyId, User user, LocalDateTime expiresAt, String ipAddress )
    {
        this();
        this.token = token;
        this.familyId = familyId;
        this.user = user;
        this.expiresAt = expiresAt;
        this.ipAddress = ipAddress;
    }

    public void setUser( User user )
    {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public Long getId() { return id; }
    public String getToken() { return token; }
    public String getFamilyId() { return familyId; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public String getIpAddress() { return ipAddress; }

    public void setId(Long id)
    {
        this.id = id;
    }
    public void setToken(String token)
    {
        this.token = token;
    }
    public void setFamilyId(String familyId)
    {
        this.familyId = familyId;
    }
    public void setExpiresAt(LocalDateTime expiresAt)
    {
        this.expiresAt = expiresAt;
    }
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

}
