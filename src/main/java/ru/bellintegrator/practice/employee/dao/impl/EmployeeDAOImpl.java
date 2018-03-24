package ru.bellintegrator.practice.employee.dao.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.practice.employee.dao.EmployeeDAO;
import ru.bellintegrator.practice.employee.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private final EntityManager em;

    @Autowired
    public EmployeeDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Employee> allByOfficeId(Long officeId, String firstName, String lastName,
                                        String middleName, String position, Integer docCode, Integer citizenshipCode) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(Employee.class);

        Root<Employee> employee = query.from(Employee.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(employee.join("offices").get("id"), officeId));

        if (firstName != null && !firstName.isEmpty())
            predicates.add(builder.like(employee.get("firstName"), "%" + firstName + "%"));

        if (lastName != null && !lastName.isEmpty())
            predicates.add(builder.like(employee.get("lastName"), "%" + lastName + "%"));

        if (middleName != null && !middleName.isEmpty())
            predicates.add(builder.like(employee.get("middleName"), "%" + middleName + "%"));

        if (position != null && !position.isEmpty())
            predicates.add(builder.like(employee.get("position"), "%" + position + "%"));

        if (docCode != null)
            predicates.add(builder.equal(employee
                    .join("employeesDocuments")
                    .join("document")
                    .get("code"), docCode));

        if (citizenshipCode != null)
            predicates.add(builder.equal(employee.join("country").get("code"), citizenshipCode));


        query.where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Employee> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public Employee loadById(Long id) throws NotFoundException {
        Employee employee = em.find(Employee.class, id);
        if (employee == null)
            throw new NotFoundException("Работник с id = " + id + " не найден");

        return employee;
    }

    @Override
    public void update(Employee employee) {
        em.merge(employee);
    }

    @Override
    public void delete(Employee employee) {
        em.remove(employee);
    }

    @Override
    public void save(Employee employee) {
        em.persist(employee);
    }
}
