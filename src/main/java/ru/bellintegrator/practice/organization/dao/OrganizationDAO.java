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
    * Обновить данные Organization в соответствии со входными параметрами
     *
     * N.B. поле id объекта Organization не изменяется, по этому полю проходит поиск
     */
    void update(String id, String name, String fullName, String inn, String kpp,
                   String address, String phone, Boolean isActive);

    /*
    * Сохранить Organization
     */
    void save(String name, String fullName, String inn, String kpp, String address, String phone, Boolean isActive);
}
