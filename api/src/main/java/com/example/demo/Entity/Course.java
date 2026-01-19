package com.example.demo.Entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "courses")
public class Course {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "name", nullable = false, length = 255)
    private String name;

    @OneToMany( mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subject> subjects = new HashSet<>();

    public Course() {}

    public Course(String name)
    {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return  name; }
    public Set<Subject> getSubjects() { return subjects; }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSubjects(Set<Subject> subjects)
    {
        this.subjects = subjects;
    }
    public void addSubject(Subject subject)
    {
        this.subjects.add(subject);
        subject.setCourse(this);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
        subject.setCourse(null);
    }

}
