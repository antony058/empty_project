package ru.bellintegrator.practice.employee.service;

import javassist.NotFoundException;
import ru.bellintegrator.practice.employee.view.DeleteEmployeeView;
import ru.bellintegrator.practice.employee.view.EmployeeView;
import ru.bellintegrator.practice.employee.view.ListEmployeeView;
import ru.bellintegrator.practice.employee.view.UpdateEmployeeView;

import java.util.List;

public interface EmployeeService {

    List<EmployeeView> getAllByOfficeId(ListEmployeeView view);

    EmployeeView getById(String id) throws NotFoundException;

    void update(UpdateEmployeeView view) throws NotFoundException;

    void delete(DeleteEmployeeView view) throws NotFoundException;

    void save(EmployeeView view) throws NotFoundException;
}
