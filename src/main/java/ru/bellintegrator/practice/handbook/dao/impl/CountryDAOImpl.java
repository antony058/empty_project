package ru.bellintegrator.practice.handbook.dao.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.handbook.dao.CountryDAO;
import ru.bellintegrator.practice.handbook.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CountryDAOImpl implements CountryDAO {

    private final EntityManager em;

    @Autowired
    public CountryDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Country> all() {
        TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c", Country.class);

        return query.getResultList();
    }

    @Override
    public Country loadByCode(Integer code) {
        return em.find(Country.class, code);
    }

    @Override
    public Country load(Integer code, String name) throws NotFoundException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Country.class);

        Root<Country> country = criteriaQuery.from(Country.class);
        criteriaQuery.where(
                criteriaBuilder.equal(country.get("code"), code),
                criteriaBuilder.equal(country.get("name"), name)
        );

        TypedQuery<Country> query = em.createQuery(criteriaQuery);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NotFoundException("Страны с code = " + code + " и name = " + name + " не найдено");
        }
    }
}
