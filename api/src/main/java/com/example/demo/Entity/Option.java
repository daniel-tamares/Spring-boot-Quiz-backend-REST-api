package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "option_text", nullable = false)
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect = false;

    public Option() {}

    public Option(Question question, String optionText, boolean isCorrect)
    {
        this.question = question;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion()
    {
        return question;
    }
    public String getOptionText()
    {
        return optionText;
    }
    public boolean getIsCorrect()
    {
        return isCorrect;
    }

    public void setQuestion(Question question)
    {
        this.question = question;
    }
    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }
    public void setCorrect(boolean isCorrect)
    {
        this.isCorrect = isCorrect;
    }

}
