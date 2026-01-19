package com.example.demo.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "quiz_type", nullable = false)
    private String quizType;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "quiz_key", nullable = false)
    private String quizKey;

    public Quiz() {
        this.createdAt = LocalDateTime.now();
    }

    public Quiz(String title, String quizType, boolean isActive, int year)
    {
        this.title = title;
        this.quizType = quizType;
        this.isActive = isActive;
        this.year = year;
    }

    public Long getId()
    {
        return id;
    }
    public String getTitle()
    {
        return title;
    }
    public String getQuizType()
    {
        return quizType;
    }
    public List<Question> getQuestions()
    {
        return questions;
    }
    public User getUser()
    {
        return user;
    }
    public Subject getSubject() { return  subject; }
    public boolean getIsActive() { return isActive; }
    public int getYear() { return year; }
    public Long getCourseId() { return courseId; }
    public String getQuizKey() { return quizKey; }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }
    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }
    public void setQuizType(String quizType)
    {
        this.quizType = quizType;
    }
    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }
    public void setYear(int year)
    {
        this.year = year;
    }
    public void setCourseId(Long courseId)
    {
        this.courseId = courseId;
    }
    public void setQuizKey(String quizKey) { this.quizKey = quizKey; }

}
