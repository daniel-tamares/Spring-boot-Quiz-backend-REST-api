package com.example.demo.DTO.CourseAndSubjectDTO;

import jakarta.validation.constraints.NotBlank;

public class QuestionRequestDTO {

    @NotBlank(message = "question is required..")
    private String question;

    public QuestionRequestDTO() {}

    public String getQuestion()
    {
        return question;
    }
    public void setQuestion(String question)
    {
        this.question = question;
    }

}
