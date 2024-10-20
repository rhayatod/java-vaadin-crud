package com.hayato.vaadin.backend.service;

import com.hayato.vaadin.backend.model.entity.User;
import com.hayato.vaadin.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewUserService {
    private final UserRepository userRepository;

    public NewUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Boolean findByUsernameOrEmail(String username, String email) {
        return !userRepository.findByUsernameOrEmail(username, email).isEmpty();
    }

}
