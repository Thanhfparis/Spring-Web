package com.example.springweb.repository;

import com.example.springweb.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);

    List<User> findAllByRole(String role);
}
