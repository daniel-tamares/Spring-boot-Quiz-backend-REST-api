package com.example.demo.DTO.CourseAndSubjectDTO;

public class SubjectResponseDTO {

    private Long id;
    private Long courseId;
    private String name;

    public SubjectResponseDTO() {}

    public SubjectResponseDTO(Long id, Long courseId, String name)
    {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }
    public Long getCourseId()
    {
        return courseId;
    }
    public String getName()
    {
        return name;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    public void setCourseId(Long courseId)
    {
        this.courseId = courseId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

}
