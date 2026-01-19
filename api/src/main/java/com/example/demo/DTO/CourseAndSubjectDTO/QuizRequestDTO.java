package com.example.demo.DTO.CourseAndSubjectDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class QuizRequestDTO {

    @NotBlank(message = "title is required")
    private String title;

    public QuizRequestDTO() {}

    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

}
