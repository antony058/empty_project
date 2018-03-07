package ru.bellintegrator.practice.testdata.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.testdata.dao.PersonDAO;
import ru.bellintegrator.practice.testdata.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Repository
public class PersonDAOImpl implements PersonDAO {

    private final EntityManager em;

    @Autowired
    public PersonDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> all() {
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person loadById(Long id) {
        return em.find(Person.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person loadByName(String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = builder.createQuery(Person.class);

        Root<Person> person = criteria.from(Person.class);
        criteria.where(builder.equal(person.get("name"), name));

        TypedQuery<Person> query = em.createQuery(criteria);
        return query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Person person) {
        em.persist(person);
    }
}