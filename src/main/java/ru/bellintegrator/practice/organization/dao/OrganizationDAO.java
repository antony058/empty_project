package ru.bellintegrator.practice.organization.dao;

import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.view.OrganizationView;

import java.util.List;

public interface OrganizationDAO {

    List<Organization> all(String name, String inn, Boolean isActive);

    Organization loadById(String id);

    void update(String id, String name, String fullName, String inn, String kpp,
                   String address, String phone, Boolean isActive);

    void save(String name, String fullName, String inn, String kpp, String address, String phone, Boolean isActive);
}
