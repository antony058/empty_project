package ru.bellintegrator.practice.organization.service;

import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.organization.view.DeleteOrganizationView;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

public interface OrganizationService {

    /*
    *
     */
    ResponseView getAll(ListOrganizationView view);

    /*
    *
     */
    ResponseView getOrganizationById(String id);

    /*
    *
     */
    ResponseView updateOrganization(UpdateOrganizationView view);

    /*
    *
     */
    ResponseView saveOrganization(OrganizationView view);

    /*
    *
     */
    ResponseView deleteOrganization(DeleteOrganizationView view);
}
