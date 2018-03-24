package ru.bellintegrator.practice.user.dao.impl;

import javassist.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.user.dao.UserDAO;
import ru.bellintegrator.practice.user.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private final EntityManager em;

    @Autowired
    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> all() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public void add(User user) {
        em.persist(user);
    }

    @Override
    public User loadByLogin(String login) {
        return em.find(User.class, login);
    }

    @Override
    public User loadByLoginAndPassword(String login, String password) throws NotFoundException {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery(User.class);

        Root<User> user = criteriaQuery.from(User.class);
        criteriaQuery.where(
                builder.equal(user.get("login"), login),
                builder.equal(user.get("password"), password)
        );

        TypedQuery<User> query = em.createQuery(criteriaQuery);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NotFoundException("Не верные данные");
        }
    }
}
