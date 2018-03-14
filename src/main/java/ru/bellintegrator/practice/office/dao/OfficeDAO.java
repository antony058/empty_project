package ru.bellintegrator.practice.office.dao;

import ru.bellintegrator.practice.office.model.Office;

import java.util.List;

public interface OfficeDAO {

    List<Office> allByOrgId(String orgId, String name, String phone, Boolean isActive);

    Office loadById(String id);

    void save(Office office);
}
