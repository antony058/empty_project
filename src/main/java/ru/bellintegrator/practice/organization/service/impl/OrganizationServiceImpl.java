package ru.bellintegrator.practice.organization.service.impl;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.mapper.OrganizationMapper;
import ru.bellintegrator.practice.organization.model.Organization;
import ru.bellintegrator.practice.organization.service.OrganizationService;
import ru.bellintegrator.practice.organization.view.DeleteOrganizationView;
import ru.bellintegrator.practice.organization.view.ListOrganizationView;
import ru.bellintegrator.practice.organization.view.OrganizationView;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.organization.view.UpdateOrganizationView;
import ru.bellintegrator.practice.user.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OrganizationServiceImpl implements OrganizationService {
    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    private final OrganizationDAO dao;

    @Autowired
    public OrganizationServiceImpl(OrganizationDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationView> getAll(ListOrganizationView view) {
        List<Organization> all = dao.all(view.name, view.inn, view.isActive);

        List<OrganizationView> views = new ArrayList<>(all.size());

        for (Organization organization: all)
            views.add(OrganizationMapper.mapFromOrganization(organization, new OrganizationView()));

        return views;
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationView getOrganizationById(String id) throws NotFoundException {
        Organization organization = dao.loadById(Long.valueOf(id));

        return OrganizationMapper.mapFromOrganization(organization, new OrganizationView());
    }

    @Override
    @Transactional
    public void updateOrganization(UpdateOrganizationView view) throws NotFoundException {
        Organization organization = dao.loadById(view.id);

        OrganizationMapper.mapFromUpdateOrganizationView(view, organization);

        log.info("Редактирована организация " + organization.getName());
    }

    @Override
    @Transactional
    public void saveOrganization(OrganizationView view) {
        Organization organization = new Organization();
        OrganizationMapper.mapFromOrganizationView(view, organization);

        dao.save(organization);

        log.info("Добавлена организация " + organization.getName());
    }

    @Override
    @Transactional
    public void deleteOrganization(DeleteOrganizationView view) throws NotFoundException {
        Organization organization = dao.loadById(view.id);

        Set<Office> offices = organization.getOffices();
        for (Office office: new HashSet<>(offices))
            organization.removeOffice(office);

        dao.delete(organization);

        log.info("Удалена организация " + organization.getName());
    }
}
