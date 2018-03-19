package ru.bellintegrator.practice.employee.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.employee.view.DeleteEmployeeView;
import ru.bellintegrator.practice.employee.view.EmployeeView;
import ru.bellintegrator.practice.employee.view.ListEmployeeView;
import ru.bellintegrator.practice.employee.view.UpdateEmployeeView;

import javax.validation.Valid;

public interface EmployeeController {

    ResponseView getEmployees(@Valid @RequestBody ListEmployeeView view, BindingResult bindingResult);

    ResponseView getEmployee(@PathVariable String id);

    ResponseView updateEmployee(@Valid @RequestBody UpdateEmployeeView view, BindingResult bindingResult);

    ResponseView deleteEmployee(@Valid @RequestBody DeleteEmployeeView view, BindingResult bindingResult);

    ResponseView saveEmployee(@Valid @RequestBody EmployeeView view, BindingResult bindingResult);
}
