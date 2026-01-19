package com.example.demo.DTO.CourseAndSubjectDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuizAnswerRequestDTO {

    @NotNull(message = "cannot be null")
    private Long questionId;

    @NotNull(message = "cannot be null")
    private Long optionId;

    public QuizAnswerRequestDTO() {}

    public QuizAnswerRequestDTO(Long questionId, Long optionId)
    {
        this.questionId = questionId;
        this.optionId = optionId;
    }

    public Long getQuestionId()
    {
        return questionId;
    }
    public Long getOptionId()
    {
        return optionId;
    }
    public void setQuestionId(Long questionId)
    {
        this.questionId = questionId;
    }
    public void setOptionId(Long optionId)
    {
        this.optionId = optionId;
    }

}
