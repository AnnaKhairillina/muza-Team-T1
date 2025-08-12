package com.muza.server.repositories;

import com.muza.server.entities.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {

    List<Badge> findByUserId(Integer userId);

    List<Badge> findByName(String name);
}