package com.example.demo.DTO.CourseAndSubjectDTO;

import java.util.List;

public class QuizSubmitResponseDTO {

    private int totalQuestions;
    private int totalCorrectAnswer;
    private List<QuizAnswerResultDTO> result;

    public QuizSubmitResponseDTO() {}

    public QuizSubmitResponseDTO(int totalQuestions, int totalCorrectAnswer, List<QuizAnswerResultDTO> result)
    {
        this.totalQuestions = totalQuestions;
        this.totalCorrectAnswer = totalCorrectAnswer;
        this.result = result;
    }

    public int getTotalQuestions()
    {
        return totalQuestions;
    }
    public int getTotalCorrectAnswer()
    {
        return totalCorrectAnswer;
    }
    public List<QuizAnswerResultDTO> getResult()
    {
        return result;
    }

    public void setTotalQuestions(int totalQuestions)
    {
        this.totalQuestions = totalQuestions;
    }
    public void setTotalCorrectAnswer(int totalCorrectAnswer)
    {
        this.totalCorrectAnswer = totalCorrectAnswer;
    }
    public void setResult(List<QuizAnswerResultDTO> result)
    {
        this.result = result;
    }

}
