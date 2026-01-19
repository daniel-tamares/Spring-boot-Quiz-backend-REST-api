package com.example.demo.Entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table( name = "subjects")
public class Subject {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "name", nullable = false, length = 512)
    private String name;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn( name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Quiz> quizzes;

    public Subject() {}

    public Subject(String name)
    {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Course getCourse() { return course; }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setCourse(Course course)
    {
        this.course = course;
    }

}
