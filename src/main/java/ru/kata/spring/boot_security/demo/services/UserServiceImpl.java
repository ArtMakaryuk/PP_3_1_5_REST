package ru.kata.spring.boot_security.demo.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserDao userDao;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleService roleService, UserDao userDao, PasswordEncoder passwordEncoder,
                           UserRepository userRepository) {
        this.roleService = roleService;
        this.userDao = userDao;
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


//    @Transactional
//    @Override
//    public void saveUser(User user, Integer[] roles) {
//        addRole(user, roles);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userDao.saveUser(user);
//    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    //    @Transactional
//    @Override
//    public void editUser(User user, Integer[] roles) {
//        addRole(user, roles);
//        if (!user.getPassword().equals(findById(user.getId()).getPassword())) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        userDao.editUser(user);
//    }
    @Transactional
    @Override
    public void edit(User user) {
        user.setPassword(user.getPassword().isEmpty() ?
                findById(user.getId()).getPassword() :
                passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void insertUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


//    private Set<Role> addRole(User user, Integer[] roles) {
//        Set<Role> set = new HashSet<>();
//        for (Integer role : roles) {
//            set.add(roleService.getById(role));
//        }
//        user.setRoles(set);
//        return set;
//    }

}
