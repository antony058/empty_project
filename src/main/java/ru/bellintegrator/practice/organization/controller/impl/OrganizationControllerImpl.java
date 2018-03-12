package ru.bellintegrator.practice.organization.controller.impl;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.practice.ViewWrapper;
import ru.bellintegrator.practice.exception.NotValidParamRuntimeException;
import ru.bellintegrator.practice.organization.controller.OrganizationController;
import ru.bellintegrator.practice.organization.service.OrganizationService;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import javax.validation.Valid;

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
    public ViewWrapper getOrganizations(@Valid @RequestBody ListOrganizationView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamRuntimeException("Необходимо заполнить поле name");

        return organizationService.getAll(view);
    }

    @Override
    @ApiOperation(value = "getOrganizationById", nickname = "getOrganizationById", httpMethod = "GET")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public ViewWrapper getOrganizationById(@PathVariable String id) {
        if (id == null || id.isEmpty()) // custom validation?
            throw new NotValidParamRuntimeException("Необходимо заполнить поле id");

        return organizationService.getOrganizationById(id);
    }

    @Override
    @ApiOperation(value = "updateOrganization", nickname = "updateOrganization", httpMethod = "POST")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ViewWrapper updateOrganization(@Valid @RequestBody UpdateOrganizationView view, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotValidParamRuntimeException("Необходимо заполнить поле id");

        return organizationService.updateOrganization(view);
    }

    @Override
    @ApiOperation(value = "saveOrganization", nickname = "saveOrganization", httpMethod = "POST")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ViewWrapper saveOrganization(@RequestBody OrganizationView view) {
        return organizationService.saveOrganization(view);
    }
}
