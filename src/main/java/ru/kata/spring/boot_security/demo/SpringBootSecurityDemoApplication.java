package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;


@SpringBootApplication
public class SpringBootSecurityDemoApplication implements CommandLineRunner{

    private final RoleDao roleDao;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public SpringBootSecurityDemoApplication(RoleDao roleDao, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
            Role admin = new Role("ROLE_ADMIN");
            Role user = new Role("ROLE_USER");
            roleDao.saveRole(admin);
            roleDao.saveRole(user);

            userDao.saveUser(new User("Василий", "Уткин", (byte) 49, "admin@mail.com",
                    passwordEncoder.encode("admin"),
                    new HashSet<>() {{
                        add(admin);
                        add(user);
                    }}));
            userDao.saveUser(new User("Дмитрий", "Губерниев", (byte) 46, "user@mail.com",
                    passwordEncoder.encode("user"),
                    new HashSet<>() {{
                        add(user);
                    }}));

    }
}
