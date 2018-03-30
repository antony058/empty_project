package ru.bellintegrator.practice.office.controller.impl;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.utils.StringChecker;
import ru.bellintegrator.practice.exception.NotValidParamException;
import ru.bellintegrator.practice.office.controller.OfficeController;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;

import javax.validation.Valid;

import java.util.List;

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

        List<OfficeView> officeViews = officeService.getAllByOrgId(view);
        return new ResponseView().data(officeViews);
    }

    @Override
    @ApiOperation(value = "getOffices", nickname = "getOffice", httpMethod = "GET")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public ResponseView getOffice(@PathVariable String id) throws NotFoundException {
        if (StringChecker.isNullOrEmpty(id))
            throw new NotValidParamException("Параметр id не может быть пустым");
        else if (!StringChecker.isNumeric(id))
            throw new NotValidParamException("Параметр id должен быть числовым");

        OfficeView officeView = officeService.getById(id);
        return new ResponseView().data(officeView);
    }

    @Override
    @ApiOperation(value = "updateOffice", nickname = "updateOffice", httpMethod = "POST")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseView updateOffice(@Valid @RequestBody UpdateOfficeView view,
                                     BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Заполните необходимые поля");

        officeService.update(view);
        return new ResponseView().success();
    }

    @Override
    @ApiOperation(value = "saveOffice", nickname = "saveOffice", httpMethod = "POST")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ResponseView saveOffice(@Valid @RequestBody OfficeView view,
                                   BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр orgId не может быть пустым");

        officeService.save(view);
        return new ResponseView().success();
    }

    @Override
    @ApiOperation(value = "deleteOffice", nickname = "deleteOffice", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResponseView deleteOffice(@Valid @RequestBody DeleteOfficeView view,
                                     BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Параметр id не может быть пустым");

        officeService.delete(view);
        return new ResponseView().success();
    }
}
