package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;



@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/users")
    public String displayAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles = roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "new";
    }

    @PostMapping("/users/save")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "roless") Long[] roles)  {
        userService.saveUser(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        List<Role> roles = roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "edit";
    }

    @PostMapping("/users/update/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "roless") Long[] roles) {
        userService.editUser(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

}

