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
    public ResponseView getAll(ListOrganizationView view) {
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

        return new ResponseView(orgViewsList); // упаковываем список в обертку
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseView getOrganizationById(String id) {
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

        return new ResponseView(view); // упаковываем объект в обертку
    }

    @Override
    @Transactional
    public ResponseView updateOrganization(UpdateOrganizationView view) {
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

        ResponseView responseView = new ResponseView();
        responseView.setSuccess("success");

        return responseView; // запись пользователю об успешном измении данных
    }

    @Override
    @Transactional
    public ResponseView saveOrganization(OrganizationView view) {
        Organization organization = new Organization(view.name, view.fullName, view.inn,
                view.kpp, view.address, view.phone, view.isActive);

        dao.save(organization);

        ResponseView responseView = new ResponseView();
        responseView.setSuccess("success");

        return responseView; // запись пользователю об успешном сохранении организации
    }
}
