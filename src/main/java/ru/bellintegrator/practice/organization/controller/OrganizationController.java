package ru.bellintegrator.practice.organization.controller;

import javassist.NotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.organization.view.DeleteOrganizationView;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import javax.validation.Valid;

public interface OrganizationController {

    /*
    * Получить все объекты Organization
    * по входному JSON
     */
    ResponseView getOrganizations(@Valid @RequestBody ListOrganizationView view, BindingResult bindingResult);

    /*
    * Получить Organization по id
    *
     */
    ResponseView getOrganizationById(@PathVariable String id) throws NotFoundException;

    /*
    * Обновить Organization
    * по входному JSON
     */
    ResponseView updateOrganization(@Valid @RequestBody UpdateOrganizationView view, BindingResult bindingResult) throws NotFoundException;

    /*
    * Сохранить Organization
    *
     */
    ResponseView saveOrganization(@RequestBody OrganizationView view, BindingResult bindingResult);

    /*
    * Удалить Organization
    *
     */
    ResponseView deleteOrganization(@Valid @RequestBody DeleteOrganizationView view, BindingResult bindingResult) throws NotFoundException;
}
