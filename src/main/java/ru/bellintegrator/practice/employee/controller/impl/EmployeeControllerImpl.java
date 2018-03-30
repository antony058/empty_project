package ru.bellintegrator.practice.employee.controller.impl;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.utils.StringChecker;
import ru.bellintegrator.practice.employee.controller.EmployeeController;
import ru.bellintegrator.practice.employee.service.EmployeeService;
import ru.bellintegrator.practice.employee.view.DeleteEmployeeView;
import ru.bellintegrator.practice.employee.view.EmployeeView;
import ru.bellintegrator.practice.employee.view.ListEmployeeView;
import ru.bellintegrator.practice.employee.view.UpdateEmployeeView;
import ru.bellintegrator.practice.exception.NotValidParamException;

import javax.validation.Valid;

import java.util.List;

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

        List<EmployeeView> employeeViews = employeeService.getAllByOfficeId(view);
        return new ResponseView().data(employeeViews);
    }

    @Override
    @ApiOperation(value = "getEmployee", nickname = "getEmployee", httpMethod = "GET")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public ResponseView getEmployee(@PathVariable String id) throws NotFoundException {
        if (StringChecker.isNullOrEmpty(id))
            throw new NotValidParamException("Параметр id не может быть пустым");
        else if (!StringChecker.isNumeric(id))
            throw new NotValidParamException("Параметр id должен быть числовым");

        EmployeeView employeeView = employeeService.getById(id);
        return new ResponseView().data(employeeView);
    }

    @Override
    @ApiOperation(value = "updateEmployee", nickname = "updateEmployee", httpMethod = "POST")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseView updateEmployee(@Valid @RequestBody UpdateEmployeeView view,
                                       BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр id не может быть пустым");

        employeeService.update(view);
        return new ResponseView().success();
    }

    @Override
    @ApiOperation(value = "deleteEmployee", nickname = "deleteEmployee", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResponseView deleteEmployee(@Valid @RequestBody DeleteEmployeeView view,
                                       BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр id не может быть пустым");

        employeeService.delete(view);
        return new ResponseView().success();
    }

    @Override
    @ApiOperation(value = "saveEmployee", nickname = "saveEmployee", httpMethod = "POST")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ResponseView saveEmployee(@Valid @RequestBody EmployeeView view,
                                     BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр officeId не может быть пустым");

        employeeService.save(view);
        return new ResponseView().success();
    }
}
