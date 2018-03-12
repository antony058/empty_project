package ru.bellintegrator.practice.organization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.service.OrganizationService;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.ViewWrapper;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationDAO dao;

    @Autowired
    public OrganizationServiceImpl(OrganizationDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public ViewWrapper getAll(ListOrganizationView view) {
        List<Organization> all = dao.all(view.name, view.inn, view.isActive);

        Function<Organization, OrganizationView> mapOrganization = o -> {
            OrganizationView v = new OrganizationView();
            v.id = String.valueOf(o.getId());
            v.name = o.getName();
            v.isActive = o.getActive();

            return v;
        };

        List<OrganizationView> orgViewsList = all.stream()
                .map(mapOrganization)
                .collect(Collectors.toList());

        return new ViewWrapper(orgViewsList);
    }

    @Override
    @Transactional(readOnly = true)
    public ViewWrapper getOrganizationById(String id) {
        Organization organization = dao.loadById(id);

        OrganizationView view = new OrganizationView();
        view.id = String.valueOf(organization.getId());
        view.name = organization.getName();
        view.fullName = organization.getFullName();
        view.inn = organization.getInn();
        view.kpp = organization.getKpp();
        view.address = organization.getAddress();
        view.phone = organization.getPhone();
        view.isActive = organization.getActive();

        return new ViewWrapper(view);
    }

    @Override
    @Transactional
    public ViewWrapper updateOrganization(UpdateOrganizationView view) {
        dao.update(view.id, view.name, view.fullName, view.inn, view.kpp, view.address, view.phone, view.isActive);

        ViewWrapper viewWrapper = new ViewWrapper();
        viewWrapper.setSuccess("success");

        return viewWrapper;
    }

    @Override
    @Transactional
    public ViewWrapper saveOrganization(OrganizationView view) {
        dao.save(view.name, view.fullName, view.inn, view.kpp, view.address, view.phone, view.isActive);

        ViewWrapper viewWrapper = new ViewWrapper();
        viewWrapper.setSuccess("success");

        return viewWrapper;
    }
}
