package ru.bellintegrator.practice.organization.dao.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.model.Organization;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizationDAOImpl implements OrganizationDAO {

    private final EntityManager em;

    @Autowired
    public OrganizationDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Organization> all(String name, String inn, Boolean isActive) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Organization> criteria = builder.createQuery(Organization.class);

        Root<Organization> organization = criteria.from(Organization.class);
        List<Predicate> predicates = new ArrayList<>(); // список критериев поиска

        predicates.add(builder.like(organization.get("name"), "%" + name.trim() + "%"));

        if (inn != null && !inn.isEmpty()) {
            predicates.add(builder.equal(organization.get("inn"), inn.trim()));
        }

        if (isActive != null) {
            predicates.add(builder.equal(organization.get("isActive"), isActive));
        }

        criteria.select(organization);
        criteria.where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Organization> query = em.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public Organization loadById(Long id) throws NotFoundException {
        Organization organization = em.find(Organization.class, id);
        if (organization == null)
            throw new NotFoundException("Организация с id = " + id + " не найдена");

        return organization;
    }

    @Override
    public void save(Organization organization) {
        em.persist(organization);
    }

    @Override
    public void delete(Organization organization) {
        em.remove(organization);
    }

    @Override
    public void update(Organization organization) {
        em.merge(organization);
    }

}
