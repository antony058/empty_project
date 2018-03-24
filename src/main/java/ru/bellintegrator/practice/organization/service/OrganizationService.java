package ru.bellintegrator.practice.organization.service;

import javassist.NotFoundException;
import ru.bellintegrator.practice.organization.view.DeleteOrganizationView;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import java.util.List;

public interface OrganizationService {

    /*
    *
     */
    List<OrganizationView> getAll(ListOrganizationView view);

    /*
    *
     */
    OrganizationView getOrganizationById(String id) throws NotFoundException;

    /*
    *
     */
    void updateOrganization(UpdateOrganizationView view) throws NotFoundException;

    /*
    *
     */
    void saveOrganization(OrganizationView view);

    /*
    *
     */
    void deleteOrganization(DeleteOrganizationView view) throws NotFoundException;
}
