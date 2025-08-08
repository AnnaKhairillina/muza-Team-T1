// QuestionRepository.java
package com.muza.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muza.server.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}