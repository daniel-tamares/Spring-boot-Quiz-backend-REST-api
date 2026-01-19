package com.example.demo.DTO.CourseAndSubjectDTO;

import java.util.List;

public class QuestionResponseDTO {

    private Long id;
    private String questionText;
    private List<OptionResponseDTO> options;

    public QuestionResponseDTO() {}

    public QuestionResponseDTO(Long id, String questionText, List<OptionResponseDTO> options)
    {
        this.id =id;
        this.questionText = questionText;
        this.options = options;
    }

    public Long getId() { return id; }
    public String getTitle() { return questionText; }
    public List<OptionResponseDTO> getOptions() { return options; }

}
