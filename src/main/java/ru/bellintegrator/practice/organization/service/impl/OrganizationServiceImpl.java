package ru.bellintegrator.practice.organization.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.service.OrganizationService;
import ru.bellintegrator.practice.organization.view.DeleteOrganizationView;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.ResponseView;
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
    public List<OrganizationView> getAll(ListOrganizationView view) {
        List<Organization> all = dao.all(view.name, view.inn, view.isActive);

        Function<Organization, OrganizationView> mapOrganization = o -> {
            OrganizationView v = new OrganizationView();
            v.id = o.getId();
            v.name = o.getName();
            v.isActive = o.getActive();

            return v;
        };

        List<OrganizationView> orgViewsList = all.stream()
                .map(mapOrganization)
                .collect(Collectors.toList());

        return orgViewsList;
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationView getOrganizationById(String id) throws NotFoundException {
        Organization organization = dao.loadById(Long.valueOf(id));

        OrganizationView view = new OrganizationView();
        view.id = organization.getId();
        view.name = organization.getName();
        view.fullName = organization.getFullName();
        view.inn = organization.getInn();
        view.kpp = organization.getKpp();
        view.address = organization.getAddress();
        view.phone = organization.getPhone();
        view.isActive = organization.getActive();

        return view;
    }

    @Override
    @Transactional
    public void updateOrganization(UpdateOrganizationView view) throws NotFoundException {
        Organization organization = dao.loadById(view.id);

        if (view.name != null && !view.name.isEmpty())
            organization.setName(view.name);

        if (view.fullName != null && !view.fullName.isEmpty())
            organization.setFullName(view.fullName);

        if (view.inn != null && !view.inn.isEmpty())
            organization.setInn(view.inn);

        if (view.kpp != null && !view.kpp.isEmpty())
            organization.setKpp(view.kpp);

        if (view.address != null && !view.address.isEmpty())
            organization.setAddress(view.address);

        if (view.phone != null && !view.phone.isEmpty())
            organization.setPhone(view.phone);

        if (view.isActive != null)
            organization.setActive(view.isActive);
    }

    @Override
    @Transactional
    public void saveOrganization(OrganizationView view) {
        Organization organization = new Organization(view.name, view.fullName, view.inn,
                view.kpp, view.address, view.phone, view.isActive);

        dao.save(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(DeleteOrganizationView view) throws NotFoundException {
        Organization organization = dao.loadById(view.id);

        dao.delete(organization);
    }
}
