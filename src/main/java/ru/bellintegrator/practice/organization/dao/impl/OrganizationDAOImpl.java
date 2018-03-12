package ru.bellintegrator.practice.organization.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.view.OrganizationView;

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
        List<Predicate> predicates = new ArrayList<>();

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
    public Organization loadById(String id) {
        return em.find(Organization.class, Long.valueOf(id));
    }

    @Override
    public void update(String id, String name, String fullName, String inn, String kpp,
                          String address, String phone, Boolean isActive) {
        Organization organization = em.find(Organization.class, Long.valueOf(id));

        if (name != null && !name.isEmpty())
            organization.setName(name);

        if (fullName != null && !fullName.isEmpty())
            organization.setFullName(fullName);

        if (inn != null && !inn.isEmpty())
            organization.setInn(inn);

        if (kpp != null && !kpp.isEmpty())
            organization.setKpp(kpp);

        if (address != null && !address.isEmpty())
            organization.setAddress(address);

        if (phone != null && !phone.isEmpty())
            organization.setPhone(phone);

        if (isActive != null)
            organization.setActive(isActive);
    }

    @Override
    public void save(String name, String fullName, String inn, String kpp,
                        String address, String phone, Boolean isActive) {
        Organization organization = new Organization();

        if (name != null && !name.isEmpty())
            organization.setName(name);

        if (fullName != null && !fullName.isEmpty())
            organization.setFullName(fullName);

        if (inn != null && !inn.isEmpty())
            organization.setInn(inn);

        if (kpp != null && !kpp.isEmpty())
            organization.setKpp(kpp);

        if (address != null && !address.isEmpty())
            organization.setAddress(address);

        if (phone != null && !phone.isEmpty())
            organization.setPhone(phone);

        if (isActive != null)
            organization.setActive(isActive);

        em.persist(organization);
    }

}
