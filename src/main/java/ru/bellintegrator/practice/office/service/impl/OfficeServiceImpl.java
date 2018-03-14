package ru.bellintegrator.practice.office.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.practice.ResponseView;
import ru.bellintegrator.practice.office.dao.OfficeDAO;
import ru.bellintegrator.practice.office.model.Office;
import ru.bellintegrator.practice.office.service.OfficeService;
import ru.bellintegrator.practice.office.view.ListOfficeView;
import ru.bellintegrator.practice.office.view.OfficeView;
import ru.bellintegrator.practice.office.view.UpdateOfficeView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class OfficeServiceImpl implements OfficeService {
    private final OfficeDAO dao;

    @Autowired
    public OfficeServiceImpl(OfficeDAO dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public ResponseView getAllByOrgId(ListOfficeView view) {
        List<Office> offices = dao.allByOrgId(view.orgId, view.name, view.phone, view.isActive);

        Function<Office, OfficeView> mapOffice = o -> {
            OfficeView v = new OfficeView();
            v.id = o.getId();
            v.name = o.getName();
            v.isActive = o.getActive();

            return v;
        };

        List<OfficeView> officeViews = offices.stream().map(mapOffice).collect(Collectors.toList());

        return new ResponseView(officeViews);
    }

    @Override
    @Transactional
    public ResponseView getById(String id) {
        Office office = dao.loadById(id);

        OfficeView view = new OfficeView();
        view.id = office.getId();
        view.name = office.getName();
        view.address = office.getAddress();
        view.phone = office.getPhone();
        view.isActive = office.getActive();

        return new ResponseView(view);
    }

    @Override
    @Transactional
    public ResponseView update(UpdateOfficeView view) {
        Office office = dao.loadById(view.id);

        if (view.name != null && !view.name.isEmpty())
            office.setName(view.name);

        if (view.address != null && !view.address.isEmpty())
            office.setAddress(view.address);

        if (view.phone != null && !view.phone.isEmpty())
            office.setPhone(view.phone);

        if (view.isActive != null)
            office.setActive(view.isActive);

        ResponseView responseView = new ResponseView();
        responseView.setSuccess("success");

        return responseView;
    }

    @Override
    @Transactional
    public ResponseView save(OfficeView view) {
        Office office = new Office(view.name, view.address, view.phone, view.isActive);

        dao.save(office);

        ResponseView responseView = new ResponseView();
        responseView.setSuccess("success");

        return responseView;
    }
}
