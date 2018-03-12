package ru.bellintegrator.practice.organization.service;

import ru.bellintegrator.practice.ViewWrapper;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import java.util.List;

public interface OrganizationService {

    ViewWrapper getAll(ListOrganizationView view);

    ViewWrapper getOrganizationById(String id);

    ViewWrapper updateOrganization(UpdateOrganizationView view);

    ViewWrapper saveOrganization(OrganizationView view);
}
