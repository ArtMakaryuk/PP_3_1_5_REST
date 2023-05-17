package ru.kata.spring.boot_security.demo.services;


import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    void updateUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    void insertUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes);
}
