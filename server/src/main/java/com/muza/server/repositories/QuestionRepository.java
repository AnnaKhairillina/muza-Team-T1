package com.muza.server.repositories;

import com.muza.server.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q JOIN q.tags t WHERE t.name = :tagName")
    Page<Question> findByTagName(@Param("tagName") String tagName, Pageable pageable);
}
