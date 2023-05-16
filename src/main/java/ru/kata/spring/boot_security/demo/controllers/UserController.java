package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

//    @GetMapping()
//    public String indexPage() {
//        return "index";
//    }
    @GetMapping()
    public String showUserInfo(@CurrentSecurityContext(expression = "authentication.principal") User principal,
                               Model model) {
        model.addAttribute("user", principal);
        return "fragments/user-info";
    }
}
