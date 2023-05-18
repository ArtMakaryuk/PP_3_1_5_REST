package ru.kata.spring.boot_security.demo.services;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.kata.spring.boot_security.demo.exception.UserDataIntegrityViolationException;
import ru.kata.spring.boot_security.demo.exception.UserValidationException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public User updateUser(User user, BindingResult bindingResult) {
        bindingResult = checkBindingResultForPasswordField(bindingResult);

        if (bindingResult.hasErrors()) {
            throw new UserValidationException(user, bindingResult.getFieldErrors(), "User's fields has errors");
        }

        String oldPassword = user.getPassword();
        user.setPassword(user.getPassword().isEmpty() ?
                findById(user.getId()).getPassword() :
                passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            user.setPassword(oldPassword);
            throw new UserDataIntegrityViolationException(user, e.getMessage());
        }

        return user;
    }

    @Override
    public User insertUser(User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserValidationException(user, bindingResult.getFieldErrors(), "User's fields has errors");
        }

        String oldPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            user.setPassword(oldPassword);
            throw new UserDataIntegrityViolationException(user, e.getMessage());
        }

        return user;
    }

    private BindingResult checkBindingResultForPasswordField(BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            return bindingResult;
        }

        User user = (User) bindingResult.getTarget();
        BindingResult newBindingResult = new BeanPropertyBindingResult(user, bindingResult.getObjectName());
        for (FieldError error : bindingResult.getFieldErrors()) {
            if (!user.isNew() && !error.getField().equals("password")) {
                newBindingResult.addError(error);
            }
        }

        return newBindingResult;
    }
}
