package com.example.demo.Services.AuthService;

import com.example.demo.DTO.CourseAndSubjectDTO.CourseRequestDTO;
import com.example.demo.DTO.CourseAndSubjectDTO.QuizResponseDTO;
import com.example.demo.DTO.CourseAndSubjectDTO.UserSubCouResDTO;
import com.example.demo.Entity.Quiz;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> addUserCourse(CourseRequestDTO requestDTO, Long id);
    List<QuizResponseDTO> getAllUserQuizzes(Long userId, Long subjectId);
}
