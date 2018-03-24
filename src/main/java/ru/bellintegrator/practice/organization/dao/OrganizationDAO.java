package ru.bellintegrator.practice.organization.dao;

import javassist.NotFoundException;
import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.view.OrganizationView;

import java.util.List;

public interface OrganizationDAO {

    /*
    * Получить список объектов Organization,
    * которые удовлетворяют входным параметрам
     */
    List<Organization> all(String name, String inn, Boolean isActive);

    /*
    * Получить Organization по id
     */
    Organization loadById(Long id) throws NotFoundException;

    /*
    * Сохранить Organization
     */
    void save(Organization organization);

    void delete(Organization organization);

    void update(Organization organization);
}
