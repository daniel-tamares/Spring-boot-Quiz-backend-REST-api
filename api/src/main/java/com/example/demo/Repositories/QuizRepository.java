package com.example.demo.Repositories;

import com.example.demo.DTO.CourseAndSubjectDTO.QuizResponseDTO;
import com.example.demo.Entity.Option;
import com.example.demo.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    boolean existsByTitle(String title);

    @Query("SELECT new com.example.demo.DTO.CourseAndSubjectDTO.QuizResponseDTO(q.id, q.title) FROM Quiz q WHERE q.subject.id = :subjectId AND isActive = false")
    List<QuizResponseDTO> getAllUserQuizzes(@Param("subjectId") Long subjectId);

    @Modifying
    @Query("UPDATE Quiz q SET q.isActive = true, q.year = :year WHERE q.id = :quizId")
    void deployQuiz(@Param("quizId") Long quizId, @Param("year") int year);

//    @Modifying
//    @Query("UPDATE RefreshToken rt SET rt.revoked = true WHERE rt.user.id = :userId")
//    int revokeAllUserTokens(@Param("userId") Long userId);


}
