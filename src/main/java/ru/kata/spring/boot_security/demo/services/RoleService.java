package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    void saveRole(Role role);

    void deleteById(Long id);

    Role findById(Long id);

    void editRole(Role role);
}
