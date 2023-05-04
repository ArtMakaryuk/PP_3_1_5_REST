package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> findAll() {
        return em.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public void saveRole(Role role) {
        em.persist(role);
    }

    @Override
    public void deleteById(Long id) {
        Role role = findById(id);
        em.remove(role);
    }

    @Override
    public Role findById(Long id) {
        TypedQuery<Role> query = em.createQuery("select r from Role r where r.id = :id", Role.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void editRole(Role role) {
        em.merge(role);
    }

}
