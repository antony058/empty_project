package ru.bellintegrator.practice.organization.dao;

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
    Organization loadById(String id);

    /*
    * Сохранить Organization
     */
    void save(Organization organization);
}
