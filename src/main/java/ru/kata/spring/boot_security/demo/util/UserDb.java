//package ru.kata.spring.boot_security.demo.util;
//
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.data.annotation.Persistent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import ru.kata.spring.boot_security.demo.dao.RoleDao;
//import ru.kata.spring.boot_security.demo.dao.UserDao;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//
//import java.util.HashSet;
//
//@Component
//public class UserDb {
//
//    private final RoleDao roleDao;
//    private final UserDao userDao;
//    private final PasswordEncoder passwordEncoder;
//
//
//    public UserDb(RoleDao roleDao, UserDao userDao, PasswordEncoder passwordEncoder) {
//        this.roleDao = roleDao;
//        this.userDao = userDao;
//        this.passwordEncoder = passwordEncoder;
//    }
//    @Transactional
//    @EventListener(ApplicationReadyEvent.class)
//    public void create() {
//        Role admin = new Role("ROLE_ADMIN");
//        Role user = new Role("ROLE_USER");
//        roleDao.saveRole(admin);
//        roleDao.saveRole(user);
//
//        userDao.saveUser(new User("Василий", "Уткин", (byte) 49, "admin@mail.com",
//                passwordEncoder.encode("admin"),
//                new HashSet<>() {{
//                    add(admin);
//                    add(user);
//                }}));
//        userDao.saveUser(new User("Дмитрий", "Губерниев", (byte) 46, "user@mail.com",
//                passwordEncoder.encode("user"),
//                new HashSet<>() {{
//                    add(user);
//                }}));
//
//    }
//}
