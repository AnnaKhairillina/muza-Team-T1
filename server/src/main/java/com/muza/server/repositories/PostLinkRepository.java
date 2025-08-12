package com.muza.server.repositories;

import com.muza.server.entities.PostLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLinkRepository extends JpaRepository<PostLink, Integer> {

    List<PostLink> findByPostId(Integer postId);

    List<PostLink> findByLinkTypeId(Short linkTypeId);
}