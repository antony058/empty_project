package ru.bellintegrator.practice.office.service.impl;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.office.dao.OfficeDAO;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.view.DeleteOfficeView;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;
import ru.bellintegrator.practice.organization.dao.OrganizationDAO;
import ru.bellintegrator.practice.organization.model.Organization;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OfficeServiceImpl implements OfficeService {

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

        Function<Office, OfficeView> mapOffice = o -> {
            OfficeView v = new OfficeView();
            v.id = o.getId();
            v.name = o.getName();
            v.isActive = o.getActive();

            return v;
        };

        List<OfficeView> officeViews = offices.stream().map(mapOffice).collect(Collectors.toList());

        return officeViews;
    }

    @Override
    @Transactional(readOnly = true)
    public OfficeView getById(String id) throws NotFoundException {
        Office office = dao.loadById(Long.valueOf(id));

        OfficeView view = new OfficeView();
        view.id = office.getId();
        view.name = office.getName();
        view.address = office.getAddress();
        view.phone = office.getPhone();
        view.isActive = office.getActive();

        return view;
    }

    @Override
    @Transactional
    public void update(UpdateOfficeView view) throws NotFoundException {
        Office office = dao.loadById(view.id);

        if (view.name != null && !view.name.isEmpty())
            office.setName(view.name);

        if (view.address != null && !view.address.isEmpty())
            office.setAddress(view.address);

        if (view.phone != null && !view.phone.isEmpty())
            office.setPhone(view.phone);

        if (view.isActive != null)
            office.setActive(view.isActive);
    }

    @Override
    @Transactional
    public void save(OfficeView view) throws NotFoundException {
        Organization organization = orgDAO.loadById(view.orgId);

        Office office = new Office();
        office.setName(view.name);
        office.setPhone(view.phone);
        office.setAddress(view.address);
        office.setActive(view.isActive);
        office.setOrganization(organization);

        dao.save(office);
    }

    @Override
    @Transactional
    public void delete(DeleteOfficeView view) throws NotFoundException {
        Office office = dao.loadById(view.id);

        dao.delete(office);
    }
}
