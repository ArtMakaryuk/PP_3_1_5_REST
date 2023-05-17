package ru.kata.spring.boot_security.demo.services;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @Transactional
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        bindingResult = checkBindingResultForPasswordField(bindingResult);

        if (!bindingResult.hasErrors()) {
            String oldPassword = user.getPassword();
            try {
                user.setPassword(user.getPassword().isEmpty() ?
                        findById(user.getId()).getPassword() :
                        passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                user.setPassword(oldPassword);
                addErrorIfDataIntegrityViolationException(bindingResult);
                addRedirectAttributesIfErrorsExists(user, bindingResult, redirectAttributes);
            }
        } else {
            addRedirectAttributesIfErrorsExists(user, bindingResult, redirectAttributes);
        }
    }

    @Transactional
    @Override
    public void insertUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            String oldPassword = user.getPassword();
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                user.setPassword(oldPassword);
                addErrorIfDataIntegrityViolationException(bindingResult);
                addRedirectAttributesIfErrorsExists(user, bindingResult, redirectAttributes);
            }
        } else {
            addRedirectAttributesIfErrorsExists(user, bindingResult, redirectAttributes);
        }
    }

    private void addErrorIfDataIntegrityViolationException(BindingResult bindingResult) {
        bindingResult.addError(new FieldError(bindingResult.getObjectName(),
                "email", "E-mail must be unique"));
    }

    private void addRedirectAttributesIfErrorsExists(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("user", user);
        redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
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
