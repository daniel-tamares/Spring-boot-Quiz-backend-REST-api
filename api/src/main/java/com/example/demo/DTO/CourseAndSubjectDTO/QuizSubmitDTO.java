package com.example.demo.DTO.CourseAndSubjectDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class QuizSubmitDTO {

    @NotEmpty(message = "answers cannot be null")
    private List<QuizAnswerRequestDTO> answers;

    public QuizSubmitDTO() {}

    public QuizSubmitDTO(List<QuizAnswerRequestDTO> answers)
    {
        this.answers = answers;
    }

    public List<QuizAnswerRequestDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuizAnswerRequestDTO> answers) {
        this.answers = answers;
    }

}
