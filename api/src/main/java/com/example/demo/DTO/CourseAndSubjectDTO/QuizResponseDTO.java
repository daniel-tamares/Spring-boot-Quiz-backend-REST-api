package com.example.demo.DTO.CourseAndSubjectDTO;

public class QuizResponseDTO {

    private Long id;
    private String title;

    public QuizResponseDTO() {}

    public QuizResponseDTO(Long id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public Long getId()
    {
        return id;
    }
    public String getTitle()
    {
        return title;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    public void setTitle()
    {
        this.title = title;
    }

}
