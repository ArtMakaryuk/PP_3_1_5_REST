package ru.kata.spring.boot_security.demo.services;


import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    User updateUser(User user, BindingResult bindingResult);

    User insertUser(User user, BindingResult bindingResult);
}
