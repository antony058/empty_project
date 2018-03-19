package ru.bellintegrator.practice.employee.controller.impl;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.employee.controller.EmployeeController;
import ru.bellintegrator.practice.employee.service.EmployeeService;
import ru.bellintegrator.practice.employee.view.DeleteEmployeeView;
import ru.bellintegrator.practice.employee.view.EmployeeView;
import ru.bellintegrator.practice.employee.view.ListEmployeeView;
import ru.bellintegrator.practice.employee.view.UpdateEmployeeView;
import ru.bellintegrator.practice.exception.NotValidParamException;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/user", produces = APPLICATION_JSON_VALUE)
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @ApiOperation(value = "getEmployees", nickname = "getEmployees", httpMethod = "POST")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public ResponseView getEmployees(@Valid @RequestBody ListEmployeeView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр officeId не может быть пустым");

        return employeeService.getAllByOfficeId(view);
    }

    @Override
    @ApiOperation(value = "getEmployee", nickname = "getEmployee", httpMethod = "GET")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public ResponseView getEmployee(@PathVariable String id) {
        if (id == null || id.isEmpty())
            throw new NotValidParamException("Параметр id не может быть пустым");

        return employeeService.getById(id);
    }

    @Override
    @ApiOperation(value = "updateEmployee", nickname = "updateEmployee", httpMethod = "POST")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseView updateEmployee(@Valid @RequestBody UpdateEmployeeView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр id не может быть пустым");

        return employeeService.update(view);
    }

    @Override
    @ApiOperation(value = "deleteEmployee", nickname = "deleteEmployee", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResponseView deleteEmployee(@Valid @RequestBody DeleteEmployeeView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр id не может быть пустым");

        return employeeService.delete(view);
    }

    @Override
    @ApiOperation(value = "saveEmployee", nickname = "saveEmployee", httpMethod = "POST")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ResponseView saveEmployee(@Valid @RequestBody EmployeeView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр officeId не может быть пустым");

        return employeeService.save(view);
    }
}
