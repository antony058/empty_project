package ru.bellintegrator.practice.user.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.user.dao.ActivationDAO;
import ru.bellintegrator.practice.user.model.Activation;
import ru.bellintegrator.practice.user.model.User;
import ru.bellintegrator.practice.user.view.ActivationView;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ActivationDAOImpl implements ActivationDAO {
    private final EntityManager em;

    @Autowired
    public ActivationDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Activation> all() {
        TypedQuery<Activation> query = em.createQuery("SELECT a FROM Activation a", Activation.class);
        return query.getResultList();
    }

    @Override
    public Activation checkActivationCode(String code) {
        TypedQuery<Activation> query = em.createQuery(
                "SELECT a FROM Activation a WHERE a.code='" + code + "'", Activation.class
        );

        return query.getSingleResult();
    }


}
