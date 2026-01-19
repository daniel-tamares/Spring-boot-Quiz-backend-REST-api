package com.example.demo.Repositories;

import com.example.demo.Entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    boolean existsByOptionText(String option);
    Optional<Option> findByIdAndQuestionId(Long optionId, Long questionId);
    boolean existsByIdAndQuestionId(Long optionId, Long questionId);

    @Query("SELECT o FROM Option o JOIN o.question q WHERE q.id IN :questionIds AND o.isCorrect = true")
    List<Option> findCorrectOptionsByQuestionIds(List<Long> questionIds);

    @Query("SELECT o FROM Option o WHERE o.id IN :ids")
    List<Option> findByOptionIds(@Param("ids") List<Long> ids);

}
