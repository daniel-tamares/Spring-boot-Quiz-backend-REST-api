package com.example.demo.DTO.CourseAndSubjectDTO;

import com.example.demo.Entity.Option;

public class OptionResponseDTO {

    private Long id;
    private String option;
    private boolean isCorrect;

    public OptionResponseDTO() {}

    public OptionResponseDTO(Long id, String option, boolean isCorrect)
    {
        this.id = id;
        this.option = option;
        this.isCorrect = isCorrect;
    }

    public Long getId()
    {
        return id;
    }
    public String getOption()
    {
        return option;
    }
    public boolean getIsCorrect()
    {
        return isCorrect;
    }

}
