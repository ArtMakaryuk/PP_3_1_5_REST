package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {

    Iterable<Role> findAllRoles();

    Role getById(Integer id);

}
