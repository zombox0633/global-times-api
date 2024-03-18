package com.springframework.globaltimes.repository.user;

import com.springframework.globaltimes.entity.user.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAll();

    Optional<User> findByEmail(String email);
}
