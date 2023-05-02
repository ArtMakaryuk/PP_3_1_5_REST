package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    void saveUser(User user);

    void deleteById(Long id);

    User findById(Long id);

    void editUser(User user);

    User findByUsername(String username);
}
