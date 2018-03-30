package ru.bellintegrator.practice.organization.controller.impl;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.utils.ErrorUtils;
import ru.bellintegrator.practice.utils.StringChecker;
import ru.bellintegrator.practice.exception.NotValidParamException;
import ru.bellintegrator.practice.organization.controller.OrganizationController;
import ru.bellintegrator.practice.organization.service.OrganizationService;
import ru.bellintegrator.practice.organization.view.DeleteOrganizationView;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/organization", produces = APPLICATION_JSON_VALUE)
public class OrganizationControllerImpl implements OrganizationController {
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationControllerImpl(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    @ApiOperation(value = "getOrganizations", nickname = "getOrganizations", httpMethod = "POST")
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public ResponseView getOrganizations(@Valid @RequestBody ListOrganizationView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Необходимо заполнить поле name");

        List<OrganizationView> organizationViews = organizationService.getAll(view);
        return new ResponseView().data(organizationViews);
    }

    @Override
    @ApiOperation(value = "getOrganizationById", nickname = "getOrganizationById", httpMethod = "GET")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public ResponseView getOrganizationById(@PathVariable String id) throws NotFoundException {
        if (StringChecker.isNullOrEmpty(id))
            throw new NotValidParamException("Необходимо заполнить поле id");
        else if (!StringChecker.isNumeric(id))
            throw new NotValidParamException("Параметр id должен быть числовым");

        OrganizationView organizationView = organizationService.getOrganizationById(id);
        return new ResponseView().data(organizationView);
    }

    @Override
    @ApiOperation(value = "updateOrganization", nickname = "updateOrganization", httpMethod = "POST")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseView updateOrganization(@Valid @RequestBody UpdateOrganizationView view,
                                           BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Необходимо заполнить поле id");

        organizationService.updateOrganization(view);
        return new ResponseView().success();
    }

    @Override
    @ApiOperation(value = "saveOrganization", nickname = "saveOrganization", httpMethod = "POST")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ResponseView saveOrganization(@Valid @RequestBody OrganizationView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamException(ErrorUtils.makeRequiredFieldsList(bindingResult.getFieldErrors()));

        organizationService.saveOrganization(view);
        return new ResponseView().success();
    }

    @Override
    @ApiOperation(value = "deleteOrganization", nickname = "deleteOrganization", httpMethod = "POST")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResponseView deleteOrganization(@Valid @RequestBody DeleteOrganizationView view,
                                           BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors())
            throw new NotValidParamException("Необходимо заполнить поле id");

        organizationService.deleteOrganization(view);
        return new ResponseView().success();
    }
}
