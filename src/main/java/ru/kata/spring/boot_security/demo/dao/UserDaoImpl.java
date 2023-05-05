package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        em.remove(user);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void editUser(User user) {
        em.merge(user);
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("select u from User u left join fetch u.roles where u.username=:username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }
}
