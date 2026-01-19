package com.example.demo.DTO.CourseAndSubjectDTO;

import jakarta.validation.constraints.NotBlank;

public class SubjectRequestDTO {

    @NotBlank(message = "subject is required..")
    private String subject;

    public SubjectRequestDTO() {}

    public String getSubject()
    {
        return subject;
    }
}
