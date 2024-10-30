package com.eisenguide2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eisenguide2.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    List<User> findAll();
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}

