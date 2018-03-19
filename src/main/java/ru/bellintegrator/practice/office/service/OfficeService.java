package ru.bellintegrator.practice.office.service;

import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;

public interface OfficeService {

    ResponseView getAllByOrgId(ListOfficeView view);

    ResponseView getById(String id);

    ResponseView update(UpdateOfficeView view);

    ResponseView save(OfficeView view);

    ResponseView delete(DeleteOfficeView view);
}
