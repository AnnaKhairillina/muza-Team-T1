package com.muza.server.repositories;

import com.muza.server.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM posts p WHERE p.post_type_id = 1 AND " +
            "(to_tsvector('english', p.title) @@ to_tsquery('english', :query) OR " +
            "to_tsvector('english', p.body) @@ to_tsquery('english', :query))",
            nativeQuery = true)
    Page<Post> fullTextSearch(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT * FROM posts p WHERE p.post_type_id = 1 AND p.tags LIKE %:tag%",
            nativeQuery = true)
    Page<Post> findQuestionsByTag(@Param("tag") String tag, Pageable pageable);

    Page<Post> findByPostTypeIdOrderByCreationDateDesc(Short postTypeId, Pageable pageable);

    List<Post> findByParentIdAndPostTypeId(Integer parentId, Short postTypeId);
}