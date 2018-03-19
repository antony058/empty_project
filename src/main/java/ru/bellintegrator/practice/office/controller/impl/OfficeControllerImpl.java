package ru.bellintegrator.practice.office.controller.impl;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.exception.NotValidParamException;
import ru.bellintegrator.practice.office.controller.OfficeController;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/office", produces = APPLICATION_JSON_VALUE)
public class OfficeControllerImpl implements OfficeController {
    private final OfficeService officeService;

    @Autowired
    public OfficeControllerImpl(OfficeService officeService) {
        this.officeService = officeService;
    }

    @Override
    @ApiOperation(value = "getOffices", nickname = "getOffices", httpMethod = "POST")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public ResponseView getOffices(@Valid @RequestBody ListOfficeView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр orgId не может быть пустым");

        return officeService.getAllByOrgId(view);
    }

    @Override
    @ApiOperation(value = "getOffices", nickname = "getOffice", httpMethod = "GET")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public ResponseView getOffice(@PathVariable String id) {
        if (id == null || id.isEmpty())
            throw new NotValidParamException("Параметр id не может быть пустым");

        return officeService.getById(id);
    }

    @Override
    @ApiOperation(value = "updateOffice", nickname = "updateOffice", httpMethod = "POST")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseView updateOffice(@Valid @RequestBody UpdateOfficeView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Заполните необходимые поля");

        return officeService.update(view);
    }

    @Override
    @ApiOperation(value = "saveOffice", nickname = "saveOffice", httpMethod = "POST")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ResponseView saveOffice(@Valid @RequestBody OfficeView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр orgId не может быть пустым");

        return officeService.save(view);
    }

    @Override
    @ApiOperation(value = "deleteOffice", nickname = "deleteOffice", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResponseView deleteOffice(@Valid @RequestBody DeleteOfficeView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр id не может быть пустым");

        return officeService.delete(view);
    }
}
