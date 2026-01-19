package com.example.demo.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username")})
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @Column( name = "password", nullable = false, length = 255)
    private String password;

    @Column( name = "year", nullable = false)
    private int year;

    @Column( name = "last_login_ip", nullable = false, length = 255)
    private String lastLoginIp;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column( name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column( name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ElementCollection( fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn( name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "role" })
    )
    private Set<String> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RefreshToken> refreshTokens = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Quiz> quizzes;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn( name = "course_id", nullable = true)
    private Course course;

    public User()
    {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.roles.add("USER");
    }

    public User( String username, String password, int year, String lastLoginIp )
    {
        this();
        this.username = username;
        this.password = password;
        this.year = year;
        this.lastLoginIp = lastLoginIp;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getYear() { return year; }
    public String getLastLoginIp() { return lastLoginIp; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Set<RefreshToken> getRefreshTokens() { return refreshTokens; }
    public Course getCourse() { return course; }
    public Set<String> getRoles() { return roles; }
    public boolean getIsActive() { return isActive; }
    public Set<Quiz> getQuizzes() { return quizzes; }

    public void setId( Long id )
    {
        this.id = id;
    }
    public void setUsername( String username )
    {
        this.username = username;
    }
    public void setYear( int year )
    {
        this.year = year;
    }
    public void setLastLoginIp( String lastLoginIp )
    {
        this.lastLoginIp = lastLoginIp;
    }
    public void setUpdatedAt( LocalDateTime updatedAt )
    {
        this.updatedAt = updatedAt;
    }
    public void setRoles( Set<String> roles )
    {
        this.roles = roles;
    }
    public void setCourse(Course course) { this.course = course; }
    public void setQuizzes(Set<Quiz> quizzes) { this.quizzes = quizzes; }
    public void addRole( String role )
    {
        if ( this.roles == null )
        {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }
    public void addRefreshToken( RefreshToken refreshTokens )
    {
        if ( this.refreshTokens == null )
        {
            this.refreshTokens = new HashSet<>();
        }
        this.refreshTokens.add(refreshTokens);
        refreshTokens.setUser(this);
    }
    public void setRefreshTokens( Set<RefreshToken> refreshTokens )
    {
        this.refreshTokens = refreshTokens;
    }
    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }

}
