package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> findAll() {
        return em.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public Role findRoleByAuthority(String authority) throws NoSuchElementException {
        return findAll().stream()
                .filter(r -> authority.equals(r.getAuthority()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Role %s not found", authority)));
    }

    @Override
    public Role getById(Long id) {
        return em.find(Role.class, id);
    }
}

