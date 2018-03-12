package ru.bellintegrator.practice.organization.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.ViewWrapper;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import javax.validation.Valid;

public interface OrganizationController {
    ViewWrapper getOrganizations(@Valid @RequestBody ListOrganizationView view, BindingResult bindingResult);

    ViewWrapper getOrganizationById(@PathVariable String id);

    ViewWrapper updateOrganization(@Valid @RequestBody UpdateOrganizationView view, BindingResult bindingResult);

    ViewWrapper saveOrganization(@RequestBody OrganizationView view);
}
