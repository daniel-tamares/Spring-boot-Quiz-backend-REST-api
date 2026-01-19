package com.example.demo.DTO.CourseAndSubjectDTO;

import jakarta.validation.constraints.NotBlank;

public class CourseRequestDTO {

    @NotBlank(message = "course field is required")
    private String course;

    public CourseRequestDTO() {}

    public String getCourse()
    {
        return course;
    }
}
