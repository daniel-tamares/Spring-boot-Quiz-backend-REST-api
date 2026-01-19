package com.example.demo.Repositories;

import com.example.demo.Entity.Question;
import com.example.demo.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    boolean existsByTitle(String title);

    @Query("SELECT DISTINCT q FROM Question q JOIN FETCH q.options WHERE q.quiz.id = :quizId")
    List<Question> getQuizQuestions(Long quizId);

//    @Query("SELECT DISTINCT q FROM Question q JOIN FETCH q.options WHERE q.quiz.id = :quizId AND q.type = :questionType")
//    List<Question> getQuizQuestionsType(Long quizId, String questionType);

//    @Query("SELECT DISTINCT q FROM Question q WHERE q.quiz.id = :quizId AND q.type = :questionType")
//    List<Question> getQuizQuestionsOnly(Long quizId, String questionType);

    @Query("""
    SELECT DISTINCT q
    FROM Question q
    LEFT JOIN FETCH q.options
    WHERE q.quiz.id = :quizId
      AND q.type = :questionType
""")
    List<Question> getQuizQuestionsType(Long quizId, String questionType);

    @Query("SELECT q FROM Question q WHERE q.id IN :ids")
    List<Question> findByQuestionByIds(@Param("ids") List<Long> ids);
}
