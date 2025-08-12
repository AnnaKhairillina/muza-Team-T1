package com.muza.server.repositories;

import com.muza.server.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByTagName(String tagName);

    @Query("SELECT t FROM Tag t ORDER BY t.count DESC")
    List<Tag> findPopularTags(Pageable pageable);

    List<Tag> findByTagNameContainingIgnoreCase(String query);
}