package com.dudka.store.service;

import com.dudka.store.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findUserById(Long id);
    Optional<User> findByEmail(String email);

    User findByUsername(String username);
    Optional<User> findByPhone(String phone);

    void saveUser(User user);

}
