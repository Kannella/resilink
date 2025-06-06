package com.reslink.demo.repository;


import com.reslink.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Método para buscar um usuário pelo email
    Optional<User> findByEmail(String email);
}