package com.example.demo.DTO.CourseAndSubjectDTO;

import jakarta.validation.constraints.NotBlank;

public class OptionRequestDTO {

    @NotBlank(message = "option is required")
    private String option;

    @NotBlank(message = "true or false is required")
    private String isCorrect;

    public OptionRequestDTO() {}

    public String getOption()
    {
        return option;
    }
    public String getIsCorrect()
    {
        return isCorrect;
    }
    public void setOption(String option)
    {
        this.option = option;
    }
    public void setIsCorrect(String isCorrect)
    {
        this.isCorrect = isCorrect;
    }

}
