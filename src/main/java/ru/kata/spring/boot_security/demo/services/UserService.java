package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();

//    void saveUser(User user, Integer[] roles);

    void deleteById(Long id);

//    void editUser(User user, Integer[] roles);

    void edit(User user);

    void insertUser(User user);
}
