package com.example.travel.repository;

import com.example.travel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndActiveAndDeleted(String email, Boolean active, Boolean deleted);
    List<User> findAllByDeleted(Boolean deleted);
    List<User> findAllByActive(Boolean active);
    List<User> findAllByNameContainingIgnoringCase(String name);
    List<User> findAllBySurnameContainingIgnoringCase(String surname);
    List<User> findAllByDateCreatedAfter(LocalDateTime after);
    List<User> findAllByDateCreatedBefore(LocalDateTime before);
    List<User> findAllByRoleId(Long id);
}