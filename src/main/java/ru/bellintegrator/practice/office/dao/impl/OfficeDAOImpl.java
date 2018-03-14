package ru.bellintegrator.practice.office.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.office.dao.OfficeDAO;
import ru.bellintegrator.practice.office.model.Office;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfficeDAOImpl implements OfficeDAO {

    private final EntityManager em;

    @Autowired
    public OfficeDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Office> allByOrgId(String orgId, String name, String phone, Boolean isActive) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Office.class);

        Root<Office> office = criteriaQuery.from(Office.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(office.join("organization").get("id"), Long.valueOf(orgId)));

        if (name != null && !name.isEmpty())
            predicates.add(criteriaBuilder.like(office.get("name"), "%" + name.trim() + "%"));

        if (phone != null && !phone.isEmpty())
            predicates.add(criteriaBuilder.like(office.get("phone"), "%" + phone.trim() + "%"));

        if (isActive != null)
            predicates.add(criteriaBuilder.equal(office.get("isActive"), isActive));

        criteriaQuery.select(office);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Office> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Office loadById(String id) {
        return em.find(Office.class, Long.valueOf(id));
    }

    @Override
    public void save(Office office) {
        em.persist(office);
    }
}
