package ru.bellintegrator.practice.office.dao;

import javassist.NotFoundException;
import ru.bellintegrator.practice.office.model.Office;

import java.util.List;

public interface OfficeDAO {

    List<Office> allByOrgId(Long orgId, String name, String phone, Boolean isActive);

    Office loadById(Long id) throws NotFoundException;

    void save(Office office);

    void delete(Office office);
}
