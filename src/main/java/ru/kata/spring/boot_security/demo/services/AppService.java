package ru.kata.spring.boot_security.demo.services;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface AppService {

    String getPage(Model model, HttpSession session, @Nullable Authentication auth);

}
