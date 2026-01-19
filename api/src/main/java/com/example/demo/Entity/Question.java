package com.example.demo.Entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    public Question() {}

    public Question(String title, String type)
    {
        this.title = title;
        this.type = type;
    }

    public Long getId()
    {
        return id;
    }
    public String getTitle()
    {
        return title;
    }
    public Quiz getQuiz()
    {
        return quiz;
    }
    public List<Option> getOptions()
    {
        return options;
    }
    public String getType()
    {
        return type;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setQuiz(Quiz quiz)
    {
        this.quiz = quiz;
    }
    public void setOptions(List<Option> options)
    {
        this.options = options;
    }
    public void setType(String type)
    {
        this.type = type;
    }

}
