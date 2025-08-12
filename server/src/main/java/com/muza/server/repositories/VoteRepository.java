package com.muza.server.repositories;

import com.muza.server.entities.Post;
import com.muza.server.entities.User;
import com.muza.server.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserIdAndPostId(Integer userId, Integer postId);

    @Query("SELECT COALESCE(SUM(v.voteTypeId), 0) FROM Vote v " +
            "WHERE v.postId = :postId AND v.voteTypeId IN (2, 3)")
    Integer sumVotesByPost(@Param("postId") Integer postId);

    @Query("SELECT COUNT(v) FROM Vote v " +
            "WHERE v.postId = :postId AND v.voteTypeId = :voteTypeId")
    Integer countVotesByType(@Param("postId") Integer postId, @Param("voteTypeId") Short voteTypeId);
}