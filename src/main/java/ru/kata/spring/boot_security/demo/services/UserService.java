package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();

    void saveUser(User user, Long[] roles);

    void deleteById(Long id);

    void editUser(User user, Long[] roles);


}
