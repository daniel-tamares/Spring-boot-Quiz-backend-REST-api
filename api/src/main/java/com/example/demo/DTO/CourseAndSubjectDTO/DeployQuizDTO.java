package com.example.demo.DTO.CourseAndSubjectDTO;

import jakarta.validation.constraints.NotNull;

public class DeployQuizDTO {

    @NotNull
    private int year;

    public DeployQuizDTO() {}

    public DeployQuizDTO(int year)
    {
        this.year = year;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

}
