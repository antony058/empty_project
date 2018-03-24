package ru.bellintegrator.practice.office.service;

import javassist.NotFoundException;
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;

import java.util.List;

public interface OfficeService {

    List<OfficeView> getAllByOrgId(ListOfficeView view);

    OfficeView getById(String id) throws NotFoundException;

    void update(UpdateOfficeView view) throws NotFoundException;

    void save(OfficeView view) throws NotFoundException;

    void delete(DeleteOfficeView view) throws NotFoundException;
}
