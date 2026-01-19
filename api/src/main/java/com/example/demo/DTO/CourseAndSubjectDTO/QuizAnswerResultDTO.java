package com.example.demo.DTO.CourseAndSubjectDTO;

public class QuizAnswerResultDTO {

    private String question;
    private String option;
    boolean isCorrect;

    public QuizAnswerResultDTO(){}

    public QuizAnswerResultDTO(String question, String option, boolean isCorrect)
    {
        this.question = question;
        this.option = option;
        this.isCorrect = isCorrect;
    }

    public String getQuestion() { return question; }
    public String getOption() { return option; }
    public boolean getIsCorrect() { return isCorrect; }

    public void setQuestion(String question)
    {
        this.question = question;
    }
    public void setOption(String option)
    {
        this.option = option;
    }
    public void setCorrect(boolean isCorrect)
    {
        this.isCorrect = isCorrect;
    }

//    private Long questionId;
//    private Long optionId;
//    private boolean isCorrect;
//
//    // ✅ Default constructor
//    public QuizAnswerResultDTO() {}
//
//    // ✅ All-args constructor
//    public QuizAnswerResultDTO(Long questionId, Long optionId, boolean isCorrect) {
//        this.questionId = questionId;
//        this.optionId = optionId;
//        this.isCorrect = isCorrect;
//    }
//
//    // ✅ Getters & Setters
//    public Long getQuestionId() { return questionId; }
//    public void setQuestionId(Long questionId) { this.questionId = questionId; }
//
//    public Long getOptionId() { return optionId; }
//    public void setOptionId(Long optionId) { this.optionId = optionId; }
//
//    public boolean isCorrect() { return isCorrect; }
//    public void setCorrect(boolean correct) { isCorrect = correct; }

}
