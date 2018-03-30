package ru.bellintegrator.practice.office.service.impl;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.employee.model.Employee;
import ru.bellintegrator.practice.office.dao.OfficeDAO;
import ru.bellintegrator.practice.office.mapper.OfficeMapper;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.model.Organization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OfficeServiceImpl implements OfficeService {
    private final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);

    private final OfficeDAO dao;
    private final OrganizationDAO orgDAO;

    @Autowired
    public OfficeServiceImpl(OfficeDAO dao, OrganizationDAO orgDAO) {
        this.dao = dao;
        this.orgDAO = orgDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OfficeView> getAllByOrgId(ListOfficeView view) {
        List<Office> offices = dao.allByOrgId(view.orgId, view.name, view.phone, view.isActive);

        List<OfficeView> views = new ArrayList<>(offices.size());

        for (Office office: offices) {
            views.add(OfficeMapper.mapFromOffice(office, new OfficeView()));
        }

        return views;
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeView getById(String id) throws NotFoundException {
        Office office = dao.loadById(Long.valueOf(id));

        return OfficeMapper.mapFromOffice(office, new OfficeView());
    }

    @Override
    @Transactional
    public void update(UpdateOfficeView view) throws NotFoundException {
        Office office = dao.loadById(view.id);

        OfficeMapper.mapFromUpdateOfficeView(view, office);

        log.info("Изменен офис " + office.getName());
    }

    @Override
    @Transactional
    public void save(OfficeView view) throws NotFoundException {
        Organization organization = orgDAO.loadById(view.orgId);

        Office office = new Office();
        office.setOrganization(organization);

        OfficeMapper.mapFromOfficeView(view, office);

        dao.save(office);

        log.info("Добавлен офис " + office.getName() + " в организацию " + organization.getName());
    }

    @Override
    @Transactional
    public void delete(DeleteOfficeView view) throws NotFoundException {
        Office office = dao.loadById(view.id);

        dao.delete(office);

        log.info("Удален офис " + office.getName());
    }
}
