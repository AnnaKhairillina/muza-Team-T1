package com.muza.server.repositories;

import com.muza.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByDisplayName(String displayName);

    Optional<User> findByEmailHash(String emailHash);

    boolean existsByEmailHash(String emailHash);
}