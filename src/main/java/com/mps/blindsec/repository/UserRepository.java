package com.mps.blindsec.repository;

import java.util.Optional;

import com.mps.blindsec.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByName(String name);
    boolean existsByEmail(String email);
}