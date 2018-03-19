package ru.bellintegrator.practice.employee.service;

import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.employee.view.DeleteEmployeeView;
import ru.bellintegrator.practice.employee.view.EmployeeView;
import ru.bellintegrator.practice.employee.view.ListEmployeeView;
import ru.bellintegrator.practice.employee.view.UpdateEmployeeView;

public interface EmployeeService {

    ResponseView getAllByOfficeId(ListEmployeeView view);

    ResponseView getById(String id);

    ResponseView update(UpdateEmployeeView view);

    ResponseView delete(DeleteEmployeeView view);

    ResponseView save(EmployeeView view);
}
