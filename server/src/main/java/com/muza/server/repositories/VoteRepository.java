package com.muza.server.repositories;

import com.muza.server.entities.Vote;
import com.muza.server.entities.User;
import com.muza.server.entities.Question;
import com.muza.server.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUserAndQuestion(User user, Question question);
    Optional<Vote> findByUserAndAnswer(User user, Answer answer);
    @Query("SELECT COALESCE(SUM(v.value), 0) FROM Vote v WHERE v.question = :question")
    Integer sumVotesByQuestion(@Param("question") Question question);

    @Query("SELECT COALESCE(SUM(v.value), 0) FROM Vote v WHERE v.answer = :answer")
    Integer sumVotesByAnswer(@Param("answer") Answer answer);

}
