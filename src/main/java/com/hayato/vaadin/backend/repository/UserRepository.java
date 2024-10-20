package com.hayato.vaadin.backend.repository;

import com.hayato.vaadin.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsernameOrEmail(String username, String email);
}
