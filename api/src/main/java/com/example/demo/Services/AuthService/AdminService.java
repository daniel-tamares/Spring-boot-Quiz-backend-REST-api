package com.example.demo.Services.AuthService;

import com.example.demo.DTO.CourseAndSubjectDTO.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AdminService {

    Map<String, Object> addCourse(CourseRequestDTO requestDTO);
    Map<String, Object> addQuizQuestion(QuestionRequestDTO requestDTO, Long quizId);
    Map<String, Object> addSubject(SubjectRequestDTO requestDTO, Long courseId);
    Map<String, Object> addSubjectQuiz(QuizRequestDTO requestDTO, Long userId, Long subjectId);
    Map<String, Object> addQuestionOption(OptionRequestDTO requestDTO, Long quizId);
    List<QuestionResponseDTO> getQuizQuestions(Long quizId, String questionType);
    QuizSubmitResponseDTO checkAnswer(QuizSubmitDTO requestDTO, Long quizId);
    List<SubjectResponseDTO> getSubjects();
    List<CourseResponseDTO> getCourses();
    List<QuizResponseDTO> getQuizzes();
    void deployQuiz(Long useId, Long quizId, DeployQuizDTO requestDtO);
}
