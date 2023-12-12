package com.example.todoapp.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.todoapp.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count (u) > 0 from User u where u.username = ?1")
    boolean existsUserByUsername(String username);

    @Query("from User u where u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("select count(u) from User u where u.status='ACTIVE'")
    long usersCount();

}